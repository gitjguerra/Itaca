package com.csi.itaca.load.model.dao;

import com.csi.itaca.load.model.PreloadDataDao;
import com.csi.itaca.load.model.dto.PreloadData;
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
public class PreloadDataDaoImpl extends JdbcDaoSupport implements PreloadDataDao {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public void insert(List<? extends PreloadData> preloadData) {
        String sql = "INSERT INTO LD_PRELOAD_DATA " + "(PRELOAD_DATA_ID, LOAD_FILE_ID, LOADED_SUCCESSFULLY, ROW_TYPE, LINE_NUMBER, DATA_COL1, DATA_COL2, DATA_COL3) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                PreloadData preload = preloadData.get(i);
                ps.setLong(1, preload.getLoadFileId());
                ps.setString(2, preload.getLoadedSuccessfully());
                ps.setLong(3, preload.getLineNumber());
                ps.setLong(4, preload.getRowType());
                ps.setString(5, preload.getDataCol1());
                ps.setString(6, preload.getDataCol2());
                ps.setString(7, preload.getDataCol3());
            }

            public int getBatchSize() {
                return preloadData.size();
            }
        });

    }

    @Override
    public List<PreloadData> loadAllPreloadDatas() {
        String sql = "SELECT * FROM LD_PRELOAD_DATA";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

        List<PreloadData> result = new ArrayList<PreloadData>();
        for (Map<String, Object> row : rows) {
            PreloadData preloadData = new PreloadData();
            Long valor = Long.parseLong(row.get("id").toString());
            preloadData.setPreloadDataId(valor);
            //preloadData.setLoadFileId((String) row.get("first_name"));
            //preloadData.setLastName((String) row.get("last_name"));
            result.add(preloadData);
        }

        return result;
    }
}
