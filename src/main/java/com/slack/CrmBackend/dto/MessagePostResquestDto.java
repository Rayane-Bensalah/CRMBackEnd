package com.slack.CrmBackend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User Post Resquest Dto
 */
@Getter
@Setter
@NoArgsConstructor
public class MessagePostResquestDto {
    private Integer userId;
    private Integer channelId;
    private String content;
}
