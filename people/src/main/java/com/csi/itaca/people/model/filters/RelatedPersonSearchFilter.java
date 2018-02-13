package com.csi.itaca.people.model.filters;

import com.csi.itaca.tools.utils.jpa.Order;
import com.csi.itaca.tools.utils.jpa.Pagination;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@JsonTypeName("RelatedPersonSearchFilter")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY , property = "relatedPersonSerchFilter")
public class RelatedPersonSearchFilter {

	@Size(max=50)
	private String id = "";

	private String personDetailId = "";

	private Pagination pagination;

	private Order order;

}
