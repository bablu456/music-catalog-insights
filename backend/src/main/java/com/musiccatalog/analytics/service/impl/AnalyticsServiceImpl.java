package com.musiccatalog.analytics.service.impl;

import com.musiccatalog.analytics.dto.AnalyticsResponseDTO;
import com.musiccatalog.analytics.dto.ChartDataDTO;
import com.musiccatalog.analytics.dto.DashboardOverviewDTO;
import com.musiccatalog.analytics.service.AnalyticsService;
import com.musiccatalog.library.repository.SavedAlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final SavedAlbumRepository savedAlbumRepository;

    @Override
    @Cacheable(value = "analyticsCache", key = "#userId")
    public DashboardOverviewDTO getOverview(UUID userId) {
        long totalAlbums = savedAlbumRepository.countByUserId(userId);
        if (totalAlbums == 0) {
            return new DashboardOverviewDTO(0, 0.0, "N/A", 0, "N/A", 0.0, 0.0);
        }

        // Calculate week-over-week percentage change
        java.time.LocalDateTime oneWeekAgo = java.time.LocalDateTime.now().minusWeeks(1);
        long newAlbumsThisWeek = savedAlbumRepository.countByUserIdAndCreatedAtAfter(userId, oneWeekAgo);
        long oldAlbumsCount = totalAlbums - newAlbumsThisWeek;
        double albumsPercentageChange = oldAlbumsCount == 0 ? 100.0 : ((double) newAlbumsThisWeek / oldAlbumsCount) * 100.0;
        
        // Favourite Artist
        List<Object[]> topArtists = savedAlbumRepository.findTopArtistsByUserId(userId, PageRequest.of(0, 1));
        String favArtist = "N/A";
        int favArtistCount = 0;
        if (!topArtists.isEmpty()) {
            Object[] row = topArtists.get(0);
            favArtist = (String) row[0];
            favArtistCount = ((Number) row[1]).intValue();
        }
        
        // Favourite Genre
        List<Object[]> topGenres = savedAlbumRepository.findTopGenresByUserId(userId, PageRequest.of(0, 1));
        String favGenre = "N/A";
        double favGenrePercentage = 0.0;
        if (!topGenres.isEmpty()) {
            Object[] row = topGenres.get(0);
            favGenre = (String) row[0];
            long genreCount = ((Number) row[1]).longValue();
            favGenrePercentage = ((double) genreCount / totalAlbums) * 100.0;
        }
        
        // Average Rating
        Double averageRatingOpt = savedAlbumRepository.getAverageRatingByUserId(userId);
        double averageRating = averageRatingOpt != null ? averageRatingOpt : 0.0;

        return new DashboardOverviewDTO(
                (int) totalAlbums,
                Math.round(albumsPercentageChange * 10.0) / 10.0,
                favArtist,
                favArtistCount,
                favGenre,
                Math.round(favGenrePercentage * 10.0) / 10.0,
                Math.round(averageRating * 10.0) / 10.0
        );
    }

    @Override
    @Cacheable(value = "analyticsCache", key = "#userId")
    public List<ChartDataDTO> getGenreDistribution(UUID userId) {
        return savedAlbumRepository.findTopGenresByUserId(userId, PageRequest.of(0, 10))
                .stream()
                .map(row -> new ChartDataDTO((String) row[0], ((Number) row[1]).longValue()))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "analyticsCache", key = "#userId")
    public List<ChartDataDTO> getTopArtists(UUID userId) {
        return savedAlbumRepository.findTopArtistsByUserId(userId, PageRequest.of(0, 5))
                .stream()
                .map(row -> new ChartDataDTO((String) row[0], ((Number) row[1]).longValue()))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "analyticsCache", key = "#userId")
    public List<ChartDataDTO> getAlbumsByReleaseYear(UUID userId) {
        return savedAlbumRepository.findAlbumsByReleaseYear(userId)
                .stream()
                .map(row -> new ChartDataDTO((String) row[0], ((Number) row[1]).longValue()))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "analyticsCache", key = "#userId")
    public List<ChartDataDTO> getRatingDistribution(UUID userId) {
        List<Object[]> queryResults = savedAlbumRepository.findRatingDistribution(userId);
        Map<String, Long> countsMap = queryResults.stream()
                .collect(Collectors.toMap(
                        row -> row[0] + " Stars",
                        row -> ((Number) row[1]).longValue()
                ));

        List<ChartDataDTO> completeRatingDistribution = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            String label = i + " Stars";
            completeRatingDistribution.add(new ChartDataDTO(label, countsMap.getOrDefault(label, 0L)));
        }
        return completeRatingDistribution;
    }
}
