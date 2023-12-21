package com.slack.CrmBackend.services;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.slack.CrmBackend.Service.ChannelService;
import com.slack.CrmBackend.model.Channel;

/**
 * Tests for ChannelServices
 * Use of @Transactional annotation causes the test to be run within a
 * transaction that is, by default, automatically rolled back after completion
 * of the test.
 */
@Transactional
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // Simulate a Database
@SpringBootTest
class ChannelServicesTests {

    @Autowired
    ChannelService channelService = Mockito.mock(ChannelService.class);

    /**
     * test getAllChannel()
     */
    @Test
    public void ChannelService_GetAllChannels() {
        // Create and save 2 Channels
        Channel testChannel1 = new Channel("testChannelname1", false);
        Channel testChannel2 = new Channel("testChannelname2", true);

        channelService.createChannel(testChannel1);
        channelService.createChannel(testChannel2);

        // Get all Channels
        List<Channel> ChannelList = channelService.getAllChannels();

        // Assert both Channels are created and saved
        Assertions.assertThat(ChannelList).isNotNull();
        Assertions.assertThat(ChannelList.size()).isEqualTo(2);
    }

    /**
     * test getChannelById
     */
    @Test
    public void ChannelService_GetChannelById() {
        // Create and save 2 Channels
        Channel testChannel1 = new Channel("testChannelname1", false);

        channelService.createChannel(testChannel1);

        // Get one Channel
        Optional<Channel> foundChannel = channelService.getChannelById(testChannel1.getId());

        // Assert channel is found and correct
        Assertions.assertThat(foundChannel).isNotNull();
        Assertions.assertThat(foundChannel).isPresent();
        Assertions.assertThat(foundChannel.get().getName()).isEqualTo(testChannel1.getName());
    }

    /**
     * test createChannel()
     */
    @Test
    void ChannelService_CreateChannel() {

        // Create test Channel and save it
        Channel testChannel = new Channel("testChannelname", false);
        channelService.createChannel(testChannel);

        // Assert Channel is created and with correct id
        Assertions.assertThat(testChannel).isNotNull();
        Assertions.assertThat(testChannel.getId()).isGreaterThan(0);
    }

    /**
     * test update
     */
    @Test
    void ChannelService_UpdateChannel() {
        // Create and save a test Channel
        Channel testChannel = new Channel("testChannelname", false);
        channelService.createChannel(testChannel);

        // Modify the Channel's properties
        testChannel.setName("UpdatedChannelName");
        testChannel.setMain(true);

        // Update the Channel in the repository
        Channel updatedChannel = channelService.updateChannel(testChannel);

        // Assert that the Channel is updated successfully
        Assertions.assertThat(updatedChannel).isNotNull();
        Assertions.assertThat(updatedChannel.getName()).isEqualTo("UpdatedChannelName");
        Assertions.assertThat(updatedChannel.isMain()).isTrue();
    }

    /**
     * test delete()
     */
    @Test
    void ChannelService_DeleteChannel() {
        // Create and save a test Channel
        Channel testChannel = new Channel("testChannelname", false);
        channelService.createChannel(testChannel);

        Integer testChannelId = testChannel.getId();

        // Delete the Channel from the repository
        channelService.deleteChannel(testChannelId);

        // Try to retrieve the deleted Channel
        Channel deletedChannel = channelService.getChannelById(testChannel.getId()).orElse(null);

        // Assert that the Channel is deleted successfully
        Assertions.assertThat(deletedChannel).isNull();
    }

}