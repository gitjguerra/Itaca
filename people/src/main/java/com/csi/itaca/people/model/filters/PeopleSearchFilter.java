package com.csi.itaca.people.model.filters;

import com.csi.itaca.people.model.PersonType;
import com.csi.itaca.people.model.dto.IDTypeDTO;
import com.csi.itaca.people.model.dto.PersonTypeDTO;
import com.csi.itaca.tools.utils.jpa.Order;
import com.csi.itaca.tools.utils.jpa.Pagination;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({ @Type(value = IndividualSearchFilter.class, name = PersonType.INDIVIDUAL_PERSON_CODE),
		        @Type(value = CompanySearchFilter.class, 	name = PersonType.COMPANY_PERSON_CODE),
				@Type(value = NullTypeSearchFilter.class, 	name = "null")
})
public abstract class PeopleSearchFilter {

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

	@Size(max=50)
	private String name = "";

	private Pagination pagination;

	private Order order;
}
