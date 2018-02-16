package com.csi.itaca.users.model.dto;

import com.csi.itaca.common.model.BaseModelImpl;
import com.csi.itaca.users.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * User dto
 * @see User
 * @author bboothe
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDTO extends BaseModelImpl implements User {

    private String username;
    
    private String password;

    private String firstName;

    private String lastName;

    private String companyCode;

    private String description;

    private UserLanguageDTO userLanguage;

    private LocalDate companyStartDate;

    private LocalDate companyEndDate;

    private String email;

    private boolean blockedUser;

    private LocalDate blockedDate;
}
