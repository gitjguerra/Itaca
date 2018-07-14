package com.csi.itaca.load.model;

/**
 * Created by Robert on 19/06/2018.
 */
public interface PreloadFile {

    Long getPreloadFileId();

    PreloadDefinition getPreloadDefinitionId();

    String getName();

    String getDescription();

    String getFilenameFormatRegex();

    String getFileType();

    String getFieldSeparator();

    String getFileLoadOrder();


}
