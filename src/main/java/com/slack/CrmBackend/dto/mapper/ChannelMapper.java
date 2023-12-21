package com.slack.CrmBackend.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.slack.CrmBackend.dto.ChannelDto;
import com.slack.CrmBackend.dto.ChannelDtoSlim;
import com.slack.CrmBackend.dto.ChannelPostResquestDto;
import com.slack.CrmBackend.model.Channel;

/**
 * Channel Dto Mapper
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {
        Channel.class, ChannelDto.class, ChannelDtoSlim.class }, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ChannelMapper {
    /**
     * Providing access to the mapper implementation and avoid test errors
     */
    ChannelMapper INSTANCE = Mappers.getMapper(ChannelMapper.class);

    /**
     * @param channel
     * @return channelDto
     */
    ChannelDto channelToDto(Channel channel);

    /**
     * Convert Channel entity to Channel DTO Slim
     *
     * @param channel
     * @return ChannelDtoSlim
     */
    ChannelDtoSlim channelToSlimDto(Channel channel);

    /**
     * Convert Channels List to Channels DTO List
     * 
     * @param channels
     * @return channelsDto
     */
    List<ChannelDto> channelsToDto(List<Channel> channels);

    /**
     * Convert ChannelDto to Channel
     * 
     * @param channelDto
     * @return channel
     */
    @Mapping(target = "messages", ignore = true)
    Channel channelDtoToChannel(ChannelDto channelDto);

    /**
     * Convert ChannelPostResquestDto to Channel
     * 
     * @param channelDto
     * @return channel
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "main", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "messages", ignore = true)
    Channel channelPostResquestDtoToChannel(ChannelPostResquestDto channelPostResquestDto);
}
