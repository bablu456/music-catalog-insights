package com.musiccatalog.library.service.impl;

import com.musiccatalog.auth.entity.User;
import com.musiccatalog.auth.repository.UserRepository;
import com.musiccatalog.exception.DuplicateResourceException;
import com.musiccatalog.exception.ResourceNotFoundException;
import com.musiccatalog.library.dto.SavedAlbumRequestDTO;
import com.musiccatalog.library.dto.SavedAlbumResponseDTO;
import com.musiccatalog.library.entity.SavedAlbum;
import com.musiccatalog.library.mapper.SavedAlbumMapper;
import com.musiccatalog.library.repository.SavedAlbumRepository;
import com.musiccatalog.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.musiccatalog.common.PagedResponseDTO;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final SavedAlbumRepository savedAlbumRepository;
    private final UserRepository userRepository;
    private final SavedAlbumMapper savedAlbumMapper;

    @Override
    @Transactional(readOnly = true)
    public PagedResponseDTO<SavedAlbumResponseDTO> getAllSavedAlbums(UUID userId, int page, int size) {
        Page<SavedAlbum> albumsPage = savedAlbumRepository.findAllByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(page, size));
        Page<SavedAlbumResponseDTO> dtoPage = albumsPage.map(savedAlbumMapper::toDto);
        return PagedResponseDTO.of(dtoPage);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"analyticsOverviewCache", "analyticsGenresCache", "analyticsArtistsCache", "analyticsReleasesCache", "analyticsRatingsCache", "recommendationCache"}, key = "#userId")
    public SavedAlbumResponseDTO saveAlbum(UUID userId, SavedAlbumRequestDTO requestDTO) {
        if (savedAlbumRepository.existsByAppleCatalogIdAndUserId(requestDTO.getAppleCatalogId(), userId)) {
            throw new DuplicateResourceException("Album with Apple Catalog ID " + requestDTO.getAppleCatalogId() + " is already saved.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        SavedAlbum savedAlbum = savedAlbumMapper.toEntity(requestDTO);
        savedAlbum.setUser(user);

        SavedAlbum result = savedAlbumRepository.save(savedAlbum);
        return savedAlbumMapper.toDto(result);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"analyticsOverviewCache", "analyticsGenresCache", "analyticsArtistsCache", "analyticsReleasesCache", "analyticsRatingsCache", "recommendationCache"}, key = "#userId")
    public SavedAlbumResponseDTO updateAlbum(UUID id, UUID userId, SavedAlbumRequestDTO requestDTO) {
        SavedAlbum existingAlbum = savedAlbumRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Saved album not found or you don't have permission to access it"));

        savedAlbumMapper.updateEntityFromDto(requestDTO, existingAlbum);
        
        SavedAlbum updated = savedAlbumRepository.save(existingAlbum);
        return savedAlbumMapper.toDto(updated);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"analyticsOverviewCache", "analyticsGenresCache", "analyticsArtistsCache", "analyticsReleasesCache", "analyticsRatingsCache", "recommendationCache"}, key = "#userId")
    public void deleteAlbum(UUID id, UUID userId) {
        SavedAlbum existingAlbum = savedAlbumRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Saved album not found or you don't have permission to access it"));

        savedAlbumRepository.delete(existingAlbum);
    }
}
