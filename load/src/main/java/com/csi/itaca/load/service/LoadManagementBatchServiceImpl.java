package com.csi.itaca.load.service;

import com.csi.itaca.load.model.dto.PreloadDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class LoadManagementBatchServiceImpl extends JdbcDaoSupport implements LoadManagementBatchService {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public void insert(List<? extends PreloadDataDTO> preloadData) {

        String sql = "INSERT INTO LD_PRELOAD_DATA \n" +
                "(PRELOAD_DATA_ID, LOAD_FILE_ID, LOADED_SUCCESSFULLY, CREATED_TIMESTAMP, ROW_TYPE, LINE_NUMBER, DATA_COL1, DATA_COL2, DATA_COL3, DATA_COL4, DATA_COL5, DATA_COL6, DATA_COL7) \n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PreloadDataDTO preload = preloadData.get(i);
                ps.setLong(1, preload.getPreloadDataId());
                ps.setLong(2, preload.getLoadFileId().getLoadFileId().longValue());
                ps.setString(3, preload.getLoadedSuccessfully());
                ps.setDate(4, (java.sql.Date) preload.getCreatedTimeStamp());
                ps.setLong(5, preload.getPreloadRowTypeId().getPreloadRowTypeId().longValue());
                ps.setLong(6, preload.getLineNumber());
                ps.setString(7, preload.getDataCol1());
                ps.setString(8, preload.getDataCol2());
                ps.setString(9, preload.getDataCol3());
                ps.setString(10, preload.getDataCol4());
                ps.setString(11, preload.getDataCol5());
                ps.setString(12, preload.getDataCol6());
                ps.setString(13, preload.getDataCol7());
            }

            public int getBatchSize() {
                return preloadData.size();
            }
        });

    }

    @Override
    public List<PreloadDataDTO> loadAllPreloadDatas() {
        String sql = "SELECT * FROM LD_PRELOAD_DATA";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

        List<PreloadDataDTO> result = new ArrayList<PreloadDataDTO>();
        for (Map<String, Object> row : rows) {
            //PreloadDataDTO preloadData = new PreloadDataDTO();
            //Long valor = Long.parseLong(row.get("id").toString());
            //preloadData.setPreloadDataId(valor);
            //preloadData.setLoadFileId((String) row.get("first_name"));
            //preloadData.setLastName((String) row.get("last_name"));
            //result.add(preloadData);
        }

        return result;
    }
}
