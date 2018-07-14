package com.csi.itaca.load.model;

/**
 * Created by Robert on 19/06/2018.
 */
public interface PreloadFieldDefinition {

    Long getPreloadFieldDefinitionId();

    PreloadRowType getPreloadRowTypeId();

    Long getColumnNo();

    Long getLength();

    String getName();

    String getDescription();

    PreloadFieldType getPreloadFieldTypeId();

    String getRegex();

    String getRequired();

    Long getRelType();

    Long getRelFieldDefinitionId();

    String getRelDbTableName();

    String getRelDbFieldName();

    Long getErrorSeverity();
}
