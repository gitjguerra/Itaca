package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.PersonStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
public class PersonStatusDTO implements PersonStatus {

	private Long id;

	@JsonInclude(NON_NULL)
	private String name;

	@JsonInclude(NON_NULL)
	private Boolean individual;

	@JsonInclude(NON_NULL)
	private Boolean company;

}
