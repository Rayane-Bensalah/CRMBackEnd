package com.slack.CrmBackend.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User Dto
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
