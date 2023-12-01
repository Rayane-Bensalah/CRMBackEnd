package com.example.CrmBackend.repository;

import com.example.CrmBackend.model.Channel;
import com.example.CrmBackend.model.Message;
import com.example.CrmBackend.model.User;
import com.example.CrmBackend.repository.MessageRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Tests for MessageRepository
 * tested save() and findAll()
 */
@SpringBootTest
class MessageRepositoryTests {

    @Autowired
    MessageRepository MessageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChannelRepository channelRepository;

    @Test
    void MessageRepository_SaveMessage() {

        // Create test user and save it
        User testUser = new User("testUsername", "testFirstname", "testLastname", "test@email.com");
        userRepository.save(testUser);

        // Create test channel and save it
        Channel testChannel = new Channel("testChannelname", false);
        channelRepository.save(testChannel);

        //Create test message with user and channel created before
        Message testMessage = new Message("testContent", testUser.getId(), testChannel.getId());
        MessageRepository.save(testMessage);

        // Assert Message is created and with correct id
        Assertions.assertThat(testMessage).isNotNull();
        Assertions.assertThat(testMessage.getId()).isGreaterThan(0);
    }

    @Test
    public void MessageRepository_GetAllMessages() {
        // Create and save user & channel

        User testUser = new User("testUsername", "testFirstname", "testLastname", "test@email.com");
        userRepository.save(testUser);

        Channel testChannel = new Channel("testChannelname", false);
        channelRepository.save(testChannel);

        //Create 2 test messages with user and channel created before
        Message testMessage1 = new Message("testContent1", testUser.getId(), testChannel.getId());
        Message testMessage2 = new Message("testContent2", testUser.getId(), testChannel.getId());
        MessageRepository.save(testMessage1);
        MessageRepository.save(testMessage2);

        // Get all Messages
        List<Message> MessageList = MessageRepository.findAll();

        // Assert both Messages are created and saved
        Assertions.assertThat(MessageList).isNotNull();
        Assertions.assertThat(MessageList.size()).isEqualTo(2);
    }

}