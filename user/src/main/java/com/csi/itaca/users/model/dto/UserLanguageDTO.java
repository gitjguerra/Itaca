package com.csi.itaca.users.model.dto;

import com.csi.itaca.users.model.UserLanguage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User Language DTO
 * @see UserLanguage
 * @author bboothe
 */
@Getter
@Setter
@NoArgsConstructor
public class UserLanguageDTO implements UserLanguage {

    private Long id;

    private String code;

    private String value;
}
