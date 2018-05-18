package com.csi.itaca.dataview.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the data within a single record.
 */
@NoArgsConstructor
@Getter
@Setter
public class GenericRecord {

    private List<Object> fields = new ArrayList<Object>();

    public void addData(Object value) {
        fields.add(value);
    }
}
