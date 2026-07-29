package com.musiccatalog.search.mapper;

import com.musiccatalog.search.dto.ItunesResponseDTO;
import com.musiccatalog.search.dto.SearchResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SearchMapper {

    @Mapping(target = "id", expression = "java(resolveId(itunesResult))")
    @Mapping(target = "type", expression = "java(resolveType(itunesResult))")
    @Mapping(target = "title", source = "trackName", defaultValue = "")
    @Mapping(target = "artist", source = "artistName")
    @Mapping(target = "album", source = "collectionName")
    @Mapping(target = "coverUrl", source = "artworkUrl100")
    @Mapping(target = "previewUrl", source = "previewUrl")
    @Mapping(target = "releaseDate", source = "releaseDate")
    @Mapping(target = "genre", source = "primaryGenreName")
    SearchResponseDTO toSearchResponseDTO(ItunesResponseDTO.ItunesResult itunesResult);

    List<SearchResponseDTO> toSearchResponseDTOList(List<ItunesResponseDTO.ItunesResult> results);

    default String resolveId(ItunesResponseDTO.ItunesResult result) {
        if (result.getTrackId() != null) return String.valueOf(result.getTrackId());
        if (result.getCollectionId() != null) return String.valueOf(result.getCollectionId());
        if (result.getArtistId() != null) return String.valueOf(result.getArtistId());
        return null;
    }

    default String resolveType(ItunesResponseDTO.ItunesResult result) {
        if ("song".equals(result.getKind())) return "song";
        if ("album".equals(result.getWrapperType()) || "collection".equals(result.getWrapperType())) return "album";
        if ("artist".equals(result.getWrapperType())) return "artist";
        return result.getKind() != null ? result.getKind() : result.getWrapperType();
    }
}
