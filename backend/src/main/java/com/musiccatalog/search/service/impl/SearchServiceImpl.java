package com.musiccatalog.search.service.impl;

import com.musiccatalog.search.client.ItunesClient;
import com.musiccatalog.search.dto.ItunesResponseDTO;
import com.musiccatalog.search.dto.SearchResponseDTO;
import com.musiccatalog.search.mapper.SearchMapper;
import com.musiccatalog.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final ItunesClient itunesClient;
    private final SearchMapper searchMapper;

    @Override
    public List<SearchResponseDTO> search(String query, String type) {
        ItunesResponseDTO itunesResponse = itunesClient.search(query, type);

        if (itunesResponse == null || itunesResponse.getResults() == null) {
            return Collections.emptyList();
        }

        return searchMapper.toSearchResponseDTOList(itunesResponse.getResults());
    }
}
