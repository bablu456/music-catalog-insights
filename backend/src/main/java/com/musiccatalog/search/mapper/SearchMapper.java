package com.musiccatalog.search.mapper;

import com.musiccatalog.search.dto.ItunesResponseDTO;
import com.musiccatalog.search.dto.SearchResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SearchMapper {

    @Mapping(target = "id", expression = "java(String.valueOf(itunesResult.getCollectionId()))")
    @Mapping(target = "type", constant = "album")
    @Mapping(target = "title", source = "collectionName")
    @Mapping(target = "artist", source = "artistName")
    @Mapping(target = "album", source = "collectionName")
    @Mapping(target = "coverUrl", source = "artworkUrl100")
    @Mapping(target = "releaseDate", source = "releaseDate")
    @Mapping(target = "genre", source = "primaryGenreName")
    @Mapping(target = "trackCount", source = "trackCount")
    SearchResponseDTO toSearchResponseDTO(ItunesResponseDTO.ItunesResult itunesResult);

    List<SearchResponseDTO> toSearchResponseDTOList(List<ItunesResponseDTO.ItunesResult> results);
}
