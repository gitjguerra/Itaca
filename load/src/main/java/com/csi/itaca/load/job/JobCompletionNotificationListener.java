package com.csi.itaca.load.job;

import java.util.Date;
import java.util.List;

import com.csi.itaca.load.model.PreloadDataDao;
import com.csi.itaca.load.model.dto.PreloadData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
    private final PreloadDataDao preloadDataDao;
    private Date startTime, stopTime;
    public JobCompletionNotificationListener(PreloadDataDao preloadDataDao) {
        this.preloadDataDao = preloadDataDao;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = new Date();
        log.info("============ JOB Start ============ \n");
        log.info("Job starts at :"+startTime);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            stopTime = new Date();
            log.info("Job stops at :"+ stopTime +" \n");
            log.info("============ JOB FINISHED ============ Verifying the results....\n");

            // TODO:  test for job process successful - Delete for production
            List<PreloadData> preloadDataDaos = preloadDataDao.loadAllPreloadDatas();
            for (PreloadData preloadData : preloadDataDaos) {
                log.info("Found <" + preloadData + "> in the database.");
            }
        }
    }
}