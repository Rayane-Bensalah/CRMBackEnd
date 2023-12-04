package com.slack.CrmBackend.dto;

import java.util.List;

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

import com.slack.CrmBackend.dto.mapper.ChannelMapper;
import com.slack.CrmBackend.dto.mapper.MessageMapper;
import com.slack.CrmBackend.dto.mapper.UserMapper;
import com.slack.CrmBackend.model.Channel;
import com.slack.CrmBackend.model.Message;
import com.slack.CrmBackend.model.User;
import com.slack.CrmBackend.repository.ChannelRepository;
import com.slack.CrmBackend.repository.MessageRepository;
import com.slack.CrmBackend.repository.UserRepository;

import jakarta.transaction.Transactional;

/**
 * Tests for Message Dto
 * tested save() and findAll()
 */
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
@Transactional
public class MessageDtoTests {

    /**
     * Partials mock for MessageMapper
     */
    @Spy
    MessageMapper messageMapper = Mappers.getMapper(MessageMapper.class);

    /**
     * Partial mock for UserMapper
     */
    @Spy
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    /**
     * Partial mock for ChannelMapper
     */
    @Spy
    ChannelMapper channelMapper = Mappers.getMapper(ChannelMapper.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    MessageRepository messageRepository;

    /**
     * Logger : Display custom datas into console
     */
    private static Logger logger = LoggerFactory.getLogger(ChannelDtoTests.class);

    @Test
    void messageDtoMapperTests() {
        logger.info(
                "############### [MessageDtoTests][messageDtoMapperTests] ###############");

        /**
         * Create One User
         */
        User user = new User("userName", "firstName", "lastName", "user@mmail.com");
        userRepository.save(user);

        /**
         * Create Main Channel
         */
        Channel channel = new Channel("General", true);
        channelRepository.save(channel);

        /**
         * Add Users messages to main Channel
         */
        Message message1 = new Message("Hello", user, channel);
        messageRepository.save(message1);
        Message message2 = new Message("Comment allez-vous ?", user, channel);
        messageRepository.save(message2);

        List<MessageDto> messages = messageMapper.messagesToDto(messageRepository.findByChannel(channel));

        Assertions.assertThat(messages).isNotNull();
        Assertions.assertThat(messages.size()).isEqualTo(2);
        Assertions.assertThat(messages.get(0).getContent()).isEqualTo("Hello");
        Assertions.assertThat(messages.get(1).getContent()).isEqualTo("Comment allez-vous ?");
    }
}
