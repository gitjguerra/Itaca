package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.PersonType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PersonTypeDTO implements PersonType {

	private String id = "";

	@JsonInclude(NON_NULL)
	private String name = "";
	
	public PersonTypeDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
}