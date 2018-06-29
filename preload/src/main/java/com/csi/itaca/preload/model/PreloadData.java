package com.csi.itaca.preload.model;

/**
 * Created by Robert on 19/06/2018.
 */
public interface PreloadData {

    Long getPreloadDataId();

    Long getLoadFileId();

    String getLoadedSuccessfully();

    Long getRowType();

    Long getLineNumber();

    String getDataCol1();

    String getDataCol2();

    String getDataCol3();

}
