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
    private double albumsPercentageChange;
    private String favouriteArtist;
    private int favouriteArtistCount;
    private String favouriteGenre;
    private double favouriteGenrePercentage;
    private double averageRating;
}
