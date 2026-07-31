package com.musiccatalog.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsResponseDTO {
    private DashboardOverviewDTO overview;
    private List<ChartDataDTO> topGenres;
    private List<ChartDataDTO> topArtists;
    private List<ChartDataDTO> releaseYears;
    private List<ChartDataDTO> ratingDistribution;
}
