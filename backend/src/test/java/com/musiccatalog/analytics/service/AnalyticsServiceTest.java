package com.musiccatalog.analytics.service;

import com.musiccatalog.analytics.dto.ChartDataDTO;
import com.musiccatalog.analytics.dto.DashboardOverviewDTO;
import com.musiccatalog.analytics.service.impl.AnalyticsServiceImpl;
import com.musiccatalog.library.entity.SavedAlbum;
import com.musiccatalog.library.repository.SavedAlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    @Mock
    private SavedAlbumRepository savedAlbumRepository;

    @InjectMocks
    private AnalyticsServiceImpl analyticsService;

    private UUID userId;
    private SavedAlbum album1;
    private SavedAlbum album2;
    private SavedAlbum album3;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        album1 = new SavedAlbum();
        album1.setArtistName("Artist A");
        album1.setGenre("Rock");
        album1.setUserRating(5);
        album1.setReleaseDate("2020-01-01");
        album1.setCreatedAt(LocalDateTime.now());

        album2 = new SavedAlbum();
        album2.setArtistName("Artist A");
        album2.setGenre("Pop");
        album2.setUserRating(4);
        album2.setReleaseDate("2020-05-01");
        album2.setCreatedAt(LocalDateTime.now().minusWeeks(2));

        album3 = new SavedAlbum();
        album3.setArtistName("Artist B");
        album3.setGenre("Rock");
        album3.setUserRating(3);
        album3.setReleaseDate("2019-12-01");
        album3.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void getOverview_ReturnsCorrectAggregations() {
        when(savedAlbumRepository.findAllByUserId(userId)).thenReturn(List.of(album1, album2, album3));

        DashboardOverviewDTO result = analyticsService.getOverview(userId);

        assertNotNull(result);
        assertEquals(3, result.getTotalAlbums());
        assertEquals("Artist A", result.getFavouriteArtist());
        assertEquals(2, result.getFavouriteArtistCount());
        assertEquals("Rock", result.getFavouriteGenre());
        assertEquals(4.0, result.getAverageRating());
    }

    @Test
    void getOverview_ReturnsEmptyDefaults() {
        when(savedAlbumRepository.findAllByUserId(userId)).thenReturn(Collections.emptyList());

        DashboardOverviewDTO result = analyticsService.getOverview(userId);

        assertNotNull(result);
        assertEquals(0, result.getTotalAlbums());
        assertEquals("N/A", result.getFavouriteArtist());
        assertEquals("N/A", result.getFavouriteGenre());
    }

    @Test
    void getGenreDistribution_ReturnsTopGenres() {
        when(savedAlbumRepository.findAllByUserId(userId)).thenReturn(List.of(album1, album2, album3));

        List<ChartDataDTO> result = analyticsService.getGenreDistribution(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Rock", result.get(0).getName());
        assertEquals(2L, result.get(0).getValue());
        assertEquals("Pop", result.get(1).getName());
        assertEquals(1L, result.get(1).getValue());
    }
}
