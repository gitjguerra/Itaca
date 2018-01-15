package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.CivilStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
public class CivilStatusDTO implements CivilStatus {

	private Long id;

	@JsonInclude(NON_NULL)
	private String name;
}
