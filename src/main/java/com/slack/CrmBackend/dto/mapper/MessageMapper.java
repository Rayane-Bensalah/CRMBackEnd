package com.slack.CrmBackend.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
    @Mapping(target = "channel", ignore = true)
    MessageDto messageToMessageDto(Message message);
}
