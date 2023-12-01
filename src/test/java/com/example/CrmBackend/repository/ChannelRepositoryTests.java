package com.example.CrmBackend.repository;

import com.example.CrmBackend.model.Channel;
import com.example.CrmBackend.repository.ChannelRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Tests for ChannelRepository
 * tested save() and findAll()
 */
@SpringBootTest
class ChannelRepositoryTests {

    @Autowired
    ChannelRepository ChannelRepository;

    @Test
    void ChannelRepository_SaveChannel() {

        // Create test Channel and save it
        Channel testChannel = new Channel("testChannelname", false);
        ChannelRepository.save(testChannel);

        // Assert Channel is created and with correct id
        Assertions.assertThat(testChannel).isNotNull();
        Assertions.assertThat(testChannel.getId()).isGreaterThan(0);
    }

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

}