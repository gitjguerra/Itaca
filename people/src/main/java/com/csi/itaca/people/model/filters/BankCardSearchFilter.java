package com.csi.itaca.people.model.filters;

import com.csi.itaca.people.model.dto.CardTypeDTO;
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
public abstract class BankCardSearchFilter {

	private CardTypeDTO cardType;

	@Size(max=50)
	private String id = "";
	
	@Size(max=50)
	private String literal = "";

	private Pagination pagination;

	private Order order;
}
