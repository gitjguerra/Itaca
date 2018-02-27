package com.csi.itaca.people.model.filters;

import com.csi.itaca.tools.utils.jpa.Order;
import com.csi.itaca.tools.utils.jpa.Pagination;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
public class ContactSearchFilter {

	@Size(max=50)
	private String id = "";
	
	@Size(max=50)
	private String value = "";

	private Pagination pagination;

	private Order order;
}
