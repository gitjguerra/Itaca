package com.csi.itaca.load.model;

import com.csi.itaca.load.model.dto.PreloadDefinitionDTO;
import org.springframework.batch.item.ItemProcessor;

public class PreloadItemProcessor implements ItemProcessor<PreloadDefinitionDTO, PreloadDefinitionDTO> {

    @Override
    public PreloadDefinitionDTO process(PreloadDefinitionDTO preload) throws Exception {
        return preload;
    }
}