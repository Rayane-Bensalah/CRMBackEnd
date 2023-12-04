package com.slack.CrmBackend.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.slack.CrmBackend.dto.UserDto;
import com.slack.CrmBackend.model.User;

/**
 * User Dto Mapper
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {
        User.class, UserDto.class })
public interface UserMapper {
    /**
     * Providing access to the mapper implementation and avoid test errors
     */
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Convert User entity to User DTO
     *
     * @param User user
     * @return UserDto
     */
    UserDto userEntityToDto(User user);
}
