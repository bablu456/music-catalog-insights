package com.musiccatalog.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponseDTO {
    private String id;
    private String type; // e.g. "song", "album", "artist"
    private String title;
    private String artist;
    private String album;
    private String coverUrl;
    private String previewUrl;
    private String releaseDate;
    private String genre;
    private Integer trackCount;
}
