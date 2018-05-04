package com.csi.itaca.dataview.model.dao;


import java.io.Serializable;

/**
 * @author Rommel Vega
 *
 */
public class AllTabCols implements Serializable {

    Long    COLUMN_ID;
    String  TABLE_NAME;
    String  COLUMN_NAME;
    String  DATA_TYPE;
    Long    DATA_LENGTH;
    Long    DATA_PRECISION;
    Long    DATA_SCALE;

    public AllTabCols(Long COLUMN_ID, String TABLE_NAME, String COLUMN_NAME, String DATA_TYPE, Long DATA_LENGTH, Long DATA_PRECISION, Long DATA_SCALE) {
        this.COLUMN_ID = COLUMN_ID;
        this.TABLE_NAME = TABLE_NAME;
        this.COLUMN_NAME = COLUMN_NAME;
        this.DATA_TYPE = DATA_TYPE;
        this.DATA_LENGTH = DATA_LENGTH;
        this.DATA_PRECISION = DATA_PRECISION;
        this.DATA_SCALE = DATA_SCALE;
    }
    public AllTabCols() {

    }


    public Long getCOLUMN_ID() {
        return COLUMN_ID;
    }

    public void setCOLUMN_ID(Long COLUMN_ID) {
        this.COLUMN_ID = COLUMN_ID;
    }

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public void setTABLE_NAME(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }

    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    public void setCOLUMN_NAME(String COLUMN_NAME) {
        this.COLUMN_NAME = COLUMN_NAME;
    }

    public String getDATA_TYPE() {
        return DATA_TYPE;
    }

    public void setDATA_TYPE(String DATA_TYPE) {
        this.DATA_TYPE = DATA_TYPE;
    }

    public Long getDATA_LENGTH() {
        return DATA_LENGTH;
    }

    public void setDATA_LENGTH(Long DATA_LENGTH) {
        this.DATA_LENGTH = DATA_LENGTH;
    }

    public Long getDATA_PRECISION() {
        return DATA_PRECISION;
    }

    public void setDATA_PRECISION(Long DATA_PRECISION) {
        this.DATA_PRECISION = DATA_PRECISION;
    }

    public Long getDATA_SCALE() {
        return DATA_SCALE;
    }

    public void setDATA_SCALE(Long DATA_SCALE) {
        this.DATA_SCALE = DATA_SCALE;
    }


}