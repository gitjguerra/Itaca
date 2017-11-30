package com.csi.itaca.users.model.dto;

import com.csi.itaca.common.model.BaseModelImpl;
import com.csi.itaca.users.model.Profile;
import com.csi.itaca.users.model.User;
import com.csi.itaca.users.model.UserConfig;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Setter
@Getter
@NoArgsConstructor
public class UserConfigDTO extends BaseModelImpl implements UserConfig {

	private ProfileDTO profile;

	private UserDTO user;

}
