package com.slack.CrmBackend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User Put Resquest Dto
 */
@Getter
@Setter
@NoArgsConstructor
public class UserPutResquestDto {
    private Integer id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
}
