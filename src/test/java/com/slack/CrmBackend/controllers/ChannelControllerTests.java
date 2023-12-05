package com.slack.CrmBackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slack.CrmBackend.model.Channel;
import com.slack.CrmBackend.Service.ChannelService;
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
 * Tests for ChannelController
 * tested methods getAllChannels(), getChannelById(), addChannel(), putChannel() and deleteChannel(),
 * used Mockito with MockMvc to mock an API Call on the controller and check the excepted JSON result
 */
@Transactional
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
@AutoConfigureMockMvc
class ChannelControllerTests {

    @MockBean
    private ChannelService channelService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Test getAllChannels() : Verify that the getChannels correctly get all existing channels
     * @throws Exception
     */
    @Test
    void getAllChannels() throws Exception {

        Channel channel1 = new Channel("testChannelName1", false);
        Channel channel2 = new Channel("testChannelName2", false);
        Mockito.when(channelService.getAllChannels()).thenReturn(Arrays.asList(channel1, channel2));

        mockMvc.perform(get("/channel"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$[0].name").value("testChannelName1"))
                .andExpect(jsonPath("$[0].main").value("false"))

                .andExpect(jsonPath("$[1].name").value("testChannelName2"))
                .andExpect(jsonPath("$[1].main").value("false"));
    }

    /**
     * Test getChannelById() : Verify that the get with endpoint /channel/{id} correctly get an existing channel
     * @throws Exception
     */
    @Test
    void getChannelById() throws Exception {

        Channel channel = new Channel("testChannelname1", false);
        Mockito.when(channelService.getChannelById(1)).thenReturn(Optional.of(channel));

        mockMvc.perform(get("/channel/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("testChannelname1"))
                .andExpect(jsonPath("$.main").value("false"));
    }

    /**
     * Test addChannel() : Verify that the add correctly create a new channel
     * @throws Exception
     */
    @Test
    void addChannel() throws Exception {
        Channel channel = new Channel("testChannelname1", false);
        Mockito.when(channelService.createChannel(any(Channel.class))).thenReturn(channel);

        mockMvc.perform(post("/channel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(channel)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("testChannelname1"))
                .andExpect(jsonPath("$.main").value("false"));
    }

    /**
     * Test putChannel() : Verify that the put with endpoint /channel/{id} correctly update an existing channel
     * @throws Exception
     */
    @Test
    void putChannel() throws Exception {
        // Mocking an existing channel
        Channel existingChannel = new Channel("testChannelname1", false);
        channelService.createChannel(existingChannel);

        Channel updatedChannel = new Channel("testChannelname1", false);

        // Mocking the service behavior
        Mockito.when(channelService.getChannelById(1)).thenReturn(Optional.of(existingChannel));
        Mockito.when(channelService.updateChannel(eq(1), any(Channel.class))).thenReturn(existingChannel); // Returning the existing channel after update

        mockMvc.perform(put("/channel/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedChannel)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("testChannelname1"))
                .andExpect(jsonPath("$.main").value("false"));
    }

    /**
     * Test deleteChannel() : Verify that the delete with endpoint /channel/{id} correctly delete an existing channel
     * @throws Exception
     */
    @Test
    void deleteChannel() throws Exception {
        Channel channelToDelete = new Channel("testChannelname1", false);
        channelToDelete.setId(1);

        mockMvc.perform(delete("/channel/1"))
                .andExpect(status().isNotFound());
    }
}