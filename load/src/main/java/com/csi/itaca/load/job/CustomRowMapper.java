package com.csi.itaca.load.job;

import com.csi.itaca.load.domain.DataIn;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomRowMapper implements RowMapper<DataIn> {

    private static final String COLUMN_TEXT1 = "Name";
    private static final String COLUMN_TEXT2 = "Description";

    @Override
    public DataIn mapRow(ResultSet resultSet, int i) throws SQLException {
        DataIn data = new DataIn();
        data.setNAME(resultSet.getString(COLUMN_TEXT1));
        data.setDESCRIPTION(resultSet.getString(COLUMN_TEXT2));
        return data;
    }
}
