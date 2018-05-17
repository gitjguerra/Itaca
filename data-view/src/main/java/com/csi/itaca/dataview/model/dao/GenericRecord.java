package com.csi.itaca.dataview.model.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the data within a single record.
 */
public class GenericRecord {

    List<Object> fields = new ArrayList<Object>();

    public GenericRecord() {
    }

    public void addData(Object value) {
        fields.add(value);
    }

    public void setFields(List<Object> fields) {
        this.fields = fields;
    }

    public List<Object> getFields() {
        return fields;
    }
}
