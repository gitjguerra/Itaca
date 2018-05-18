package com.csi.itaca.dataview.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.core.data.EntityImpl;
import org.apache.olingo.commons.core.data.PropertyImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    /**
     * Builds entity based on this record.
     * @param colsList column definitions list.
     * @return a new Entity.
     */
    public Entity getEntity(List<ColumnDefinition> colsList) {
        Entity row = new EntityImpl();
        int numCol = 0;
        while (numCol < colsList.size()) {

            String columnType = colsList.get(numCol).getDATA_TYPE();
            if (columnType.equals("NUMBER")){
                row.addProperty(new PropertyImpl(null, colsList.get(numCol).getCOLUMN_NAME(),
                        ValueType.PRIMITIVE, Long.valueOf(getFields().get(numCol).toString())));
            }
            else if (columnType.equals("VARCHAR2")){
                row.addProperty(new PropertyImpl(null, colsList.get(numCol).getCOLUMN_NAME(),
                        ValueType.PRIMITIVE, getFields().get(numCol)));
            }
            else if (columnType.equals("DATE")){
                row.addProperty(new PropertyImpl(null, colsList.get(numCol).getCOLUMN_NAME(),
                        ValueType.PRIMITIVE, parseDate (getFields().get(numCol))));
            }
            else if (columnType.equals("CHAR")){
                row.addProperty(new PropertyImpl(null, colsList.get(numCol).getCOLUMN_NAME(),
                        ValueType.PRIMITIVE, getFields().get(numCol)));
            }
            //logger.debug("gnericRow.get("+record+").getFields().get("+numCol+") "+columnType+" "+ gnericRow.get(record).getFields().get(numCol));
            numCol++;
        }
        return row;
    }

    /**
     * Parse a date string.
     * @param dateStr date in string form.
     * @return standard java date object.
     */
    private Date parseDate(Object dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr.toString());
        }
        catch(Exception e) {
            return null;
        }
    }
}
