package com.csi.itaca.load.job;

import java.util.List;
import javax.sql.DataSource;

import com.csi.itaca.load.model.dto.PreloadData;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

public class PreloadJdbcItemWriter implements ItemWriter<PreloadData> {
    private static final String INSERT_DATA = "insert into product "+
            "(id,name,description,price) values(?,?,?,?)";
    private static final String UPDATE_DATA = "update product set "+
            "name=?, description=?, price=? where id = ?";
    private JdbcTemplate jdbcTemplate;

    public PreloadJdbcItemWriter(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }
    public void write(List<? extends PreloadData> items) throws Exception {
        for (PreloadData item : items) {
            int updated = jdbcTemplate.update(
                    UPDATE_DATA,
                    item.getLoadFileId(),item.getLoadFileId(),
                    item.getDataCol1(),item.getDataCol1()
            );
            if (updated == 0) {
                jdbcTemplate.update(
                        INSERT_DATA,
                        item.getLoadFileId(),item.getPreloadDataId(),
                        item.getDataCol1(),item.getDataCol1()                );
            }
        }
    }

}