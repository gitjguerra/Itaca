package com.csi.itaca.load.job;

import java.util.List;
import java.util.Random;

import com.csi.itaca.load.model.PreloadRowType;
import com.csi.itaca.load.model.dto.PreloadData;
import com.csi.itaca.load.model.dto.PreloadFieldDefinitionDTO;
import com.csi.itaca.load.model.dto.PreloadRowTypeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

// TODO: Change for Itaca processor and put the fields
@Component
public class PreloadProcessor implements ItemProcessor<PreloadData, PreloadData> {

    private static final Logger log = LoggerFactory.getLogger(PreloadProcessor.class);

    @Override
    public PreloadData process(PreloadData preloadData) throws Exception {

        Long preloadId = preloadData.getPreloadDataId();
        Long loadFileId = preloadData.getLoadFileId();
        String loadedSuccessfully = preloadData.getLoadedSuccessfully();

        Long rowType = preloadData.getRowType();
        Long lineNumber = preloadData.getLineNumber();
        String dataCol1 = preloadData.getDataCol1();
        String dataCol2 = preloadData.getDataCol2();
        String dataCol3 = preloadData.getDataCol3();
        String dataCol4 = preloadData.getDataCol4();
        String dataCol5 = preloadData.getDataCol5();
        String dataCol6 = preloadData.getDataCol6();
        String dataCol7 = preloadData.getDataCol7();
        String dataCol8 = preloadData.getDataCol8();
        String dataCol9 = preloadData.getDataCol9();
        String dataCol10 = preloadData.getDataCol10();
        String dataCol11 = preloadData.getDataCol11();
        String dataCol12 = preloadData.getDataCol12();
        String dataCol13 = preloadData.getDataCol13();
        String dataCol14 = preloadData.getDataCol14();
        String dataCol15 = preloadData.getDataCol15();
        String dataCol16 = preloadData.getDataCol16();
        String dataCol17 = preloadData.getDataCol17();
        String dataCol18 = preloadData.getDataCol18();
        String dataCol19 = preloadData.getDataCol19();
        String dataCol20 = preloadData.getDataCol20();

        final PreloadData fixedCustomer = new PreloadData(preloadId, loadFileId, loadedSuccessfully, rowType, lineNumber,
                dataCol1, dataCol2, dataCol3, dataCol4, dataCol5, dataCol6, dataCol7, dataCol8, dataCol9, dataCol10,
                dataCol11, dataCol12, dataCol13, dataCol14, dataCol15, dataCol16, dataCol17, dataCol18, dataCol19, dataCol20);

        log.info("Converting (" + preloadData + ") into (" + fixedCustomer + ")");

        return fixedCustomer;
    }
}