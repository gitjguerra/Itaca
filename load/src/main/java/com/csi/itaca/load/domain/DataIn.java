package com.csi.itaca.load.domain;

import java.io.Serializable;

/**
 * Created by gustavo on 8/31/15.
 */
public class DataIn implements Serializable {

    private String PRELOAD_FIELD_DEFINITION_ID;
    private String PRELOAD_ROW_TYPE_ID;
    private String COLUMN_NO;
    private String NAME;
    private String DESCRIPTION;
    private String PRELOAD_FIELD_TYPE_ID;
    private String REGEX;
    private String REQUIRED;
    private String REL_TYPE;
    private String REL_FIELD_DEFINITION_ID;
    private String REL_DB_TABLE_NAME;
    private String REL_DB_FIELD_NAME;
    private String ERROR_SEVERITY;

    public DataIn() {
    }

    public String getPRELOAD_FIELD_DEFINITION_ID() {
        return PRELOAD_FIELD_DEFINITION_ID;
    }

    public void setPRELOAD_FIELD_DEFINITION_ID(String PRELOAD_FIELD_DEFINITION_ID) {
        this.PRELOAD_FIELD_DEFINITION_ID = PRELOAD_FIELD_DEFINITION_ID;
    }

    public String getPRELOAD_ROW_TYPE_ID() {
        return PRELOAD_ROW_TYPE_ID;
    }

    public void setPRELOAD_ROW_TYPE_ID(String PRELOAD_ROW_TYPE_ID) {
        this.PRELOAD_ROW_TYPE_ID = PRELOAD_ROW_TYPE_ID;
    }

    public String getCOLUMN_NO() {
        return COLUMN_NO;
    }

    public void setCOLUMN_NO(String COLUMN_NO) {
        this.COLUMN_NO = COLUMN_NO;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getPRELOAD_FIELD_TYPE_ID() {
        return PRELOAD_FIELD_TYPE_ID;
    }

    public void setPRELOAD_FIELD_TYPE_ID(String PRELOAD_FIELD_TYPE_ID) {
        this.PRELOAD_FIELD_TYPE_ID = PRELOAD_FIELD_TYPE_ID;
    }

    public String getREGEX() {
        return REGEX;
    }

    public void setREGEX(String REGEX) {
        this.REGEX = REGEX;
    }

    public String getREQUIRED() {
        return REQUIRED;
    }

    public void setREQUIRED(String REQUIRED) {
        this.REQUIRED = REQUIRED;
    }

    public String getREL_TYPE() {
        return REL_TYPE;
    }

    public void setREL_TYPE(String REL_TYPE) {
        this.REL_TYPE = REL_TYPE;
    }

    public String getREL_FIELD_DEFINITION_ID() {
        return REL_FIELD_DEFINITION_ID;
    }

    public void setREL_FIELD_DEFINITION_ID(String REL_FIELD_DEFINITION_ID) {
        this.REL_FIELD_DEFINITION_ID = REL_FIELD_DEFINITION_ID;
    }

    public String getREL_DB_TABLE_NAME() {
        return REL_DB_TABLE_NAME;
    }

    public void setREL_DB_TABLE_NAME(String REL_DB_TABLE_NAME) {
        this.REL_DB_TABLE_NAME = REL_DB_TABLE_NAME;
    }

    public String getREL_DB_FIELD_NAME() {
        return REL_DB_FIELD_NAME;
    }

    public void setREL_DB_FIELD_NAME(String REL_DB_FIELD_NAME) {
        this.REL_DB_FIELD_NAME = REL_DB_FIELD_NAME;
    }

    public String getERROR_SEVERITY() {
        return ERROR_SEVERITY;
    }

    public void setERROR_SEVERITY(String ERROR_SEVERITY) {
        this.ERROR_SEVERITY = ERROR_SEVERITY;
    }
}
