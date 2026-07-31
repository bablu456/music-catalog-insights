package com.musiccatalog.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponseDTO {
    private String genreSummary;
    private String favouriteArtist;
    private String listeningTrends;
    private String interestingObservations;
    private List<String> albumRecommendations;
}
