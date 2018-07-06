package com.csi.itaca.load.model.dto;

import com.csi.itaca.load.model.TestReader;

public class TestReaderDTO implements TestReader {
    private Long getPreloadDefinitionId;

    private String getName;

    private String getDescription;

    @Override
    public Long getPreloadDefinitionId() {
        return getPreloadDefinitionId;
    }

    @Override
    public String getName() {
        return getName;
    }

    @Override
    public String getDescription() {
        return getDescription;
    }
}
