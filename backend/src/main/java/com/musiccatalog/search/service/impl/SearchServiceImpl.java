package com.musiccatalog.search.service.impl;

import com.musiccatalog.search.client.ItunesClient;
import com.musiccatalog.search.dto.ItunesResponseDTO;
import com.musiccatalog.search.dto.SearchResponseDTO;
import com.musiccatalog.search.mapper.SearchMapper;
import com.musiccatalog.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final ItunesClient itunesClient;
    private final SearchMapper searchMapper;

    @Override
    @Cacheable(value = "searchCache", key = "#query", unless = "#result == null or #result.isEmpty()")
    public List<SearchResponseDTO> search(String query) {
        ItunesResponseDTO itunesResponse = itunesClient.search(query);

        if (itunesResponse == null || itunesResponse.getResults() == null) {
            return Collections.emptyList();
        }

        return searchMapper.toSearchResponseDTOList(itunesResponse.getResults());
    }
}
