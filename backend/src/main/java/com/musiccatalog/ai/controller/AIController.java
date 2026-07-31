package com.musiccatalog.ai.controller;

import com.musiccatalog.ai.dto.RecommendationResponseDTO;
import com.musiccatalog.ai.service.RecommendationService;
import com.musiccatalog.common.ApiResponse;
import com.musiccatalog.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
@Tag(name = "AI Recommendations", description = "Endpoints for generating AI-driven insights from the user's library")
public class AIController {

    private final RecommendationService recommendationService;

    @GetMapping("/recommendations")
    @Operation(summary = "Generate AI recommendations based on saved albums")
    public ApiResponse<RecommendationResponseDTO> getRecommendations(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {

        RecommendationResponseDTO response = recommendationService.generateRecommendations(userDetails.getId());
        return ApiResponse.ok(response, request.getRequestURI());
    }
}
