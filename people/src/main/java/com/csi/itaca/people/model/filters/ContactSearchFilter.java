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
@JsonSubTypes({ @Type(value = NullTypeSearchFilter.class, 	name = "null")
})
public class ContactSearchFilter {

	public static final String ID_CODE_FIELD 		= "ID_PER_CONTACT";
	public static final String VALUE_FIELD 			= "VALUE_CONTACT";

	@Size(max=50)
	private String id = "";
	
	@Size(max=50)
	private String value = "";

	private Pagination pagination;

	private Order order;
}
