package com.csi.itaca.load.model.filter;

import com.csi.itaca.tools.utils.jpa.Order;
import com.csi.itaca.tools.utils.jpa.Pagination;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Robert on 09/07/2018.
 */

@Getter
@Setter
@NoArgsConstructor
public class PreloadDataOrderPaginFilter {

    private PreloadDataDTOFilter filter;
    private Pagination pagination;
    private Order order;

    public PreloadDataOrderPaginFilter(PreloadDataDTOFilter filter, Pagination pagination, Order order) {
        this.filter = filter;
        this.pagination = pagination;
        this.order = order;
    }

}
