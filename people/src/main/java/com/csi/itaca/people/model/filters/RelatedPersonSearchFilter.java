package com.csi.itaca.people.model.filters;

import com.csi.itaca.tools.utils.jpa.Order;
import com.csi.itaca.tools.utils.jpa.Pagination;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonSubTypes({ @JsonSubTypes.Type(value = NullTypeSearchFilter.class, 	name = "null")
})
public class RelatedPersonSearchFilter {

	private String id = "";

	private String personDetailId = "";

	private Pagination pagination;

	private Order order;

}
