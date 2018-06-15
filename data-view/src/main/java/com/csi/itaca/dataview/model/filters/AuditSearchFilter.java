package com.csi.itaca.dataview.model.filters;

import com.csi.itaca.tools.utils.jpa.Order;
import com.csi.itaca.tools.utils.jpa.Pagination;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@JsonTypeName("AuditSearchFilter")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY , property = "auditSerchFilter")
public class AuditSearchFilter {
    @Size(max=50)
    private String id = "";

    private Date timeStamp = null;

    @Size(max=200)
    private String userName = "";

    @Size(max=50)
    private String operation = "";

    @Size(max=1000)
    private String sqlCommand = "";

    private Pagination pagination;

    private Order order;
}
