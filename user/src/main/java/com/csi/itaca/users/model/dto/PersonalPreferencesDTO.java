package com.csi.itaca.users.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class PersonalPreferencesDTO {

    private Long userId;

    @NotNull
    private UserLanguageDTO userLanguage;
}
