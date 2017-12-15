package com.csi.itaca.people.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PersonType {
	
	public static final String INDIVIDUAL_PERSON_CODE 		= "F";
	public static final String I18N_INDIVIDUAL_PERSON_KEY 	= "personType.individual";
	public static final String COMPANY_PERSON_CODE 			= "J";
	public static final String I18N_COMPANY_PERSON_KEY 		= "personType.company";
	
	private String id = "";
	private String name = "";
	
	public PersonType(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
}