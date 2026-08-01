package com.musiccatalog.ai.service;

import com.musiccatalog.ai.dto.RecommendationResponseDTO;
import com.musiccatalog.ai.provider.AIProvider;
import com.musiccatalog.ai.provider.PromptBuilder;
import com.musiccatalog.ai.service.impl.RecommendationServiceImpl;
import com.musiccatalog.exception.BusinessException;
import com.musiccatalog.library.entity.SavedAlbum;
import com.musiccatalog.library.repository.SavedAlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {

    @Mock
    private SavedAlbumRepository savedAlbumRepository;

    @Mock
    private AIProvider aiProvider;

    @Mock
    private PromptBuilder promptBuilder;

    @InjectMocks
    private RecommendationServiceImpl recommendationService;

    private UUID userId;
    private SavedAlbum album;
    private RecommendationResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        album = new SavedAlbum();
        album.setArtistName("Artist A");
        album.setTitle("Album A");

        responseDTO = new RecommendationResponseDTO();
        responseDTO.setFavouriteArtist("Artist A");
    }

    @Test
    void generateRecommendations_Success() {
        when(savedAlbumRepository.findAllByUserId(userId)).thenReturn(List.of(album));
        when(promptBuilder.buildPrompt(anyList())).thenReturn("Prompt");
        when(aiProvider.generateInsights("Prompt")).thenReturn(responseDTO);

        RecommendationResponseDTO result = recommendationService.generateRecommendations(userId);

        assertNotNull(result);
        assertEquals("Artist A", result.getFavouriteArtist());
        verify(aiProvider).generateInsights("Prompt");
    }

    @Test
    void generateRecommendations_ThrowsWhenLibraryEmpty() {
        when(savedAlbumRepository.findAllByUserId(userId)).thenReturn(Collections.emptyList());

        assertThrows(BusinessException.class, () -> recommendationService.generateRecommendations(userId));
        verify(promptBuilder, never()).buildPrompt(anyList());
        verify(aiProvider, never()).generateInsights(anyString());
    }
}
