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

        final String preloadId = preloadData.getLoadFileId().toString();
        final String loadId = preloadData.getLoadFileId().toString();

        Long loadFileId = 0L;
        String loadedSuccessfully = "";
        Long rowType = 0L;
        Long lineNumber = 0L;
        String dataCol1 = "";
        String dataCol2 = "";
        String dataCol3 = "";

        final PreloadData fixedCustomer = new PreloadData(r.nextLong(), loadFileId, loadedSuccessfully, rowType, lineNumber, dataCol1, dataCol2, dataCol3);

        log.info("Converting (" + preloadData + ") into (" + fixedCustomer + ")");

        return fixedCustomer;
    }
}