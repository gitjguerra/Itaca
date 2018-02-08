package com.csi.itaca.people.model.filters;

import com.csi.itaca.tools.utils.jpa.Order;
import com.csi.itaca.tools.utils.jpa.Pagination;
//import com.csi_ti.itaca.architecture.tools.api.Orden;
//import com.csi_ti.itaca.architecture.tools.api.Pagina;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NationalityOrderPaginFilter {

	private NationalityDTOFilter filter;
	private Pagination pagination;
	private Order order;

	public NationalityOrderPaginFilter(NationalityDTOFilter filter, Pagination pagination, Order order) {
		this.filter = filter;
		this.pagination = pagination;
		this.order = order;
	}
	
}
