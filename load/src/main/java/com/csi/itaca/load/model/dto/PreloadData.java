package com.csi.itaca.load.model.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PreloadData {
    private Long preloadDataId;

    private Long loadFileId;

    private String loadedSuccessfully;

    private Long rowType;

    private Long lineNumber;

    private String dataCol1;

    private String dataCol2;

    private String dataCol3;

    private String dataCol4;

    private String dataCol5;

    public void setPreloadDataId(Long preloadDataId) {
        this.preloadDataId = preloadDataId;
    }

    public Long getLoadFileId() {
        return loadFileId;
    }

    public void setLoadFileId(Long loadFileId) {
        this.loadFileId = loadFileId;
    }

    public String getLoadedSuccessfully() {
        return loadedSuccessfully;
    }

    public void setLoadedSuccessfully(String loadedSuccessfully) {
        this.loadedSuccessfully = loadedSuccessfully;
    }

    public Long getRowType() {
        return rowType;
    }

    public void setRowType(Long rowType) {
        this.rowType = rowType;
    }

    public Long getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Long lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getDataCol1() {
        return dataCol1;
    }

    public void setDataCol1(String dataCol1) {
        this.dataCol1 = dataCol1;
    }

    public String getDataCol2() {
        return dataCol2;
    }

    public void setDataCol2(String dataCol2) {
        this.dataCol2 = dataCol2;
    }

    public String getDataCol3() {
        return dataCol3;
    }

    public void setDataCol3(String dataCol3) {
        this.dataCol3 = dataCol3;
    }

    public String getDataCol4() {
        return dataCol4;
    }

    public void setDataCol4(String dataCol4) {
        this.dataCol4 = dataCol4;
    }

    public String getDataCol5() {
        return dataCol5;
    }

    public void setDataCol5(String dataCol5) {
        this.dataCol5 = dataCol5;
    }

    public PreloadData() {
    }

    public PreloadData(Long preloadDataId, Long loadFileId, String loadedSuccessfully, Long rowType, Long lineNumber, String dataCol1, String dataCol2, String dataCol3, String dataCol4, String dataCol5) {
        this.preloadDataId = preloadDataId;
        this.loadFileId = loadFileId;
        this.loadedSuccessfully = loadedSuccessfully;
        this.rowType = rowType;
        this.lineNumber = lineNumber;
        this.dataCol1 = dataCol1;
        this.dataCol2 = dataCol2;
        this.dataCol3 = dataCol3;
        this.dataCol4 = dataCol4;
        this.dataCol5 = dataCol5;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("preloadDataId", this.preloadDataId)
                .append("loadFileId", this.loadFileId = loadFileId)
                .append("loadedSuccessfully", this.loadedSuccessfully)
                .append("rowType", this.rowType)
                .append("lineNumber", this.lineNumber)
                .append("dataCol1", this.dataCol1)
                .append("dataCol2", this.dataCol2)
                .append("dataCol3", this.dataCol3)
                .append("dataCol4", this.dataCol4)
                .append("dataCol5", this.dataCol5)
                .toString();
    }

}