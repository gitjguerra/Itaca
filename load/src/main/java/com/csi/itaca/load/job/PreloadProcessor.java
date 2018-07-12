package com.csi.itaca.load.job;

import java.util.Random;
import com.csi.itaca.load.model.dto.PreloadData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

// TODO: Change for Itaca processor and put the fields
@Component
public class PreloadProcessor implements ItemProcessor<PreloadData, PreloadData> {

    private static final Logger log = LoggerFactory.getLogger(PreloadProcessor.class);

    @Override
    public PreloadData process(PreloadData preloadData) throws Exception {
        Random r = new Random();

        // Add the values of data

        String preloadId = preloadData.getLoadFileId().toString();
        String loadId = preloadData.getLoadFileId().toString();

        Long loadFileId = 1L;
        String loadedSuccessfully = "1";
        Long rowType = 1L;
        Long lineNumber = 1L;
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

        final PreloadData fixedCustomer = new PreloadData(r.nextLong(), loadFileId, loadedSuccessfully, rowType, lineNumber,
                dataCol1, dataCol2, dataCol3, dataCol4, dataCol5, dataCol6, dataCol7, dataCol8, dataCol9, dataCol10,
                dataCol11, dataCol12, dataCol13, dataCol14, dataCol15, dataCol16, dataCol17, dataCol18, dataCol19, dataCol20);

        log.info("Converting (" + preloadData + ") into (" + fixedCustomer + ")");

        return fixedCustomer;
    }
}