package com.slack.CrmBackend.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.slack.CrmBackend.dto.MessageDto;
import com.slack.CrmBackend.model.Message;

/**
 * Message Dto Mapper
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {
        Message.class, MessageDto.class })
public interface MessageMapper {
    /**
     * Providing access to the mapper implementation and avoid test errors
     */
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    /**
     * @param Message message
     * @return MessageDto
     */
    // @Mapping(target = "channel", ignore = true)
    // @Mapping(target = "user", ignore = true)
    MessageDto messageToDto(Message message);

    /**
     * Convert Message List to Message DTO List
     * 
     * @param Message List
     * @return MessageDto List
     */
    // @Mapping(target = "channel", ignore = false)
    // @Mapping(target = "user", ignore = false)
    List<MessageDto> messagesToDto(List<Message> messages);
}
