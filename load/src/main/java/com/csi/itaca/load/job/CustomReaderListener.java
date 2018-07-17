package com.csi.itaca.load.job;

import com.csi.itaca.load.model.dao.ErrorFieldEntity;
import com.csi.itaca.load.model.dao.LoadFileEntity;
import com.csi.itaca.load.model.dao.LoadProcessEntity;
import com.csi.itaca.load.model.dto.GlobalDTO;
import com.csi.itaca.load.model.dto.PreloadDataDTO;
import com.csi.itaca.load.repository.ErrorFieldRepository;
import com.csi.itaca.load.repository.LoadFileRepository;
import com.csi.itaca.load.utils.Constants;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class CustomReaderListener implements ItemReadListener<PreloadDataDTO> {

    @Autowired
    ErrorFieldRepository errorFieldRepository;

    @Autowired
    LoadFileRepository loadFileRepository;

    @Autowired
    GlobalDTO globalDTO;

    private Long preloadDataId;
    private Long preloadFieldDefinitionId;

    @Override
    public void beforeRead() {
    }

    @Override
    public void afterRead(PreloadDataDTO item) {
        preloadDataId = item.getPreloadDataId();
        preloadFieldDefinitionId = Long.valueOf(globalDTO.getPreloadFieldDefinitionId());
    }

    @Override
    public void onReadError(Exception ex) {

        Random random = new Random();
        ErrorFieldEntity errorFieldEntity = new ErrorFieldEntity();
        errorFieldEntity.setErrFieldsId(random.nextLong());
        errorFieldEntity.setPreloaDataId(preloadDataId);
        errorFieldEntity.setPreloadFieldDefinitionId(preloadFieldDefinitionId.toString());
        errorFieldEntity.setValidationErrMsg(ex.getMessage().substring(0,200));
        errorFieldEntity = errorFieldRepository.saveAndFlush(errorFieldEntity);

        // On error update LD_LOAD_FILE
        LoadProcessEntity loadProcessEntity = new LoadProcessEntity();
        loadProcessEntity.setLoadProcessId(globalDTO.getLoadProcessId());
        List<LoadFileEntity> LoadFileEntity = loadFileRepository.findByloadProcessIdAndLoadFileId(loadProcessEntity, globalDTO.getLoadFileId());
        LoadFileEntity.get(0).setStatusCode(Long.valueOf(Constants.getPreloadingInProgressWithErrors()));
        LoadFileEntity.get(0).setStatusMessage(Constants.getPreloadingInProgressWithErrorsDesc());
        loadFileRepository.save(LoadFileEntity);

    }

}
