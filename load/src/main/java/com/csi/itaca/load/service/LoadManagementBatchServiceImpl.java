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

        String sql = "INSERT INTO LD_PRELOAD_DATA " +
                "(PRELOAD_DATA_ID, LOAD_FILE_ID, LOADED_SUCCESSFULLY, CREATED_TIMESTAMP, ROW_TYPE, LINE_NUMBER, " +
                "DATA_COL1, DATA_COL2, DATA_COL3, DATA_COL4, DATA_COL5, DATA_COL6, DATA_COL7, DATA_COL8, DATA_COL9, DATA_COL10, " +
                "DATA_COL11, DATA_COL12, DATA_COL13, DATA_COL14, DATA_COL15, DATA_COL16, DATA_COL17, DATA_COL18, DATA_COL19, DATA_COL20, " +
                "DATA_COL21, DATA_COL22, DATA_COL23, DATA_COL24, DATA_COL25, DATA_COL26, DATA_COL27, DATA_COL28, DATA_COL29, DATA_COL30, " +
                "DATA_COL31, DATA_COL32, DATA_COL33, DATA_COL34, DATA_COL35, DATA_COL36, DATA_COL37, DATA_COL38, DATA_COL39, DATA_COL40, " +
                "DATA_COL41, DATA_COL42, DATA_COL43, DATA_COL44, DATA_COL45, DATA_COL46, DATA_COL47, DATA_COL48, DATA_COL49, DATA_COL50) " +
                "VALUES(?, ?, ?, ?, ?, ?, " +
                "?, ?, ?, ?, ?, ?, ? ,? ,? ,? " +
                ",? ,? ,? ,? ,? ,? ,? ,? ,? ,? " +
                ",? ,? ,? ,? ,? ,? ,? ,? ,? ,? " +
                ",? ,? ,? ,? ,? ,? ,? ,? ,? ,? " +
                ",? ,? ,? ,? ,? ,? ,? ,? ,? ,?)";
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
                ps.setString(14, preload.getDataCol8());
                ps.setString(15, preload.getDataCol9());
                ps.setString(16, preload.getDataCol10());
                ps.setString(17, preload.getDataCol11());
                ps.setString(18, preload.getDataCol12());
                ps.setString(19, preload.getDataCol13());
                ps.setString(20, preload.getDataCol14());
                ps.setString(21, preload.getDataCol15());
                ps.setString(22, preload.getDataCol16());
                ps.setString(23, preload.getDataCol17());
                ps.setString(24, preload.getDataCol18());
                ps.setString(25, preload.getDataCol19());
                ps.setString(26, preload.getDataCol20());
                ps.setString(27, preload.getDataCol21());
                ps.setString(28, preload.getDataCol22());
                ps.setString(29, preload.getDataCol23());
                ps.setString(30, preload.getDataCol24());
                ps.setString(31, preload.getDataCol25());
                ps.setString(32, preload.getDataCol26());
                ps.setString(33, preload.getDataCol27());
                ps.setString(34, preload.getDataCol28());
                ps.setString(35, preload.getDataCol29());
                ps.setString(36, preload.getDataCol30());
                ps.setString(37, preload.getDataCol31());
                ps.setString(38, preload.getDataCol32());
                ps.setString(39, preload.getDataCol33());
                ps.setString(40, preload.getDataCol34());
                ps.setString(41, preload.getDataCol35());
                ps.setString(42, preload.getDataCol36());
                ps.setString(43, preload.getDataCol37());
                ps.setString(44, preload.getDataCol38());
                ps.setString(45, preload.getDataCol39());
                ps.setString(46, preload.getDataCol40());
                ps.setString(47, preload.getDataCol41());
                ps.setString(48, preload.getDataCol42());
                ps.setString(49, preload.getDataCol43());
                ps.setString(50, preload.getDataCol44());
                ps.setString(51, preload.getDataCol45());
                ps.setString(52, preload.getDataCol46());
                ps.setString(53, preload.getDataCol47());
                ps.setString(54, preload.getDataCol48());
                ps.setString(55, preload.getDataCol49());
                ps.setString(56, preload.getDataCol50());
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
