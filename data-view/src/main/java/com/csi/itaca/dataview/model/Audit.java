package com.csi.itaca.dataview.model;

import java.util.Date;

public interface Audit {
    Long getId();
    Date getTimeStamp();
    String getUserName();
    String getOperation();
    String getSqlCommand();
}
