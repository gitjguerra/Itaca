package com.csi.itaca.load.job;

import com.csi.itaca.load.model.dao.LoadFileEntity;
import com.csi.itaca.load.model.dao.LoadProcessEntity;
import com.csi.itaca.load.model.dto.GlobalDTO;
import com.csi.itaca.load.repository.LoadFileRepository;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class StepExecutionListenerImpl implements StepExecutionListener {

    @Autowired
    GlobalDTO globalDTO;

    @Autowired
    LoadFileRepository loadFileRepository;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        //System.out.println("***** Before starting step *****");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        List<Throwable> exceptions = stepExecution.getFailureExceptions();
        if(exceptions.isEmpty()) {
            // update end time for job LD_LOAD_FILE
            Date date = new Date();
            java.sql.Date preload_end_time = new java.sql.Date(date.getTime());

            LoadProcessEntity loadProcessEntity = new LoadProcessEntity();
            loadProcessEntity.setLoadProcessId(globalDTO.getLoadProcessId());
            List<LoadFileEntity> LoadFileEntity = loadFileRepository.findByloadProcessIdAndLoadFileId(loadProcessEntity, globalDTO.getLoadFileId());
            LoadFileEntity.get(0).setPreloadEndTime(preload_end_time);
            loadFileRepository.save(LoadFileEntity);

            return ExitStatus.COMPLETED;
        } else {
            System.out.println("This step has encountered exceptions");
            exceptions.forEach(th -> System.out.println("Exception has occurred in job"));
            return ExitStatus.FAILED;
        }
    }
}