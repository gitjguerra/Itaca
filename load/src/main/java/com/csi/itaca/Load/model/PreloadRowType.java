package com.csi.itaca.preLoad.model;

/**
 * Created by Robert on 19/06/2018.
 */
public interface PreloadRowType {

    Long getPreloadRowTypeId();

    Long getPreloadFileId();

    String getName();

    String getDescription();

    Long getIdentifierColumnNo();

    String getIdentifierValue();

}
