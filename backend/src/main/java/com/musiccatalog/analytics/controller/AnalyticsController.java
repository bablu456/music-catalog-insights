package com.musiccatalog.analytics.controller;

import com.musiccatalog.analytics.dto.AnalyticsResponseDTO;
import com.musiccatalog.analytics.service.AnalyticsService;
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
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics", description = "Endpoints for generating user library analytics and charts")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping
    @Operation(summary = "Get analytics data for the authenticated user")
    public ApiResponse<AnalyticsResponseDTO> getAnalytics(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {

        AnalyticsResponseDTO response = analyticsService.getAnalytics(userDetails.getId());
        return ApiResponse.ok(response, request.getRequestURI());
    }
}
