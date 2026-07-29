package com.musiccatalog.auth.mapper;

import com.musiccatalog.auth.dto.AuthResponse.UserDto;
import com.musiccatalog.auth.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDto toUserDto(User user);
}
