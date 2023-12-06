package com.slack.CrmBackend.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.slack.CrmBackend.dto.mapper.UserMapper;
import com.slack.CrmBackend.model.User;
import com.slack.CrmBackend.repository.UserRepository;

import jakarta.transaction.Transactional;

/**
 * Tests for User Dto
 */
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
@Transactional
public class UserDtoTests {

    /**
     * Partial mock for UserMapper
     */
    @Spy
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    UserRepository userRepository;

    /**
     * Logger : Display custom datas into console
     */
    private static Logger logger = LoggerFactory.getLogger(UserDtoTests.class);

    @Test
    void userDtoMapperTests() {
        logger.info(
                "############### [UserDtoTests][userDtoMapperTests] ###############");

        /**
         * Create new User
         */
        User user1 = new User("userName1", "firstName1", "lastName1", "user1@mmail.com");
        userRepository.save(user1);

        /**
         * Check UserDto values
         */
        UserDto userDto = userMapper.userToDto(user1);
        Assertions.assertThat(userDto)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", user1.getId())
                .hasFieldOrPropertyWithValue("userName", user1.getUserName())
                .hasFieldOrPropertyWithValue("firstName", user1.getFirstName())
                .hasFieldOrPropertyWithValue("lastName", user1.getLastName())
                .hasFieldOrPropertyWithValue("email", user1.getEmail());
    }

}
