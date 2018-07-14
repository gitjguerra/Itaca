package com.csi.itaca.load.model.filter;

import com.csi.itaca.tools.utils.jpa.Order;
import com.csi.itaca.tools.utils.jpa.Pagination;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoadFileOrderPaginFilter {

	private LoadFileDTOFilter filter;
	private Pagination pagination;
	private Order order;

	public LoadFileOrderPaginFilter(LoadFileDTOFilter filter, Pagination pagination, Order order) {
		this.filter = filter;
		this.pagination = pagination;
		this.order = order;
	}
	
}
