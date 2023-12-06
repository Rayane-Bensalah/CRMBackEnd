package com.slack.CrmBackend.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.slack.CrmBackend.dto.UserDto;
import com.slack.CrmBackend.dto.UserDtoSlim;
import com.slack.CrmBackend.model.User;

/**
 * User Dto Mapper
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {
        User.class, UserDto.class, UserDtoSlim.class })
public interface UserMapper {
    /**
     * Providing access to the mapper implementation and avoid test errors
     */
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Convert User entity to User DTO
     *
     * @param user
     * @return userDto
     */
    UserDto userToDto(User user);

    /**
     * Convert User entity to User DTO Slim
     *
     * @param user
     * @return userDtoSlim
     */
    UserDtoSlim userToSlimDto(User user);

    /**
     * Convert Users List to Users DTO List
     * 
     * @param users
     * @return userDto List
     */
    List<UserDto> usersToUsersDto(List<User> users);

    /**
     * Convert UserDto to User
     * 
     * @param userDto
     * @return user
     */
    @Mapping(target = "messages", ignore = true)
    User userDtoToUser(UserDto userDto);
}
