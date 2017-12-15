package com.csi.itaca.people.model.filters;

import com.csi.itaca.people.model.dto.IDTypeDTO;
import com.csi.itaca.people.model.dto.PersonType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@JsonSubTypes({ @Type(value = IndividualPeopleSearchFilter.class), @Type(value = CompanyPeopleSearchFilter.class) })
public class PeopleSearchFilter {
	
	private PersonType personType;
	
	private IDTypeDTO idType;
	
	@Size(max=50)
	private String idCode = "";
	
	@Size(max=50)
	private String externalReference = "";
	
}
