package com.csi.itaca.load.job;

import com.csi.itaca.load.model.dto.PreloadFieldDefinitionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

public class PreloadProcessor implements ItemProcessor<PreloadFieldDefinitionDTO, PreloadFieldDefinitionDTO> {

    private static final Logger log = LoggerFactory.getLogger(PreloadProcessor.class);

    // TODO: Verified and validation business rules batch process
    @Override
    public PreloadFieldDefinitionDTO process(final PreloadFieldDefinitionDTO preloadFieldDefinitionDTO) throws Exception {

        final Long preloadFieldDefinitionId = preloadFieldDefinitionDTO.getPreloadFieldDefinitionId();
        final Long preloadRowTypeId = preloadFieldDefinitionDTO.getPreloadRowTypeId();
        final Long columnNo = preloadFieldDefinitionDTO.getColumnNo();
        final String name = preloadFieldDefinitionDTO.getName();
        final String description = preloadFieldDefinitionDTO.getDescription();
        final Long preloadFieldTypeId = preloadFieldDefinitionDTO.getPreloadFieldTypeId();
        final String regex = preloadFieldDefinitionDTO.getRegex();
        final String required = preloadFieldDefinitionDTO.getRequired();
        final Long relType = preloadFieldDefinitionDTO.getRelType();
        final Long relFieldDefinitionId = preloadFieldDefinitionDTO.getRelFieldDefinitionId();
        final String relDbTableName = preloadFieldDefinitionDTO.getRelDbTableName();
        final String relDbFieldName = preloadFieldDefinitionDTO.getRelDbFieldName();
        final Long errorSeverity = preloadFieldDefinitionDTO.getErrorSeverity();

        final PreloadFieldDefinitionDTO transformedPreloadFieldDefinitionDTO = new PreloadFieldDefinitionDTO(preloadFieldDefinitionId,preloadRowTypeId,columnNo,name,description,preloadFieldTypeId,regex,required,relType,relFieldDefinitionId,relDbTableName,relDbFieldName,errorSeverity);

        log.info("Converting (" + preloadFieldDefinitionDTO + ") into (" + transformedPreloadFieldDefinitionDTO + ")");

        return transformedPreloadFieldDefinitionDTO;
    }
}
