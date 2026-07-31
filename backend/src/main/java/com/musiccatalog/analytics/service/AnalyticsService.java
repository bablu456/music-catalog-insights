package com.musiccatalog.analytics.service;

import com.musiccatalog.analytics.dto.AnalyticsResponseDTO;

import java.util.UUID;

public interface AnalyticsService {
    com.musiccatalog.analytics.dto.DashboardOverviewDTO getOverview(UUID userId);
    java.util.List<com.musiccatalog.analytics.dto.ChartDataDTO> getGenreDistribution(UUID userId);
    java.util.List<com.musiccatalog.analytics.dto.ChartDataDTO> getTopArtists(UUID userId);
    java.util.List<com.musiccatalog.analytics.dto.ChartDataDTO> getAlbumsByReleaseYear(UUID userId);
    java.util.List<com.musiccatalog.analytics.dto.ChartDataDTO> getRatingDistribution(UUID userId);
}
