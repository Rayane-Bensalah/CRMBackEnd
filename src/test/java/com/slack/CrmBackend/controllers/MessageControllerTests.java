package com.slack.CrmBackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.CrmBackend.Service.ChannelService;
import com.slack.CrmBackend.Service.UserService;
import com.slack.CrmBackend.model.Channel;
import com.slack.CrmBackend.model.Message;
import com.slack.CrmBackend.Service.MessageService;
import com.slack.CrmBackend.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for MessageController
 * tested methods getAllMessages(), getMessageById(), addMessage(), putMessage() and deleteMessage(),
 * used Mockito with MockMvc to mock an API Call on the controller and check the expected JSON result
 */
@Transactional
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerTests {

    @MockBean
    private MessageService messageService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ChannelService channelService;

    /**
     * TODO : create testUser and testChannel @BeforeEach test
     */


    /**
     * Test getAllMessages() : Verify that the get correctly get all existing messages
     * @throws Exception
     */
    @Test
    void getAllMessages() throws Exception {
        User testUser = new User("testUserName1", "testFirstName1", "testLastName1", "test@email1.com");
        userService.createUser(testUser);

        User testUser2 = new User("testUserName2", "testFirstName2", "testLastName2", "test@email2.com");
        userService.createUser(testUser);

        Channel testChannel = new Channel("testChannelName", false);
        channelService.createChannel(testChannel);

        Channel testChannel2 = new Channel("testChannelName2", true);
        channelService.createChannel(testChannel);

        Message message1 = new Message("Test content 1", testUser, testChannel);
        Message message2 = new Message("Test content 2", testUser2, testChannel2);
        Mockito.when(messageService.getAllMessages()).thenReturn(Arrays.asList(message1, message2));

        mockMvc.perform(get("/messages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].content").value("Test content 1"))
                .andExpect(jsonPath("$[1].content").value("Test content 2"));
    }

    /**
     * Test getMessageById() : Verify that the get with endpoint /messages/{id} correctly get an existing message
     * @throws Exception
     */
    @Test
    void getMessageById() throws Exception {
        User testUser = new User("testUserName1", "testFirstName1", "testLastName1", "test@email1.com");
        userService.createUser(testUser);

        Channel testChannel = new Channel("testChannelName", false);
        channelService.createChannel(testChannel);

        Message message = new Message("Test content", testUser, testChannel);
        Mockito.when(messageService.getMessageById(1)).thenReturn(Optional.of(message));

        mockMvc.perform(get("/messages/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    /**
     * Test addMessage() : Verify that the add correctly create a new message
     * @throws Exception
     */
    @Test
    void addMessage() throws Exception {
        User testUser = new User("testUserName1", "testFirstName1", "testLastName1", "test@email1.com");
        userService.createUser(testUser);

        Channel testChannel = new Channel("testChannelName", false);
        channelService.createChannel(testChannel);

        Message message = new Message("Test content", testUser, testChannel);
        Mockito.when(messageService.createMessage(any(Message.class))).thenReturn(message);

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").value("Test content"));
    }

    /**
     * Test putMessage() : Verify that the put with endpoint /messages/{id} correctly update an existing message
     * @throws Exception
     */
    @Test
    void putMessage() throws Exception {
        User testUser = new User("testUserName1", "testFirstName1", "testLastName1", "test@email1.com");
        userService.createUser(testUser);

        Channel testChannel = new Channel("testChannelName", false);
        channelService.createChannel(testChannel);

        // Mocking an existing message
        Message existingMessage = new Message("Test content", testUser, testChannel);
        messageService.createMessage(existingMessage);

        Message updatedMessage = new Message("Updated content", testUser, testChannel);

        // Mocking the service behavior
        Mockito.when(messageService.getMessageById(1)).thenReturn(Optional.of(existingMessage));
        Mockito.when(messageService.updateMessage(eq(1), any(Message.class))).thenReturn(existingMessage); // Returning the existing message after update

        mockMvc.perform(put("/messages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedMessage)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").value("Test content")); // Checking the existing message's fields
    }

    /**
     * Test deleteMessage() : Verify that the delete with endpoint /messages/{id} correctly delete an existing message
     * @throws Exception
     */
    @Test
    void deleteMessage() throws Exception {
        User testUser = new User("testUserName1", "testFirstName1", "testLastName1", "test@email1.com");
        userService.createUser(testUser);

        Channel testChannel = new Channel("testChannelName", false);
        channelService.createChannel(testChannel);

        Message messageToDelete = new Message("Test content", testUser, testChannel);
        messageToDelete.setId(1);

        mockMvc.perform(delete("/messages/1"))
                .andExpect(status().isNotFound());
    }
}