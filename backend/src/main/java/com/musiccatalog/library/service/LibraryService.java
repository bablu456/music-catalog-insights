package com.musiccatalog.library.service;

import com.musiccatalog.library.dto.SavedAlbumRequestDTO;
import com.musiccatalog.library.dto.SavedAlbumResponseDTO;

import java.util.List;
import java.util.UUID;

public interface LibraryService {
    List<SavedAlbumResponseDTO> getAllSavedAlbums(UUID userId);
    SavedAlbumResponseDTO saveAlbum(UUID userId, SavedAlbumRequestDTO requestDTO);
    SavedAlbumResponseDTO updateAlbum(UUID id, UUID userId, SavedAlbumRequestDTO requestDTO);
    void deleteAlbum(UUID id, UUID userId);
}
