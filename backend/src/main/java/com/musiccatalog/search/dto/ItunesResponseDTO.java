package com.musiccatalog.search.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItunesResponseDTO {
    private Integer resultCount;
    private List<ItunesResult> results;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ItunesResult {
        private Long collectionId;
        private String collectionName;
        private String artistName;
        private String primaryGenreName;
        private String releaseDate;
        private Integer trackCount;
        private String artworkUrl100;
    }
}
