package com.csi.itaca.people.model.filters;

import com.csi.itaca.people.model.dto.IDTypeDTO;
import com.csi.itaca.people.model.dto.PersonTypeDTO;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@JsonSubTypes({ @Type(value = IndividualSearchFilter.class), @Type(value = CompanySearchFilter.class) })
public class PeopleSearchFilter {

	public static final String PERSON_TYPE_FIELD 		= "personType";
	public static final String ID_TYPE_FIELD 			= "idType";
	public static final String ID_CODE_FIELD 			= "idCode";
	public static final String EXTERNAL_REFERENCE_FIELD = "externalReference";

	private PersonTypeDTO personType;
	
	private IDTypeDTO idType;
	
	@Size(max=50)
	private String idCode = "";
	
	@Size(max=50)
	private String externalReference = "";
	
}
