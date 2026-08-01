package com.musiccatalog.library.service;

import com.musiccatalog.library.dto.SavedAlbumRequestDTO;
import com.musiccatalog.library.dto.SavedAlbumResponseDTO;

import java.util.List;
import java.util.UUID;

import com.musiccatalog.common.PagedResponseDTO;

public interface LibraryService {
    PagedResponseDTO<SavedAlbumResponseDTO> getAllSavedAlbums(UUID userId, int page, int size);
    SavedAlbumResponseDTO saveAlbum(UUID userId, SavedAlbumRequestDTO requestDTO);
    SavedAlbumResponseDTO updateAlbum(UUID id, UUID userId, SavedAlbumRequestDTO requestDTO);
    void deleteAlbum(UUID id, UUID userId);
}
