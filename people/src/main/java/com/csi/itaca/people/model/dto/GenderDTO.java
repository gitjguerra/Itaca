package com.csi.itaca.people.model.dto;

import com.csi.itaca.common.model.BaseModelImpl;
import com.csi.itaca.people.model.Gender;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
public class GenderDTO extends BaseModelImpl implements Gender {

	@JsonInclude(NON_NULL)
	private String name;

	@JsonInclude(NON_NULL)
	private Boolean male;

	@JsonInclude(NON_NULL)
	private Boolean female;

	@JsonInclude(NON_NULL)
	private Boolean other;

}
