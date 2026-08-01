package com.musiccatalog.library.service;

import com.musiccatalog.auth.entity.User;
import com.musiccatalog.auth.repository.UserRepository;
import com.musiccatalog.exception.DuplicateResourceException;
import com.musiccatalog.exception.ResourceNotFoundException;
import com.musiccatalog.library.dto.SavedAlbumRequestDTO;
import com.musiccatalog.library.dto.SavedAlbumResponseDTO;
import com.musiccatalog.library.entity.SavedAlbum;
import com.musiccatalog.library.mapper.SavedAlbumMapper;
import com.musiccatalog.library.repository.SavedAlbumRepository;
import com.musiccatalog.library.service.impl.LibraryServiceImpl;
import com.musiccatalog.common.PagedResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {

    @Mock
    private SavedAlbumRepository savedAlbumRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SavedAlbumMapper savedAlbumMapper;

    @InjectMocks
    private LibraryServiceImpl libraryService;

    private UUID userId;
    private User user;
    private SavedAlbumRequestDTO requestDTO;
    private SavedAlbum savedAlbum;
    private SavedAlbumResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        user = new User();
        user.setId(userId);

        requestDTO = new SavedAlbumRequestDTO();
        requestDTO.setAppleCatalogId("12345");
        requestDTO.setTitle("Test Album");

        savedAlbum = new SavedAlbum();
        savedAlbum.setId(UUID.randomUUID());
        savedAlbum.setAppleCatalogId("12345");
        savedAlbum.setTitle("Test Album");
        savedAlbum.setUser(user);

        responseDTO = new SavedAlbumResponseDTO();
        responseDTO.setId(savedAlbum.getId());
        responseDTO.setAppleCatalogId("12345");
        responseDTO.setTitle("Test Album");
    }

    @Test
    void getAllSavedAlbums_ReturnsPagedResponse() {
        Page<SavedAlbum> page = new PageImpl<>(List.of(savedAlbum));
        when(savedAlbumRepository.findAllByUserIdOrderByCreatedAtDesc(eq(userId), any(PageRequest.class))).thenReturn(page);
        when(savedAlbumMapper.toDto(any(SavedAlbum.class))).thenReturn(responseDTO);

        PagedResponseDTO<SavedAlbumResponseDTO> result = libraryService.getAllSavedAlbums(userId, 0, 20);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("12345", result.getContent().get(0).getAppleCatalogId());
        verify(savedAlbumRepository).findAllByUserIdOrderByCreatedAtDesc(eq(userId), any(PageRequest.class));
    }

    @Test
    void saveAlbum_Success() {
        when(savedAlbumRepository.existsByAppleCatalogIdAndUserId("12345", userId)).thenReturn(false);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(savedAlbumMapper.toEntity(requestDTO)).thenReturn(savedAlbum);
        when(savedAlbumRepository.save(any(SavedAlbum.class))).thenReturn(savedAlbum);
        when(savedAlbumMapper.toDto(savedAlbum)).thenReturn(responseDTO);

        SavedAlbumResponseDTO result = libraryService.saveAlbum(userId, requestDTO);

        assertNotNull(result);
        assertEquals("Test Album", result.getTitle());
        verify(savedAlbumRepository).save(any(SavedAlbum.class));
    }

    @Test
    void saveAlbum_ThrowsDuplicateResourceException() {
        when(savedAlbumRepository.existsByAppleCatalogIdAndUserId("12345", userId)).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> libraryService.saveAlbum(userId, requestDTO));
        verify(savedAlbumRepository, never()).save(any(SavedAlbum.class));
    }

    @Test
    void deleteAlbum_Success() {
        UUID albumId = savedAlbum.getId();
        when(savedAlbumRepository.findByIdAndUserId(albumId, userId)).thenReturn(Optional.of(savedAlbum));

        libraryService.deleteAlbum(albumId, userId);

        verify(savedAlbumRepository).delete(savedAlbum);
    }

    @Test
    void deleteAlbum_ThrowsResourceNotFoundException() {
        UUID albumId = UUID.randomUUID();
        when(savedAlbumRepository.findByIdAndUserId(albumId, userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> libraryService.deleteAlbum(albumId, userId));
        verify(savedAlbumRepository, never()).delete(any(SavedAlbum.class));
    }
}
