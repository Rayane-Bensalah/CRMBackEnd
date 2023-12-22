package com.slack.CrmBackend.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.slack.CrmBackend.dto.MessageDto;
import com.slack.CrmBackend.dto.MessagePostResquestDto;
import com.slack.CrmBackend.model.Message;

/**
 * Message Dto Mapper
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {
        Message.class, MessageDto.class }, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MessageMapper {
    /**
     * Providing access to the mapper implementation and avoid test errors
     */
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    /**
     * @param message
     * @return messageDto
     */
    // @Mapping(target = "channel", ignore = true)
    // @Mapping(target = "user", ignore = true)
    MessageDto messageToDto(Message message);

    /**
     * Convert Message List to Message DTO List
     * 
     * @param messages
     * @return messagesDto
     */
     @Mapping(target = "channel", ignore = true)
     @Mapping(target = "user", ignore = true)
    List<MessageDto> messagesToDto(List<Message> messages);

    /**
     * Convert MessageDto to Message
     * 
     * @param messageDto
     * @return message
     */
    @Mapping(target = "channel", ignore = true)
    @Mapping(target = "user", ignore = true)
    Message messageDtoToMessage(MessageDto messageDto);

    /**
     * Convert messagePostResquestDto to Message
     * 
     * @param channelDto
     * @return channel
     */
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "channelId", target = "channel.id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sendDate", ignore = true)
    @Mapping(target = "user.messages", ignore = true)
    @Mapping(target = "channel.messages", ignore = true)
    Message messagePostResquestDtoToMessage(MessagePostResquestDto messagePostResquestDto);
}
