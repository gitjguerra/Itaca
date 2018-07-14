package com.csi.itaca.load.model;

/**
 * Created by Robert on 19/06/2018.
 */
public interface PreloadRowType {

    Long getPreloadRowTypeId();

    PreloadFile getPreloadFileId();

    String getName();

    String getDescription();

    Long getIdentifierColumnNo();

    String getIdentifierValue();

}
