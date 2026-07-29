package com.musiccatalog.search.service;

import com.musiccatalog.search.dto.SearchResponseDTO;

import java.util.List;

public interface SearchService {
    List<SearchResponseDTO> search(String query, String type);
}
