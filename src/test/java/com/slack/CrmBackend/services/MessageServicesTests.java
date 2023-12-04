package com.slack.CrmBackend.services;

import java.util.List;

import com.slack.CrmBackend.Service.ChannelService;
import com.slack.CrmBackend.Service.MessageService;
import com.slack.CrmBackend.Service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import com.slack.CrmBackend.model.Channel;
import com.slack.CrmBackend.model.Message;
import com.slack.CrmBackend.model.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tests for MessageService
 *  * Use of @Transactional annotation causes the test to be run within a transaction that is, by default, automatically rolled back after completion of the test.
 */
@Transactional
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // Simulate a Database
@SpringBootTest
class MessageServicesTests {

    @Autowired
    MessageService messageService = Mockito.mock(MessageService.class);

    @Autowired
    UserService userService = Mockito.mock(UserService.class);

    @Autowired
    ChannelService channelService = Mockito.mock(ChannelService.class);

    @Test
    public void MessageService_GetAllMessages() {
        // Create and save user & channel

        User testUser = new User("testUsername", "testFirstname", "testLastname", "test@email.com");
        userService.createUser(testUser);

        Channel testChannel = new Channel("testChannelname", false);
        channelService.createChannel(testChannel);

        // Create 2 test messages with user and channel created before
        Message testMessage1 = new Message("testContent1", testUser, testChannel);
        Message testMessage2 = new Message("testContent2", testUser, testChannel);
        messageService.createMessage(testMessage1);
        messageService.createMessage(testMessage2);

        // Get all Messages
        List<Message> MessageList = messageService.getAllMessages();

        // Assert both Messages are created and saved
        Assertions.assertThat(MessageList).isNotNull();
        Assertions.assertThat(MessageList.size()).isEqualTo(2);
    }

    @Test
    void MessageService_SaveMessage() {

        // Create test user and save it
        User testUser = new User("testUsername", "testFirstname", "testLastname", "test@email.com");
        userService.createUser(testUser);

        // Create test channel and save it
        Channel testChannel = new Channel("testChannelname", false);
        channelService.createChannel(testChannel);

        // Create test message with user and channel created before
        Message testMessage = new Message("testContent", testUser, testChannel);
        messageService.createMessage(testMessage);

        // Assert Message is created and with correct id
        Assertions.assertThat(testMessage).isNotNull();
        Assertions.assertThat(testMessage.getId()).isGreaterThan(0);
    }

    /**
     * test update
     */
    @Test
    void MessageService_UpdateMessage() {
        // Create and save a test User
        User testUser = new User("testUsername", "testFirstname", "testLastname", "test@email.com");
        userService.createUser(testUser);

        // Create and save a test Channel
        Channel testChannel = new Channel("testChannelname", false);
        channelService.createChannel(testChannel);

        // Create and save a test Message
        Message testMessage = new Message("testContent", testUser, testChannel);
        messageService.createMessage(testMessage);

        // Modify the Message's properties
        testMessage.setContent("UpdatedContent");

        // Update the Message in the service
        Message updatedMessage = messageService.createMessage(testMessage);

        // Assert that the Message is updated successfully
        Assertions.assertThat(updatedMessage).isNotNull();
        Assertions.assertThat(updatedMessage.getContent()).isEqualTo("UpdatedContent");
    }

    /**
     * test delete()
     */
    @Test
    void MessageService_DeleteMessage() {
        // Create and save a test User
        User testUser = new User("testUsername", "testFirstname", "testLastname", "test@email.com");
        userService.createUser(testUser);

        // Create and save a test Channel
        Channel testChannel = new Channel("testChannelname", false);
        channelService.createChannel(testChannel);

        // Create and save a test Message
        Message testMessage = new Message("testContent", testUser, testChannel);
        messageService.createMessage(testMessage);

        // Delete the Message from the service
        messageService.deleteMessage(testMessage.getId());

        // Try to retrieve the deleted Message
        Message deletedMessage = messageService.getMessageById(testMessage.getId()).orElse(null);

        // Assert that the Message is deleted successfully
        Assertions.assertThat(deletedMessage).isNull();
    }

}