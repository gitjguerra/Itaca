package com.csi.itaca.load.job;

import java.util.Random;

import com.csi.itaca.load.model.dto.PreloadData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

// TODO: Change for Itaca processor
public class PreloadProcessor implements ItemProcessor<PreloadData, PreloadData> {

    private static final Logger log = LoggerFactory.getLogger(PreloadProcessor.class);

    @Override
    public PreloadData process(PreloadData preloadData) throws Exception {
        Random r = new Random();

        // Add the values of data
        /*
        final String preloadId = preloadData.getLoadFileId().toString();
        final String loadId = preloadData.getLoadFileId().toString();

        Long loadFileId = 1L;
        String loadedSuccessfully = "1";
        Long rowType = 1L;
        Long lineNumber = 1L;
        String dataCol1 = preloadData.getDataCol1();
        String dataCol2 = preloadData.getDataCol2();
        String dataCol3 = preloadData.getDataCol3();
        String dataCol4 = preloadData.getDataCol4();
        String dataCol5 = preloadData.getDataCol5();

        final PreloadData fixedCustomer = new PreloadData(r.nextLong(), loadFileId, loadedSuccessfully, rowType, lineNumber, dataCol1, dataCol2, dataCol3, dataCol4, dataCol5);

        log.info("Converting (" + preloadData + ") into (" + fixedCustomer + ")");
        return fixedCustomer;
        */
        return null;
    }
}