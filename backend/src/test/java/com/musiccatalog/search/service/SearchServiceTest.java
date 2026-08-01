package com.musiccatalog.search.service;

import com.musiccatalog.search.client.ItunesClient;
import com.musiccatalog.search.dto.ItunesResponseDTO;
import com.musiccatalog.search.dto.ItunesResponseDTO.ItunesResult;
import com.musiccatalog.search.dto.SearchResponseDTO;
import com.musiccatalog.search.mapper.SearchMapper;
import com.musiccatalog.search.service.impl.SearchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private ItunesClient itunesClient;

    @Mock
    private SearchMapper searchMapper;

    @InjectMocks
    private SearchServiceImpl searchService;

    private ItunesResponseDTO itunesResponse;
    private ItunesResult itunesResult;
    private SearchResponseDTO searchResponse;

    @BeforeEach
    void setUp() {
        itunesResult = new ItunesResult();
        itunesResult.setCollectionId(12345L);
        itunesResult.setCollectionName("Test Album");

        itunesResponse = new ItunesResponseDTO();
        itunesResponse.setResults(List.of(itunesResult));

        searchResponse = new SearchResponseDTO();
        searchResponse.setId("12345");
        searchResponse.setTitle("Test Album");
    }

    @Test
    void search_ReturnsResults() {
        when(itunesClient.search("Test")).thenReturn(itunesResponse);
        when(searchMapper.toSearchResponseDTOList(anyList())).thenReturn(List.of(searchResponse));

        List<SearchResponseDTO> result = searchService.search("Test");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("12345", result.get(0).getId());
        verify(itunesClient).search("Test");
    }

    @Test
    void search_ReturnsEmptyListWhenClientReturnsNull() {
        when(itunesClient.search("Unknown")).thenReturn(null);

        List<SearchResponseDTO> result = searchService.search("Unknown");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(searchMapper, never()).toSearchResponseDTOList(anyList());
    }

    @Test
    void search_ReturnsEmptyListWhenResultsAreNull() {
        ItunesResponseDTO emptyResponse = new ItunesResponseDTO();
        emptyResponse.setResults(null);
        when(itunesClient.search("Empty")).thenReturn(emptyResponse);

        List<SearchResponseDTO> result = searchService.search("Empty");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(searchMapper, never()).toSearchResponseDTOList(anyList());
    }
}
