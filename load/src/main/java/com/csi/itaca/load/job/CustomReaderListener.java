package com.csi.itaca.load.job;

import com.csi.itaca.load.model.dto.PreloadDataDTO;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class CustomReaderListener implements ItemReadListener<PreloadDataDTO> {

    @Override
    public void beforeRead() {
    }

    @Override
    public void afterRead(PreloadDataDTO item) {
    }

    @Override
    public void onReadError(Exception ex) {

        // Grabar en BD ld_error_field
        //err_fields_id number Primary key.
        //preload_data_id number(ld_preload_data.preload_data_id) The row this error is associated
        //preload_field_definition_id number(preload_field_definition.preload_field_definition_id)  Associate preload definition field
        //validation_err_msg varchar Contains a description the detected validation error for this field.

        System.out.println("***** Ocurrio un error:  " + ex.getMessage() + " ***** registrando en LD_ERROR_FIELD");

        int result = 0;
        String INSERT_QUERY = "";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        /*
        JdbcTemplate template = new JdbcTemplate(dataSource);

        template.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
                statement.setLong(1, beginning); // set parameters you need in your insert

                return statement;
            }
        });
        */
        System.out.println("Status 201 - Preloading in progress (errors detected): "+ result);
    }

}
