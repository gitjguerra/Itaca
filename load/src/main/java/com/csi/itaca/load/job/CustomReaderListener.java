package com.csi.itaca.load.job;

import com.csi.itaca.load.model.dao.ErrorFieldEntity;
import com.csi.itaca.load.model.dao.LoadFileEntity;
import com.csi.itaca.load.model.dao.LoadProcessEntity;
import com.csi.itaca.load.model.dto.GlobalDTO;
import com.csi.itaca.load.model.dto.PreloadDataDTO;
import com.csi.itaca.load.repository.ErrorFieldRepository;
import com.csi.itaca.load.repository.LoadFileRepository;
import com.csi.itaca.load.service.LoadManagementServiceImpl;
import com.csi.itaca.load.utils.Constants;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomReaderListener implements ItemReadListener<PreloadDataDTO> {

    @Autowired
    private LoadManagementServiceImpl managementService;

    @Autowired
    private ErrorFieldRepository errorFieldRepository;

    @Autowired
    private LoadFileRepository loadFileRepository;

    @Autowired
    private GlobalDTO globalDTO;

    private Long preloadDataId;
    private Long preloadFieldDefinitionId;

    @Override
    public void beforeRead() {
    }

    @Override
    public void afterRead(PreloadDataDTO item) {
        preloadDataId = item.getPreloadDataId();
        preloadFieldDefinitionId = globalDTO.getPreloadFieldDefinitionId();
    }

    @Override
    public void onReadError(Exception ex) {

        Long errorFieldId = managementService.findNextVal("SEQ_ERROR_FIELD_Id.NEXTVAL");
        ErrorFieldEntity errorFieldEntity = new ErrorFieldEntity();
        errorFieldEntity.setErrFieldsId(errorFieldId);

        // ** Not exist preloadDataId because is an error **
        //errorFieldEntity.setPreloaDataId(preloadDataId);

        errorFieldEntity.setPreloadFieldDefinitionId(preloadFieldDefinitionId.toString());
        errorFieldEntity.setValidationErrMsg(ex.getMessage().substring(0, Integer.parseInt(Constants.getMaxLengthMssgError())));
        errorFieldRepository.saveAndFlush(errorFieldEntity);

        // On error update LD_LOAD_FILE
        LoadProcessEntity loadProcessEntity = new LoadProcessEntity();
        loadProcessEntity.setLoadProcessId(globalDTO.getLoadProcessId());
        List<LoadFileEntity> LoadFileEntity = loadFileRepository.findByloadProcessIdAndLoadFileId(loadProcessEntity, globalDTO.getLoadFileId());
        LoadFileEntity.get(0).setStatusCode(Long.valueOf(Constants.getPreloadingInProgressWithErrors()));
        LoadFileEntity.get(0).setStatusMessage(Constants.getPreloadingInProgressWithErrorsDesc());
        loadFileRepository.save(LoadFileEntity);

    }

}
