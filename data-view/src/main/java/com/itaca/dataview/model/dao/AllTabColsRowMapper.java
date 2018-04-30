package com.itaca.dataview.model.dao;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Rommel Vega
 *
 */
@Component
public class AllTabColsRowMapper implements RowMapper<AllTabCols>
{

    private static Logger logger = Logger.getLogger(AllTabColsRowMapper.class);

    public AllTabCols mapRow(ResultSet rs, int rowNum) throws SQLException {
        AllTabCols alltabcols = new AllTabCols();
          try {
              alltabcols.setCOLUMN_ID(rs.getLong( "COLUMN_ID"));
              alltabcols.setTABLE_NAME(rs.getString("TABLE_NAME"));
              alltabcols.setCOLUMN_NAME(rs.getString("COLUMN_NAME"));
              alltabcols.setDATA_TYPE(rs.getString("DATA_TYPE"));
              alltabcols.setDATA_LENGTH(rs.getLong("DATA_LENGTH"));
              alltabcols.setDATA_PRECISION(rs.getLong("DATA_PRECISION"));
              alltabcols.setDATA_SCALE(rs.getLong("DATA_SCALE"));

            return alltabcols;
        }catch(SQLException sqlEx){
            logger.info("SQL Error: "+sqlEx);

        }

        return alltabcols;
    }

}
