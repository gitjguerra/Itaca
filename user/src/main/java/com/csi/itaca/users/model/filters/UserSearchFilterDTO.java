package com.csi.itaca.users.model.filters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSearchFilterDTO {

    private String username = "";

    private String description = "";

    private Boolean blockedUser = Boolean.FALSE;

}
