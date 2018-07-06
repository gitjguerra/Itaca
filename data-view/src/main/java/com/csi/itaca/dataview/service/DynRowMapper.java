package com.csi.itaca.dataview.service;

import com.csi.itaca.dataview.model.GenericRecord;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Rommel Vega
 *
 */
public class DynRowMapper implements RowMapper<GenericRecord>
{
    private static Logger logger = Logger.getLogger(DynRowMapper.class);

    public GenericRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
        GenericRecord numFila = new GenericRecord();
        try {
            for(int col = 1; col <= rs.getMetaData().getColumnCount(); col++) {
                numFila.addData(rs.getString(col));
            }
            return numFila;
        } catch(SQLException sqlEx){
            logger.error("SQL Error: "+sqlEx, sqlEx);
        }
        return numFila;
    }

}
