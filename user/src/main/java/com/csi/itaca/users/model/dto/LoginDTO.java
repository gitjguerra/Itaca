package com.csi.itaca.users.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * DTO for simple login of user password
 * @author wfrits
 */
@Setter
@Getter
@NoArgsConstructor
public class LoginDTO {

    public static String USER_NAME        = "username";
    public static String PASSWORD         = "password";

    private String username;

    private String password;

}

