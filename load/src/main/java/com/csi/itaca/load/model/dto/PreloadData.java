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

    private String dataCol6;

    private String dataCol7;

    private String dataCol8;

    private String dataCol9;

    private String dataCol10;

    private String dataCol11;

    private String dataCol12;

    private String dataCol13;

    private String dataCol14;

    private String dataCol15;

    private String dataCol16;

    private String dataCol17;

    private String dataCol18;

    private String dataCol19;

    private String dataCol20;

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

    public Long getPreloadDataId() {
        return preloadDataId;
    }

    public String getDataCol6() {
        return dataCol6;
    }

    public void setDataCol6(String dataCol6) {
        this.dataCol6 = dataCol6;
    }

    public String getDataCol7() {
        return dataCol7;
    }

    public void setDataCol7(String dataCol7) {
        this.dataCol7 = dataCol7;
    }

    public String getDataCol8() {
        return dataCol8;
    }

    public void setDataCol8(String dataCol8) {
        this.dataCol8 = dataCol8;
    }

    public String getDataCol9() {
        return dataCol9;
    }

    public void setDataCol9(String dataCol9) {
        this.dataCol9 = dataCol9;
    }

    public String getDataCol10() {
        return dataCol10;
    }

    public void setDataCol10(String dataCol10) {
        this.dataCol10 = dataCol10;
    }

    public String getDataCol11() {
        return dataCol11;
    }

    public void setDataCol11(String dataCol11) {
        this.dataCol11 = dataCol11;
    }

    public String getDataCol12() {
        return dataCol12;
    }

    public void setDataCol12(String dataCol12) {
        this.dataCol12 = dataCol12;
    }

    public String getDataCol13() {
        return dataCol13;
    }

    public void setDataCol13(String dataCol13) {
        this.dataCol13 = dataCol13;
    }

    public String getDataCol14() {
        return dataCol14;
    }

    public void setDataCol14(String dataCol14) {
        this.dataCol14 = dataCol14;
    }

    public String getDataCol15() {
        return dataCol15;
    }

    public void setDataCol15(String dataCol15) {
        this.dataCol15 = dataCol15;
    }

    public String getDataCol16() {
        return dataCol16;
    }

    public void setDataCol16(String dataCol16) {
        this.dataCol16 = dataCol16;
    }

    public String getDataCol17() {
        return dataCol17;
    }

    public void setDataCol17(String dataCol17) {
        this.dataCol17 = dataCol17;
    }

    public String getDataCol18() {
        return dataCol18;
    }

    public void setDataCol18(String dataCol18) {
        this.dataCol18 = dataCol18;
    }

    public String getDataCol19() {
        return dataCol19;
    }

    public void setDataCol19(String dataCol19) {
        this.dataCol19 = dataCol19;
    }

    public String getDataCol20() {
        return dataCol20;
    }

    public void setDataCol20(String dataCol20) {
        this.dataCol20 = dataCol20;
    }

    public PreloadData() {
    }

    public PreloadData(Long preloadDataId, Long loadFileId, String loadedSuccessfully, Long rowType, Long lineNumber, String dataCol1, String dataCol2, String dataCol3, String dataCol4, String dataCol5, String dataCol6, String dataCol7, String dataCol8, String dataCol9, String dataCol10, String dataCol11, String dataCol12, String dataCol13, String dataCol14, String dataCol15, String dataCol16, String dataCol17, String dataCol18, String dataCol19, String dataCol20) {
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
        this.dataCol6 = dataCol6;
        this.dataCol7 = dataCol7;
        this.dataCol8 = dataCol8;
        this.dataCol9 = dataCol9;
        this.dataCol10 = dataCol10;
        this.dataCol11 = dataCol11;
        this.dataCol12 = dataCol12;
        this.dataCol13 = dataCol13;
        this.dataCol14 = dataCol14;
        this.dataCol15 = dataCol15;
        this.dataCol16 = dataCol16;
        this.dataCol17 = dataCol17;
        this.dataCol18 = dataCol18;
        this.dataCol19 = dataCol19;
        this.dataCol20 = dataCol20;
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
                .append("dataCol6", this.dataCol6)
                .append("dataCol7", this.dataCol7)
                .append("dataCol8", this.dataCol8)
                .append("dataCol9", this.dataCol9)
                .append("dataCol10", this.dataCol10)
                .append("dataCol11", this.dataCol11)
                .append("dataCol12", this.dataCol12)
                .append("dataCol13", this.dataCol13)
                .append("dataCol14", this.dataCol14)
                .append("dataCol15", this.dataCol15)
                .append("dataCol16", this.dataCol16)
                .append("dataCol17", this.dataCol17)
                .append("dataCol18", this.dataCol18)
                .append("dataCol19", this.dataCol19)
                .append("dataCol20", this.dataCol20)
                .toString();
    }

}