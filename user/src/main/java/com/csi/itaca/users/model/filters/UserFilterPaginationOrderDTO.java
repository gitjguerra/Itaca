package com.csi.itaca.users.model.filters;

import com.csi.itaca.tools.utils.jpa.Order;
import com.csi.itaca.tools.utils.jpa.Pagination;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserFilterPaginationOrderDTO {

    private Pagination pagination;

    private Order order;

    private UserSearchFilterDTO filter;
}
