package com.slack.CrmBackend.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Channel Dto
 */
@Getter
@Setter
@NoArgsConstructor
public class ChannelDto {
    private Integer id;
    private String name;
    private boolean isMain;
    private MessageDto messages;
    private LocalDateTime createdAt;
}
