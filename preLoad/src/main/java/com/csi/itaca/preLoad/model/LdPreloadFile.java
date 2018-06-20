package com.csi.itaca.preLoad.model;

/**
 * Created by Robert on 19/06/2018.
 */
public interface LdPreloadFile {

    Long getPreloadFileId();

    Long getPreloadDefinitionId();

    String getName();

    String getDescription();

    String getFilenameFormatRegex();

}
