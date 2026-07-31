package com.musiccatalog.ai.service.impl;

import com.musiccatalog.ai.dto.RecommendationResponseDTO;
import com.musiccatalog.ai.provider.AIProvider;
import com.musiccatalog.ai.service.RecommendationService;
import com.musiccatalog.exception.BusinessException;
import com.musiccatalog.library.entity.SavedAlbum;
import com.musiccatalog.library.repository.SavedAlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final SavedAlbumRepository savedAlbumRepository;
    private final AIProvider aiProvider;

    @Override
    public RecommendationResponseDTO generateRecommendations(UUID userId) {
        List<SavedAlbum> library = savedAlbumRepository.findAllByUserId(userId);

        if (library.isEmpty()) {
            throw new BusinessException("Your library is empty. Save some albums first to get recommendations!", "EMPTY_LIBRARY");
        }

        // Build a prompt based on the user's library
        String librarySummary = library.stream()
                .map(album -> String.format("Title: %s, Artist: %s, Genre: %s, Rating: %d", 
                        album.getTitle(), album.getArtistName(), album.getGenre(), album.getUserRating()))
                .collect(Collectors.joining("; "));

        String prompt = "Analyze the following music library and provide insights and recommendations: " + librarySummary;

        // Call the abstracted AI provider
        return aiProvider.generateInsights(prompt);
    }
}
