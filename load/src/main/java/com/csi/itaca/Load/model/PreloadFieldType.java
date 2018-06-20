package com.csi.itaca.preLoad.model;

/**
 * Created by Robert on 19/06/2018.
 */
public interface PreloadFieldType {

    Long getPreloadFieldTypeId();

    String getName();

    String getDescription();

    Long getMinLength();

    Long getMaxLength();

    String getRegex();

}
