package com.musiccatalog.search.controller;

import com.musiccatalog.activity.service.SearchHistoryService;
import com.musiccatalog.security.CustomUserDetails;
import com.musiccatalog.common.ApiResponse;
import com.musiccatalog.search.dto.SearchResponseDTO;
import com.musiccatalog.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
@Validated
@Tag(name = "Search", description = "Endpoints for searching music catalog")
public class SearchController {

    private final SearchService searchService;
    private final SearchHistoryService searchHistoryService;

    @GetMapping
    @Operation(summary = "Search for songs, albums, or artists")
    public ApiResponse<List<SearchResponseDTO>> search(
            @RequestParam @NotBlank(message = "Search query must not be blank") String query,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {

        List<SearchResponseDTO> results = searchService.search(query);
        
        if (userDetails != null && results != null && !results.isEmpty()) {
            searchHistoryService.saveSearchQuery(userDetails.getId(), query);
        }
        
        return ApiResponse.ok(results, request.getRequestURI());
    }
}
