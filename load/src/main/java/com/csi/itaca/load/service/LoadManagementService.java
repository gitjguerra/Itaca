package com.csi.itaca.load.service;

import com.csi.itaca.load.job.JobCompletionNotificationListener;
import org.springframework.batch.core.Step;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;

public interface LoadManagementService {

    // Upload and download files
    void store(MultipartFile file, Path rootLocation);

    // Get upload file
    Resource loadFile(File file);

    // Job of batch process
    HttpStatus fileToDatabaseJob(JobCompletionNotificationListener listener, Path rootLocation, File file);

    // TODO: Implementar metodo en rest controller para eliminar archivos subidos
    void deleteAll(Path rootLocation);
    // TODO: Implementar metodo en rest controller para crear directorio de carga de archivos
    void init(Path rootLocation);

    // Steps of batch process
    /*
    Step csvFileToDatabaseStep();
    Step txtFileToDatabaseStep();
    Step excelFileToDatabaseStep();
    Step xmlFileToDatabaseStep();
    */

}