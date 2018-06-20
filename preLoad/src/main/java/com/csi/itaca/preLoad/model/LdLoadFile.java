package com.csi.itaca.preLoad.model;

import java.util.Date;

/**
 * Created by Robert on 19/06/2018.
 */
public interface LdLoadFile {

    Long getLoadFileId();

    Long getLoadProcessId();

    String getFileName();

    Long getFileSize();

    String getChecksum();

    Date getPreloadStartTime();

    Date getPreloadEndTime();

    Date getLoadStartTime();

    Date getLoadEndTime();

    Long getStatusCode();

    String getStatusMessage();

    Date getUserLoadCancel();


}
