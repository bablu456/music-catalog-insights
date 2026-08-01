package com.musiccatalog.activity.controller;

import com.musiccatalog.activity.dto.TimelineEventDTO;
import com.musiccatalog.activity.service.ActivityService;
import com.musiccatalog.security.CustomUserDetails;
import com.musiccatalog.common.PagedResponseDTO;
import com.musiccatalog.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activity")
@RequiredArgsConstructor
@Tag(name = "Activity", description = "Endpoints for user recent activity")
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping("/recent")
    @Operation(summary = "Get unified recent activity timeline for user")
    public ApiResponse<PagedResponseDTO<TimelineEventDTO>> getRecentActivity(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            HttpServletRequest request) {
        PagedResponseDTO<TimelineEventDTO> events = activityService.getRecentActivity(userDetails.getId(), page, size);
        return ApiResponse.ok(events, request.getRequestURI());
    }
}
