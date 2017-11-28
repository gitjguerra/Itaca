package com.csi.itaca.users.model.dto;

import com.csi.itaca.common.model.BaseModelImpl;
import com.csi.itaca.users.model.ProfileType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class ProfileTypeDTO extends BaseModelImpl implements ProfileType {

	private String value;
}
