package com.slack.CrmBackend.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;

import com.slack.CrmBackend.Service.MessageService;
import com.slack.CrmBackend.Service.UserService;
import com.slack.CrmBackend.model.Message;
import com.slack.CrmBackend.model.User;
import org.assertj.core.api.Assertions;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.CrmBackend.Service.ChannelService;
import com.slack.CrmBackend.model.Channel;

/**
 * Tests for ChannelController
 * tested methods getAllChannels(), getChannelById(), addChannel(), putChannel()
 * and deleteChannel(),
 * used Mockito with MockMvc to mock an API Call on the controller and check the
 * excepted JSON result
 */
@Transactional
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
@AutoConfigureMockMvc
class ChannelControllerTests {

    @MockBean
    private ChannelService channelService;

    @MockBean
    private UserService userService;

    @MockBean
    private MessageService messageService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Test getAllChannels() : Verify that the getChannels correctly get all
     * existing channels
     * 
     * @throws Exception
     */
    @Test
    void getAllChannels() throws Exception {

        Channel channel1 = new Channel("testChannelName1", false);
        Channel channel2 = new Channel("testChannelName2", false);
        Mockito.when(channelService.getAllChannels()).thenReturn(Arrays.asList(channel1, channel2));

        mockMvc.perform(get("/channels"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].name").value("testChannelName1"))
                .andExpect(jsonPath("$[0].main").value("false"))

                .andExpect(jsonPath("$[1].name").value("testChannelName2"))
                .andExpect(jsonPath("$[1].main").value("false"));
    }

    /**
     * Test getChannelById() : Verify that the get with endpoint /channels/{id}
     * correctly get an existing channel
     * 
     * @throws Exception
     */
    @Test
    void getChannelById() throws Exception {

        Channel channel = new Channel("testChannelname1", false);
        Mockito.when(channelService.getChannelById(1)).thenReturn(Optional.of(channel));

        mockMvc.perform(get("/channels/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("testChannelname1"))
                .andExpect(jsonPath("$.main").value("false"));
    }

    /**
     * Test addChannel() : Verify that the add correctly create a new channel
     * 
     * @throws Exception
     */
    @Test
    void addChannel() throws Exception {
        Channel channel = new Channel("testChannelname1", false);
        Mockito.when(channelService.createChannel(any(Channel.class))).thenReturn(channel);

        mockMvc.perform(post("/channels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(channel)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("testChannelname1"))
                .andExpect(jsonPath("$.main").value("false"));
    }

    /**
     * Test putChannel() : Verify that the put with endpoint /channels/{id}
     * correctly update an existing channel
     * 
     * @throws Exception
     */
    @Test
    void putChannel() throws Exception {
        // Mocking an existing channel
        Channel existingChannel = new Channel("testChannelname1", false);
        channelService.createChannel(existingChannel);

        Channel updatedChannel = new Channel("updatedChannelname1", false);

        // Mocking the service behavior
        Mockito.when(channelService.getChannelById(1)).thenReturn(Optional.of(existingChannel));
        Mockito.when(channelService.updateChannel(any(Channel.class))).thenReturn(existingChannel); // Returning
                                                                                                    // the
                                                                                                    // existing
                                                                                                    // channel
                                                                                                    // after
                                                                                                    // update

        mockMvc.perform(put("/channels/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedChannel)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("updatedChannelname1"))
                .andExpect(jsonPath("$.main").value("false"));
    }

    /**
     * Test deleteChannel() : Verify that the delete with endpoint /channels/{id}
     * correctly delete an existing channel
     * 
     * @throws Exception
     */
    @Test
    void deleteChannel() throws Exception {
        Channel channelToDelete = new Channel("testChannelname1", false);
        channelToDelete.setId(1);

        mockMvc.perform(delete("/channels/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * test getMessagesChannel()
     */
    @Test
    void ChannelController_GetMessagesByChannelId() throws Exception {
        // Create and save a test User
        User testUser = new User("testUsername", "testFirstname", "testLastname", "test@email.com");
        userService.createUser(testUser);
        testUser.setId(1);

        // Create and save a test Channel
        Channel testChannel = new Channel("testChannelname", false);
        channelService.createChannel(testChannel);
        testChannel.setId(1);

        // Create and save a test Message
        Message testMessage = new Message("testContent", testUser, testChannel);
        messageService.createMessage(testMessage);
        testMessage.setId(1);

        // Create a list with
        List<Message> messageList = Collections.singletonList(testMessage);

        Mockito.when(messageService.getMessagesChannel(1)).thenReturn(Optional.of(messageList));


        mockMvc.perform(get("/channels/1/messages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].content").value("testContent"))
                .andExpect(jsonPath("$[0].user.id").value(testUser.getId()))
                .andExpect(jsonPath("$[0].channel.id").value(testChannel.getId()));
    }
}