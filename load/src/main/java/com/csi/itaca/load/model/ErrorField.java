package com.csi.itaca.load.model;

/**
 * Created by Robert on 19/06/2018.
 */
public interface ErrorField {


    Long getErrFieldsId();

    PreloadData getPreloaDataId();

    String getPreloadFieldDefinitionId();

    String getValidationErrMsg();

}
