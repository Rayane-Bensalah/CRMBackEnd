package com.slack.CrmBackend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Channel Dto
 */
@Getter
@Setter
@NoArgsConstructor
public class ChannelDtoSlim {
    private Integer id;
    private String name;
    private boolean isMain;
}
