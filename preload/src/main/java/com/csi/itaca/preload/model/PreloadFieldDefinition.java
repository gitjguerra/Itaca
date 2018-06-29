package com.csi.itaca.preload.model;

/**
 * Created by Robert on 19/06/2018.
 */
public interface PreloadFieldDefinition {

    Long getPreloadFieldDefinitionId();

    Long getPreloadRowTypeId();

    Long getColumnNo();

    String getName();

    String getDescription();

    Long getPreloadFieldTypeId();

    String getRegex();

    String getRequired();

    Long getRelType();

    Long getRelFieldDefinitionId();

    String getRelDbTableName();

    String getRelDbFieldName();

    Long getErrorSeverity();
}
