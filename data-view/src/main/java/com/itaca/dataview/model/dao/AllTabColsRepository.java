package com.itaca.dataview.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Rommel Vega
 *
 */
@Repository
@Component
public class AllTabColsRepository
{
    @Autowired
    private JdbcTemplate jdbcTemplate;



    @Transactional(readOnly=true)
    public List<AllTabCols> findAll() {
       return jdbcTemplate.query(
                "SELECT COLUMN_ID , TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_LENGTH, DATA_PRECISION, DATA_SCALE " +
                        " from all_tab_cols where owner='ITACA'" +
                       // " AND TABLE_NAME='PER_BANK' " +
                        " ORDER BY TABLE_NAME,COLUMN_ID ASC",
                (rs, rowNum) -> new AllTabCols(rs.getLong( "COLUMN_ID"),
                                        rs.getString("TABLE_NAME"),
                                        rs.getString("COLUMN_NAME"),
                                        rs.getString("DATA_TYPE"),
                                        rs.getLong("DATA_LENGTH"),
                                        rs.getLong("DATA_PRECISION"),
                                        rs.getLong("DATA_SCALE")
                ));

    }

    @Transactional(readOnly=true)
    public List<AllTabCols>findByTableName(String tablename) {
        return jdbcTemplate.query(
                "SELECT COLUMN_ID, TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_LENGTH, DATA_PRECISION, DATA_SCALE " +
                        " from all_tab_cols where owner='ITACA'" +
                        " AND TABLE_NAME='"+tablename+"' " +
                        " ORDER BY TABLE_NAME,COLUMN_ID ASC",
                (rs, rowNum) -> new AllTabCols(rs.getLong( "COLUMN_ID"),
                        rs.getString("TABLE_NAME"),
                        rs.getString("COLUMN_NAME"),
                        rs.getString("DATA_TYPE"),
                        rs.getLong("DATA_LENGTH"),
                        rs.getLong("DATA_PRECISION"),
                        rs.getLong("DATA_SCALE")
                ));


    }
    @Transactional(readOnly=true)
    public List<FilaGenerico> findAllRows(String Cols, String Tabla) {

        String SQLStmt ="SELECT " +Cols+" FROM " +Tabla+" WHERE 1=1";
        return jdbcTemplate.query(SQLStmt,new DynRowMapper());

   //return rows;
    }


    public FilaGenerico create(final FilaGenerico filagenerico, String table, String setcampos, String where) {
        String field1 = filagenerico.getCampos().get(1).toString();
        String field2 = filagenerico.getCampos().get(1).toString();
        String field3 = filagenerico.getCampos().get(1).toString();
        final String InsertTableSQL = "INSERT  INTO" + table + " VALUES(?,?,?)";

        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                try (PreparedStatement ps = connection.prepareStatement(InsertTableSQL, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, filagenerico.getCampos().get(0).toString());
                    ps.setString(2, filagenerico.getCampos().get(1).toString());
                    ps.setString(3, filagenerico.getCampos().get(2).toString());
                    return ps;
                }
            }
        }, holder);

        Long createFilaGenericoId = holder.getKey().longValue();
        filagenerico.setCampos(filagenerico.getCampos());
        return filagenerico;
    }

    public FilaGenerico update(final FilaGenerico filagenerico, String table, String setcampos, String where)
    {
        final String updateTableSQL = "UPDATE  "+table+" SET BANK_COD= ? , BANK_NAME =?";

        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                try (PreparedStatement ps = connection.prepareStatement(updateTableSQL, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, filagenerico.getCampos().get(0).toString());
                    ps.setString(2, filagenerico.getCampos().get(0).toString());
                    return ps;
                }
            }
        }, holder);

        Long updateFilaGenericoId = holder.getKey().longValue();
        filagenerico.setCampos(filagenerico.getCampos());
        return filagenerico;
    }

    public FilaGenerico delete(final FilaGenerico filagenerico, String table, String setcampos, String where)
    {
        final String deleteTableSQL = "DELETE  "+table+" SET BANK_COD= ? , BANK_NAME =?";

        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                try (PreparedStatement ps = connection.prepareStatement(deleteTableSQL, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, filagenerico.getCampos().get(0).toString());
                    ps.setString(2, filagenerico.getCampos().get(0).toString());
                    return ps;
                }
            }
        }, holder);

        Long daleteFilaGenericoId = holder.getKey().longValue();
        filagenerico.setCampos(filagenerico.getCampos());
        return filagenerico;
    }



}
