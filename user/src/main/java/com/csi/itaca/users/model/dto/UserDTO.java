package com.csi.itaca.users.model.dto;

import com.csi.itaca.users.model.User;
import com.csi.itaca.users.model.UserLanguage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.Date;

/**
 * User DTO
 * @see User
 * @author bboothe
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDTO implements User {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String companyCode;

    private String description;

    private UserLanguageDTO userLanguages;

    private LocalDate companyStartDate;

    private LocalDate companyEndDate;

    private String email;

    private boolean blocked;

    private LocalDate blockedDate;
}
