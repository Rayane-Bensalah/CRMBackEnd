package com.example.CrmBackend.repository;

import com.example.CrmBackend.model.Channel;
import com.example.CrmBackend.model.Message;
import com.example.CrmBackend.model.User;
import com.example.CrmBackend.repository.MessageRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Tests for MessageRepository
 * tested save() and findAll()
 */
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // Simulate a Database
@SpringBootTest
class MessageRepositoryTests {

    @Autowired
    MessageRepository messageRepository;

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
        Message testMessage = new Message("testContent", testUser, testChannel);
        messageRepository.save(testMessage);

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
        Message testMessage1 = new Message("testContent1", testUser, testChannel);
        Message testMessage2 = new Message("testContent2", testUser, testChannel);
        messageRepository.save(testMessage1);
        messageRepository.save(testMessage2);

        // Get all Messages
        List<Message> MessageList = messageRepository.findAll();

        // Assert both Messages are created and saved
        Assertions.assertThat(MessageList).isNotNull();
        Assertions.assertThat(MessageList.size()).isEqualTo(2);
    }

    /**
     * test update
     */
    @Test
    void MessageRepository_UpdateMessage() {
        // Create and save a test User
        User testUser = new User("testUsername", "testFirstname", "testLastname", "test@email.com");
        userRepository.save(testUser);

        // Create and save a test Channel
        Channel testChannel = new Channel("testChannelname", false);
        channelRepository.save(testChannel);

        // Create and save a test Message
        Message testMessage = new Message("testContent", testUser, testChannel);
        messageRepository.save(testMessage);

        // Modify the Message's properties
        testMessage.setContent("UpdatedContent");

        // Update the Message in the repository
        Message updatedMessage = messageRepository.save(testMessage);

        // Assert that the Message is updated successfully
        Assertions.assertThat(updatedMessage).isNotNull();
        Assertions.assertThat(updatedMessage.getContent()).isEqualTo("UpdatedContent");
    }

    /**
     * test delete()
     */
    @Test
    void MessageRepository_DeleteMessage() {
        // Create and save a test User
        User testUser = new User("testUsername", "testFirstname", "testLastname", "test@email.com");
        userRepository.save(testUser);

        // Create and save a test Channel
        Channel testChannel = new Channel("testChannelname", false);
        channelRepository.save(testChannel);

        // Create and save a test Message
        Message testMessage = new Message("testContent", testUser, testChannel);
        messageRepository.save(testMessage);

        // Delete the Message from the repository
        messageRepository.delete(testMessage);

        // Try to retrieve the deleted Message
        Message deletedMessage = messageRepository.findById(testMessage.getId()).orElse(null);

        // Assert that the Message is deleted successfully
        Assertions.assertThat(deletedMessage).isNull();
    }

}