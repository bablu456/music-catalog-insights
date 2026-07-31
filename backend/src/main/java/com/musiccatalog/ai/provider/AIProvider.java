package com.musiccatalog.ai.provider;

import com.musiccatalog.ai.dto.RecommendationResponseDTO;

/**
 * Interface to abstract the underlying AI service (e.g. OpenAI, Google Gemini).
 */
public interface AIProvider {
    /**
     * Generates music recommendations and insights based on a constructed prompt.
     * 
     * @param prompt The summarized prompt of the user's library.
     * @return A structured recommendation response.
     */
    RecommendationResponseDTO generateInsights(String prompt);
}
