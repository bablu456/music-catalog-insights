package com.musiccatalog.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardOverviewDTO {
    private int totalAlbums;
    private int totalArtists;
    private int totalGenres;
    private double averageRating;
}
