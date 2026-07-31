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

    @GetMapping("/overview")
    @Operation(summary = "Get dashboard overview metrics")
    public ApiResponse<com.musiccatalog.analytics.dto.DashboardOverviewDTO> getOverview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        return ApiResponse.ok(analyticsService.getOverview(userDetails.getId()), request.getRequestURI());
    }

    @GetMapping("/genres")
    @Operation(summary = "Get genre distribution chart data")
    public ApiResponse<java.util.List<com.musiccatalog.analytics.dto.ChartDataDTO>> getGenres(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        return ApiResponse.ok(analyticsService.getGenreDistribution(userDetails.getId()), request.getRequestURI());
    }

    @GetMapping("/artists")
    @Operation(summary = "Get top artists chart data")
    public ApiResponse<java.util.List<com.musiccatalog.analytics.dto.ChartDataDTO>> getArtists(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        return ApiResponse.ok(analyticsService.getTopArtists(userDetails.getId()), request.getRequestURI());
    }

    @GetMapping("/releases")
    @Operation(summary = "Get albums by release year chart data")
    public ApiResponse<java.util.List<com.musiccatalog.analytics.dto.ChartDataDTO>> getReleases(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        return ApiResponse.ok(analyticsService.getAlbumsByReleaseYear(userDetails.getId()), request.getRequestURI());
    }

    @GetMapping("/ratings")
    @Operation(summary = "Get rating distribution chart data")
    public ApiResponse<java.util.List<com.musiccatalog.analytics.dto.ChartDataDTO>> getRatings(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        return ApiResponse.ok(analyticsService.getRatingDistribution(userDetails.getId()), request.getRequestURI());
    }
}
