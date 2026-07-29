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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<SavedAlbumResponseDTO> getAllSavedAlbums(UUID userId) {
        List<SavedAlbum> albums = savedAlbumRepository.findAllByUserId(userId);
        return savedAlbumMapper.toDtoList(albums);
    }

    @Override
    @Transactional
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
    public SavedAlbumResponseDTO updateAlbum(UUID id, UUID userId, SavedAlbumRequestDTO requestDTO) {
        SavedAlbum existingAlbum = savedAlbumRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Saved album not found or you don't have permission to access it"));

        savedAlbumMapper.updateEntityFromDto(requestDTO, existingAlbum);
        
        SavedAlbum updated = savedAlbumRepository.save(existingAlbum);
        return savedAlbumMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteAlbum(UUID id, UUID userId) {
        SavedAlbum existingAlbum = savedAlbumRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Saved album not found or you don't have permission to access it"));

        savedAlbumRepository.delete(existingAlbum);
    }
}
