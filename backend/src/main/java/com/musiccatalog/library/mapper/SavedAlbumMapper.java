package com.musiccatalog.library.mapper;

import com.musiccatalog.library.dto.SavedAlbumRequestDTO;
import com.musiccatalog.library.dto.SavedAlbumResponseDTO;
import com.musiccatalog.library.entity.SavedAlbum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SavedAlbumMapper {
    
    SavedAlbumResponseDTO toDto(SavedAlbum entity);
    
    List<SavedAlbumResponseDTO> toDtoList(List<SavedAlbum> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SavedAlbum toEntity(SavedAlbumRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "appleCatalogId", ignore = true) // Disallow updating the core catalog ID
    void updateEntityFromDto(SavedAlbumRequestDTO dto, @MappingTarget SavedAlbum entity);
}
