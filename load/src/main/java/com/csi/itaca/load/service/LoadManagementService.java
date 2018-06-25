package com.csi.itaca.load.service;

import org.springframework.batch.core.Step;
import org.springframework.http.HttpStatus;

public interface LoadManagementService {

    // one JOB
    HttpStatus fileToDatabaseJob(JobCompletionNotificationListener listener);
    // STEP for each type file
    Step csvFileToDatabaseStep();
    Step txtFileToDatabaseStep();
    Step excelFileToDatabaseStep();
    Step xmlFileToDatabaseStep();
}