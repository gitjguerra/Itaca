package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.PersonType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PersonTypeDTO implements PersonType {

	private String id = "";
	private String name = "";
	
	public PersonTypeDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
}