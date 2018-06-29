package com.csi.itaca.load.service;

import com.csi.itaca.load.model.dto.PreloadDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class PreloadProcessor implements ItemProcessor<PreloadDataDTO, PreloadDataDTO> {

    private static final Logger log = LoggerFactory.getLogger(PreloadProcessor.class);

    // TODO: Verified and validation business rules batch process
    @Override
    public PreloadDataDTO process(final PreloadDataDTO PreloadDataDTO) throws Exception {
        final Long preloadDataId = PreloadDataDTO.getPreloadDataId();
        final Long loadFileId = PreloadDataDTO.getLoadFileId();
        final String loadedSuccessfully = PreloadDataDTO.getLoadedSuccessfully();
        final Long rowType = PreloadDataDTO.getRowType();
        final Long lineNumber = PreloadDataDTO.getLineNumber();
        final String dataCol1 = PreloadDataDTO.getDataCol1();
        final String dataCol2 = PreloadDataDTO.getDataCol2();
        final String dataCol3 = PreloadDataDTO.getDataCol3();

        final PreloadDataDTO transformedPreloadDataDTO = new PreloadDataDTO(preloadDataId, loadFileId, loadedSuccessfully,
                rowType, lineNumber, dataCol1, dataCol2, dataCol3);

        log.info("Converting (" + PreloadDataDTO + ") into (" + transformedPreloadDataDTO + ")");

        return transformedPreloadDataDTO;
    }
}