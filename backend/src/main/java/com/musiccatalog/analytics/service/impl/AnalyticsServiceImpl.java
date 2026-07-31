package com.musiccatalog.analytics.service.impl;

import com.musiccatalog.analytics.dto.AnalyticsResponseDTO;
import com.musiccatalog.analytics.dto.ChartDataDTO;
import com.musiccatalog.analytics.dto.DashboardOverviewDTO;
import com.musiccatalog.analytics.service.AnalyticsService;
import com.musiccatalog.library.entity.SavedAlbum;
import com.musiccatalog.library.repository.SavedAlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final SavedAlbumRepository savedAlbumRepository;

    @Override
    public AnalyticsResponseDTO getAnalytics(UUID userId) {
        List<SavedAlbum> albums = savedAlbumRepository.findAllByUserId(userId);

        if (albums.isEmpty()) {
            return AnalyticsResponseDTO.builder()
                    .overview(new DashboardOverviewDTO(0, 0, 0, 0.0))
                    .topGenres(Collections.emptyList())
                    .topArtists(Collections.emptyList())
                    .releaseYears(Collections.emptyList())
                    .ratingDistribution(Collections.emptyList())
                    .build();
        }

        // Overview
        int totalAlbums = albums.size();
        int totalArtists = (int) albums.stream().map(SavedAlbum::getArtistName).filter(Objects::nonNull).distinct().count();
        int totalGenres = (int) albums.stream().map(SavedAlbum::getGenre).filter(Objects::nonNull).distinct().count();
        double averageRating = albums.stream()
                .map(SavedAlbum::getUserRating)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        DashboardOverviewDTO overview = new DashboardOverviewDTO(totalAlbums, totalArtists, totalGenres, Math.round(averageRating * 10.0) / 10.0);

        // Top Genres
        List<ChartDataDTO> topGenres = getTopN(albums, SavedAlbum::getGenre, 5);

        // Top Artists
        List<ChartDataDTO> topArtists = getTopN(albums, SavedAlbum::getArtistName, 5);

        // Release Years
        List<ChartDataDTO> releaseYears = albums.stream()
                .map(SavedAlbum::getReleaseDate)
                .filter(Objects::nonNull)
                .map(date -> date.length() >= 4 ? date.substring(0, 4) : "Unknown")
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new ChartDataDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        // Rating Distribution
        List<ChartDataDTO> ratingDistribution = albums.stream()
                .map(SavedAlbum::getUserRating)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(String::valueOf, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new ChartDataDTO(entry.getKey() + " Stars", entry.getValue()))
                .collect(Collectors.toList());

        // Ensure 1-5 stars are present even if 0 count
        List<ChartDataDTO> completeRatingDistribution = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            String label = i + " Stars";
            long count = ratingDistribution.stream()
                    .filter(d -> d.getName().equals(label))
                    .map(d -> d.getValue().longValue())
                    .findFirst()
                    .orElse(0L);
            completeRatingDistribution.add(new ChartDataDTO(label, count));
        }

        return AnalyticsResponseDTO.builder()
                .overview(overview)
                .topGenres(topGenres)
                .topArtists(topArtists)
                .releaseYears(releaseYears)
                .ratingDistribution(completeRatingDistribution)
                .build();
    }

    private List<ChartDataDTO> getTopN(List<SavedAlbum> albums, Function<SavedAlbum, String> keyExtractor, int limit) {
        return albums.stream()
                .map(keyExtractor)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .map(entry -> new ChartDataDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
