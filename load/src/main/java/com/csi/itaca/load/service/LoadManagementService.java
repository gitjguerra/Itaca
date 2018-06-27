package com.csi.itaca.load.service;

import org.springframework.batch.core.Step;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;

public interface LoadManagementService {

    // Upload and download files
    void store(MultipartFile file, Path rootLocation);
    Resource loadFile(File file);
    void deleteAll(Path rootLocation);
    void init(Path rootLocation);

    // Job of batch process
    HttpStatus fileToDatabaseJob(JobCompletionNotificationListener listener, File file);
    // Steps of batch process
    Step csvFileToDatabaseStep();
    Step txtFileToDatabaseStep();
    Step excelFileToDatabaseStep();
    Step xmlFileToDatabaseStep();

}