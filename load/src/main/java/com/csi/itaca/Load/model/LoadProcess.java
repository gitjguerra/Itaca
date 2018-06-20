package com.csi.itaca.preLoad.model;

import java.util.Date;

/**
 * Created by Robert on 19/06/2018.
 */
public interface LoadProcess {

    Long getLoadProcessId();

    Long getUserId();

    Date getCreatedTimestamp();

    Long getPreloadDefinitionId();

}
