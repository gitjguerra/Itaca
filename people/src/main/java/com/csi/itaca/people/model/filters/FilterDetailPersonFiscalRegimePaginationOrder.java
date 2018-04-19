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
public class FilterDetailPersonFiscalRegimePaginationOrder {
	
	private FilterSearchPersonFiscalRegimelDTO filter;

	private Pagination pagination;

	private Order order;

	public FilterDetailPersonFiscalRegimePaginationOrder(FilterSearchPersonFiscalRegimelDTO filter, Pagination pagination, Order order) {
		this.filter = filter;
		this.pagination = pagination;
		this.order = order;
	}


}
