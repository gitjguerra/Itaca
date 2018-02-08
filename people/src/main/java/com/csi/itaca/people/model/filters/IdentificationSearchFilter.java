package com.csi.itaca.people.model.filters;

import com.csi.itaca.tools.utils.jpa.Order;
import com.csi.itaca.tools.utils.jpa.Pagination;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode

public class IdentificationSearchFilter {

	private Long personDetailsId;
	private Pagination pagination;
	private Order order;

	public IdentificationSearchFilter(Long personDetailsId, Pagination pagination, Order order) {
		this.personDetailsId = personDetailsId;
		this.pagination = pagination;
		this.order = order;
	}
}
