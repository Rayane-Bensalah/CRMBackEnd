package com.slack.CrmBackend.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.slack.CrmBackend.dto.ChannelDto;
import com.slack.CrmBackend.dto.ChannelDtoSlim;
import com.slack.CrmBackend.model.Channel;

/**
 * Channel Dto Mapper
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {
        Channel.class, ChannelDto.class })
public interface ChannelMapper {
    /**
     * Providing access to the mapper implementation and avoid test errors
     */
    ChannelMapper INSTANCE = Mappers.getMapper(ChannelMapper.class);

    /**
     * @param Channel channel
     * @return ChannelDto
     */
    @Mapping(target = "messages", ignore = true)
    ChannelDto channelToDto(Channel channel);

    /**
     * Convert Channel entity to Channel DTO Slim
     *
     * @param Channel channel
     * @return ChannelDtoSlim
     */
    ChannelDtoSlim channelToSlimDto(Channel channel);

    /**
     * Convert Channels List to Channels DTO List
     * 
     * @param Channels List
     * @return ChannelDto List
     */
    // @Mapping(target = "messages", ignore = true)
    List<ChannelDto> channelsToDto(List<Channel> channels);
}
