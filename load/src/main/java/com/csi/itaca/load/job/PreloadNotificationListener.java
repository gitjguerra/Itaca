package com.csi.itaca.load.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PreloadNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(PreloadNotificationListener.class);
    private Date startTime, stopTime;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = new Date();
        log.info("============ JOB Start ============ \n");
        log.info("Job starts at :"+startTime);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {

            stopTime = new Date();
            log.info("Job stops at :"+ stopTime +" \n");
            log.info("============ JOB FINISHED ============ Verifying the results....\n");
        }
    }

}