package com.musiccatalog.library.controller;

import com.musiccatalog.common.ApiResponse;
import com.musiccatalog.library.dto.SavedAlbumRequestDTO;
import com.musiccatalog.library.dto.SavedAlbumResponseDTO;
import com.musiccatalog.library.service.LibraryService;
import com.musiccatalog.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/library")
@RequiredArgsConstructor
@Tag(name = "Library", description = "Endpoints for managing the user's personal music library")
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping
    @Operation(summary = "Get all saved albums for the authenticated user")
    public ApiResponse<List<SavedAlbumResponseDTO>> getAllAlbums(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request) {
        
        List<SavedAlbumResponseDTO> albums = libraryService.getAllSavedAlbums(userDetails.getId());
        return ApiResponse.ok(albums, request.getRequestURI());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Save an album to the library")
    public ApiResponse<SavedAlbumResponseDTO> saveAlbum(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody SavedAlbumRequestDTO requestDTO,
            HttpServletRequest request) {

        SavedAlbumResponseDTO responseDTO = libraryService.saveAlbum(userDetails.getId(), requestDTO);
        return ApiResponse.success(responseDTO, HttpStatus.CREATED.value(), request.getRequestURI());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a saved album (e.g. rating or notes)")
    public ApiResponse<SavedAlbumResponseDTO> updateAlbum(
            @PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody SavedAlbumRequestDTO requestDTO,
            HttpServletRequest request) {

        SavedAlbumResponseDTO responseDTO = libraryService.updateAlbum(id, userDetails.getId(), requestDTO);
        return ApiResponse.ok(responseDTO, request.getRequestURI());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove an album from the library")
    public void deleteAlbum(
            @PathVariable UUID id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        libraryService.deleteAlbum(id, userDetails.getId());
    }
}
