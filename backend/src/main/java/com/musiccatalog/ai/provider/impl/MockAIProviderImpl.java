package com.musiccatalog.ai.provider.impl;

import com.musiccatalog.ai.dto.RecommendationResponseDTO;
import com.musiccatalog.ai.provider.AIProvider;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * A mock implementation of the AIProvider.
 * Useful for development or when a real LLM API key is not provided.
 */
@Service
public class MockAIProviderImpl implements AIProvider {

    @Override
    public RecommendationResponseDTO generateInsights(String prompt) {
        // In a real implementation, 'prompt' would be sent to the LLM.
        // For now, we simulate an AI response.
        
        return RecommendationResponseDTO.builder()
                .genreSummary("Based on your library, you have a strong preference for alternative rock and electronic music.")
                .favouriteArtist("You seem to enjoy 'Coldplay' the most.")
                .listeningTrends("You tend to listen to albums released between 2000 and 2015, showing a nostalgic trend.")
                .albumRecommendations(List.of(
                        "Radiohead - OK Computer",
                        "The Killers - Hot Fuss",
                        "Daft Punk - Discovery"
                ))
                .build();
    }
}
