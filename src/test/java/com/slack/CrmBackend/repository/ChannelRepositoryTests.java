package com.slack.CrmBackend.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.slack.CrmBackend.model.Channel;

/**
 * Tests for ChannelRepository
 * tested save() and findAll()
 */
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // Simulate a Database
@DataJpaTest
class ChannelRepositoryTests {

    @Autowired
    ChannelRepository ChannelRepository;

    /**
     * test finAll()
     */
    @Test
    public void ChannelRepository_GetAllChannels() {
        // Create and save 2 Channels
        Channel testChannel1 = new Channel("testChannelname1", false);
        Channel testChannel2 = new Channel("testChannelname2", true);

        ChannelRepository.save(testChannel1);
        ChannelRepository.save(testChannel2);

        // Get all Channels
        List<Channel> ChannelList = ChannelRepository.findAll();

        // Assert both Channels are created and saved
        Assertions.assertThat(ChannelList).isNotNull();
        Assertions.assertThat(ChannelList.size()).isEqualTo(2);
    }

    /**
     * test save()
     */
    @Test
    void ChannelRepository_SaveChannel() {

        // Create test Channel and save it
        Channel testChannel = new Channel("testChannelname", false);
        ChannelRepository.save(testChannel);

        // Assert Channel is created and with correct id
        Assertions.assertThat(testChannel).isNotNull();
        Assertions.assertThat(testChannel.getId()).isGreaterThan(0);
    }

    /**
     * test update
     */
    @Test
    void ChannelRepository_UpdateChannel() {
        // Create and save a test Channel
        Channel testChannel = new Channel("testChannelname", false);
        ChannelRepository.save(testChannel);

        // Modify the Channel's properties
        testChannel.setName("UpdatedChannelName");
        testChannel.setMain(true);

        // Update the Channel in the repository
        Channel updatedChannel = ChannelRepository.save(testChannel);

        // Assert that the Channel is updated successfully
        Assertions.assertThat(updatedChannel).isNotNull();
        Assertions.assertThat(updatedChannel.getName()).isEqualTo("UpdatedChannelName");
        Assertions.assertThat(updatedChannel.isMain()).isTrue();
    }

    /**
     * test delete()
     */
    @Test
    void ChannelRepository_DeleteChannel() {
        // Create and save a test Channel
        Channel testChannel = new Channel("testChannelname", false);
        ChannelRepository.save(testChannel);

        // Delete the Channel from the repository
        ChannelRepository.delete(testChannel);

        // Try to retrieve the deleted Channel
        Channel deletedChannel = ChannelRepository.findById(testChannel.getId()).orElse(null);

        // Assert that the Channel is deleted successfully
        Assertions.assertThat(deletedChannel).isNull();
    }

}