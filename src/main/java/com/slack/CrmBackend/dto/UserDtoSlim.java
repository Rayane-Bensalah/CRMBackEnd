package com.slack.CrmBackend.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User Dto Slim
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDtoSlim {
    private Integer id;
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
