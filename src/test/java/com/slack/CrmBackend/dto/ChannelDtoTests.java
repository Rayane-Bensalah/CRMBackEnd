package com.slack.CrmBackend.dto;

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
import com.slack.CrmBackend.model.Channel;
import com.slack.CrmBackend.repository.ChannelRepository;

import jakarta.transaction.Transactional;

/**
 * Tests for Channel Dto
 * tested save() and findAll()
 */
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
@Transactional
public class ChannelDtoTests {

    /**
     * Partial mock for ChannelMapper
     */
    @Spy
    ChannelMapper channelMapper = Mappers.getMapper(ChannelMapper.class);

    @Autowired
    ChannelRepository channelRepository;

    /**
     * Logger : Display custom datas into console
     */
    private static Logger logger = LoggerFactory.getLogger(ChannelDtoTests.class);

    @Test
    void channelDtoMapperTests() {
        logger.info(
                "############### [ChannelDtoTests][channelDtoMapperTests] ###############");

        /**
         * Create Main Channel
         */
        Channel mainChannel = new Channel("General", true);
        channelRepository.save(mainChannel);

        /**
         * Check mainChannelDto values
         */
        ChannelDto mainChannelDto = channelMapper.channelToDto(mainChannel);
        Assertions.assertThat(mainChannelDto)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", mainChannel.getId())
                .hasFieldOrPropertyWithValue("name", mainChannel.getName())
                .hasFieldOrPropertyWithValue("isMain", mainChannel.isMain());
    }
}
