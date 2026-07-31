package com.musiccatalog.ai.service;

import com.musiccatalog.ai.dto.RecommendationResponseDTO;

import java.util.UUID;

public interface RecommendationService {
    RecommendationResponseDTO generateRecommendations(UUID userId);
}
