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
public class DynRowMapper implements RowMapper<FilaGenerico>
{
    private static Logger logger = Logger.getLogger(DynRowMapper.class);
    private int rsRow;

    //logger.info(" Ingresa a DynRowMapper");
    public FilaGenerico mapRow(ResultSet rs, int rowNum) throws SQLException {
        FilaGenerico numFila = new FilaGenerico();
          try {

              int col;
              logger.info(" rs.getMetaData().getColumnCount(): "+rs.getMetaData().getColumnCount());

              for(col = 1; col <= rs.getMetaData().getColumnCount(); col++) {
                   // logger.info(" Numero Columna : "+col);
                  numFila.addData(rs.getString(col));
                    //logger.info(" Valor Columna: "+rs.getString(col));
              }

              /*bank.setCBIC(rs.getString(5));*/
            return numFila;
        }catch(SQLException sqlEx){
            logger.info("SQL Error: "+sqlEx);

        }
        return numFila;
    }

}
