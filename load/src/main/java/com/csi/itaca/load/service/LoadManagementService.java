package com.csi.itaca.load.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;

public interface LoadManagementService {

    Step csvFileToDatabaseStep();

    Job csvFileToDatabaseJob(JobCompletionNotificationListener listener);

}