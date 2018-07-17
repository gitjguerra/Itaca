package com.csi.itaca.load.model.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class GlobalDTO implements Serializable {
    private Long loadFileId;
    private Long loadProcessId;
    private Long preloadDataId;
    private Long preloadFieldDefinitionId;
    private Integer skipLimit;

    public Long getLoadFileId() {
        return loadFileId;
    }

    public void setLoadFileId(Long loadFileId) {
        this.loadFileId = loadFileId;
    }

    public Long getLoadProcessId() {
        return loadProcessId;
    }

    public void setLoadProcessId(Long loadProcessId) {
        this.loadProcessId = loadProcessId;
    }

    public Long getPreloadDataId() {
        return preloadDataId;
    }

    public void setPreloadDataId(Long preloadDataId) {
        this.preloadDataId = preloadDataId;
    }

    public Long getPreloadFieldDefinitionId() {
        return preloadFieldDefinitionId;
    }

    public void setPreloadFieldDefinitionId(Long preloadFieldDefinitionId) {
        this.preloadFieldDefinitionId = preloadFieldDefinitionId;
    }

    public Integer getSkipLimit() {
        return skipLimit;
    }

    public void setSkipLimit(Integer skipLimit) {
        this.skipLimit = skipLimit;
    }
}
