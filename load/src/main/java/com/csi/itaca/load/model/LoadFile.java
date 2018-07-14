package com.csi.itaca.load.model;

import java.util.Date;

/**
 * Created by Robert on 19/06/2018.
 */
public interface LoadFile {

    Long getLoadFileId();

    LoadProcess getLoadProcessId();

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
