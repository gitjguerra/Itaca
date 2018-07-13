package com.csi.itaca.load.job;

import java.util.List;
import javax.sql.DataSource;

import com.csi.itaca.load.model.dto.PreloadData;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;

public class PreloadJdbcItemWriter implements ItemWriter<PreloadData> {

    private static final String INSERT_DATA = "INSERT INTO LD_PRELOAD_DATA (PRELOAD_DATA_ID, LOAD_FILE_ID, " +
            "LOADED_SUCCESSFULLY, ROW_TYPE, LINE_NUMBER, DATA_COL1, DATA_COL2, DATA_COL3, DATA_COL4, DATA_COL5, DATA_COL6, DATA_COL7) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_DATA = "update LD_PRELOAD_DATA set "+
            "DATA_COL1=?, DATA_COL2=?, DATA_COL3=? where PRELOAD_DATA_ID = ?";

    private JdbcTemplate jdbcTemplate;

    public PreloadJdbcItemWriter(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    public void write(List<? extends PreloadData> items) throws Exception {
        for (PreloadData item : items) {
            int updated = jdbcTemplate.update(
                    UPDATE_DATA,
                    item.getPreloadDataId(),
                    item.getDataCol1(),
                    item.getDataCol2(),
                    item.getDataCol3()
            );
            if (updated == 0) {
                jdbcTemplate.update(
                        INSERT_DATA,
                        item.getPreloadDataId(),
                        item.getLoadFileId(),
                        item.getLoadedSuccessfully(),
                        item.getRowType(),
                        item.getLineNumber(),
                        item.getDataCol1(),
                        item.getDataCol2(),
                        item.getDataCol3(),
                        item.getDataCol4(),
                        item.getDataCol5(),
                        item.getDataCol6(),
                        item.getDataCol7()
                );
            }
        }
    }

}