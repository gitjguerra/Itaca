package com.csi.itaca.dataview.model;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class ColumnDefinition implements Serializable {

    private Long    COLUMN_ID;
    private String  TABLE_NAME;
    private String  COLUMN_NAME;
    private String  DATA_TYPE;
    private Long    DATA_LENGTH;
    private Long    DATA_PRECISION;
    private Long    DATA_SCALE;

    public ColumnDefinition(Long COLUMN_ID, String TABLE_NAME, String COLUMN_NAME, String DATA_TYPE, Long DATA_LENGTH, Long DATA_PRECISION, Long DATA_SCALE) {
        this.COLUMN_ID = COLUMN_ID;
        this.TABLE_NAME = TABLE_NAME;
        this.COLUMN_NAME = COLUMN_NAME;
        this.DATA_TYPE = DATA_TYPE;
        this.DATA_LENGTH = DATA_LENGTH;
        this.DATA_PRECISION = DATA_PRECISION;
        this.DATA_SCALE = DATA_SCALE;
    }

}