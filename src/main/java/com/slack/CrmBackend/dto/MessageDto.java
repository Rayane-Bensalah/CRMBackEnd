package com.slack.CrmBackend.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Message Dto
 */
@Getter
@Setter
@NoArgsConstructor
public class MessageDto {
    private Integer id;
    private String content;
    private UserDtoSlim user;
    private ChannelDtoSlim channel;
    LocalDateTime sendDate;
}
