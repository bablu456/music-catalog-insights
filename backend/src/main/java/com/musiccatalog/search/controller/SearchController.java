package com.musiccatalog.search.controller;

import com.musiccatalog.common.ApiResponse;
import com.musiccatalog.search.dto.SearchResponseDTO;
import com.musiccatalog.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    @Operation(summary = "Search for songs, albums, or artists")
    public ApiResponse<List<SearchResponseDTO>> search(
            @RequestParam @NotBlank(message = "Search query must not be blank") String query,
            HttpServletRequest request) {

        List<SearchResponseDTO> results = searchService.search(query);
        return ApiResponse.ok(results, request.getRequestURI());
    }
}
