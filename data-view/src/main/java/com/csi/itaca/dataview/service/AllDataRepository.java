package com.csi.itaca.dataview.service;

import com.csi.itaca.dataview.model.ColumnDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rommel Vega
 *
 */
@Repository
@Component
public class AllDataRepository
{
    @Autowired
    private AllTabColsRepository ColsService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<ColumnDefinition>  AllData(String NombreTabla){
       // List<ColumnDefinition> allTabColskList = ColsService.findByTableName(NombreTabla);
        String sql= "SELECT * FROM "+NombreTabla+" ";

        List allTabColskList= jdbcTemplate.query(" "+
                /*"SELECT COLUMN_ID , TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_LENGTH,DATA_PRECISION,DATA_SCALE " +
                        " from all_tab_cols where owner='ITACA'" +
                        " AND TABLE_NAME='"+tablename+"' " +
                        " ORDER BY TABLE_NAME,COLUMN_ID ASC",*/
                " "+sql,
                (rs, rowNum) -> new ColumnDefinition(rs.getLong( "COLUMN_ID"),
                        rs.getString("TABLE_NAME"),
                        rs.getString("COLUMN_NAME"),
                        rs.getString("DATA_TYPE"),
                        rs.getLong("DATA_LENGTH"),
                        rs.getLong("DATA_PRECISION"),
                        rs.getLong("DATA_SCALE")

                ));
        return allTabColskList;

    }

    public Integer extractData( ResultSetMetaData rsmd,int nColId ) throws SQLException {
        List columns = new ArrayList();

        //ResultSetMetaData rsmd = rs.getMetaData();

        int columnCount = rsmd.getColumnCount();

        for( nColId  = 1 ; nColId <  columnCount ; nColId++){
            ColumnDefinition column = new ColumnDefinition();
            column.setCOLUMN_NAME(rsmd.getColumnName(nColId));
            column.setDATA_TYPE(rsmd.getColumnTypeName(nColId));
            column.setTABLE_NAME(rsmd.getTableName(nColId));
            column.setDATA_PRECISION((long) rsmd.getPrecision(nColId));
            column.setDATA_SCALE((long) rsmd.getScale(nColId));
            //column.setDATA_SCALE(sqlTable.getName().toUpperCase());
            columns.add(column);
        }
        return columnCount;
    }





}
