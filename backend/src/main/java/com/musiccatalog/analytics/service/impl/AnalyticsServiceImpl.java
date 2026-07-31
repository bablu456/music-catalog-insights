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
    public DashboardOverviewDTO getOverview(UUID userId) {
        List<SavedAlbum> albums = savedAlbumRepository.findAllByUserId(userId);
        if (albums.isEmpty()) {
            return new DashboardOverviewDTO(0, 0.0, "N/A", 0, "N/A", 0.0, 0.0);
        }

        int totalAlbums = albums.size();
        
        // Calculate week-over-week percentage change
        java.time.LocalDateTime oneWeekAgo = java.time.LocalDateTime.now().minusWeeks(1);
        long newAlbumsThisWeek = albums.stream().filter(a -> a.getCreatedAt() != null && a.getCreatedAt().isAfter(oneWeekAgo)).count();
        long oldAlbumsCount = totalAlbums - newAlbumsThisWeek;
        double albumsPercentageChange = oldAlbumsCount == 0 ? 100.0 : ((double) newAlbumsThisWeek / oldAlbumsCount) * 100.0;
        
        // Favourite Artist
        Map.Entry<String, Long> topArtist = getTopEntry(albums, SavedAlbum::getArtistName);
        String favArtist = topArtist != null ? topArtist.getKey() : "N/A";
        int favArtistCount = topArtist != null ? topArtist.getValue().intValue() : 0;
        
        // Favourite Genre
        Map.Entry<String, Long> topGenre = getTopEntry(albums, SavedAlbum::getGenre);
        String favGenre = topGenre != null ? topGenre.getKey() : "N/A";
        double favGenrePercentage = topGenre != null ? ((double) topGenre.getValue() / totalAlbums) * 100.0 : 0.0;
        
        // Average Rating
        double averageRating = albums.stream()
                .map(SavedAlbum::getUserRating)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        return new DashboardOverviewDTO(
                totalAlbums,
                Math.round(albumsPercentageChange * 10.0) / 10.0,
                favArtist,
                favArtistCount,
                favGenre,
                Math.round(favGenrePercentage * 10.0) / 10.0,
                Math.round(averageRating * 10.0) / 10.0
        );
    }

    @Override
    public List<ChartDataDTO> getGenreDistribution(UUID userId) {
        List<SavedAlbum> albums = savedAlbumRepository.findAllByUserId(userId);
        return getTopN(albums, SavedAlbum::getGenre, 10);
    }

    @Override
    public List<ChartDataDTO> getTopArtists(UUID userId) {
        List<SavedAlbum> albums = savedAlbumRepository.findAllByUserId(userId);
        return getTopN(albums, SavedAlbum::getArtistName, 5);
    }

    @Override
    public List<ChartDataDTO> getAlbumsByReleaseYear(UUID userId) {
        List<SavedAlbum> albums = savedAlbumRepository.findAllByUserId(userId);
        return albums.stream()
                .map(SavedAlbum::getReleaseDate)
                .filter(Objects::nonNull)
                .map(date -> date.length() >= 4 ? date.substring(0, 4) : "Unknown")
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new ChartDataDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ChartDataDTO> getRatingDistribution(UUID userId) {
        List<SavedAlbum> albums = savedAlbumRepository.findAllByUserId(userId);
        List<ChartDataDTO> ratingDistribution = albums.stream()
                .map(SavedAlbum::getUserRating)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(String::valueOf, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new ChartDataDTO(entry.getKey() + " Stars", entry.getValue()))
                .collect(Collectors.toList());

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
        return completeRatingDistribution;
    }

    private Map.Entry<String, Long> getTopEntry(List<SavedAlbum> albums, Function<SavedAlbum, String> keyExtractor) {
        return albums.stream()
                .map(keyExtractor)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);
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
