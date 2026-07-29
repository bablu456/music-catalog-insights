package com.musiccatalog.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItunesResponseDTO {
    private Integer resultCount;
    private List<ItunesResult> results;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItunesResult {
        private String wrapperType;
        private String kind;
        private Long artistId;
        private Long collectionId;
        private Long trackId;
        private String artistName;
        private String collectionName;
        private String trackName;
        private String previewUrl;
        private String artworkUrl30;
        private String artworkUrl60;
        private String artworkUrl100;
        private Double collectionPrice;
        private Double trackPrice;
        private String releaseDate;
        private String primaryGenreName;
    }
}
