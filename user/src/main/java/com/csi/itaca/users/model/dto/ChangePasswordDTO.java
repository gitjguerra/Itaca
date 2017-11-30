package com.csi.itaca.users.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Represents the DTO that will be used for change password functionality.
 * @author bboothe
 */
@Setter
@Getter
@NoArgsConstructor
public class ChangePasswordDTO {

    public static String USER_NAME                = "username";
    public static String CURRENT_PASSWORD         = "currentPassword";
    public static String NEW_PASSWORD             = "newPassword";
    public static String CONFIRM_PASSWORD_FIELD   = "confirmationPassword";

    private String username;

    private String currentPassword;

    private String newPassword;

    private String confirmationPassword;

}

