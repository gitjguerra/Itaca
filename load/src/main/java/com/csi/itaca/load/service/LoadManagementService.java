package com.csi.itaca.load.service;

import com.csi.itaca.load.job.JobCompletionNotificationListener;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;

public interface LoadManagementService {

    // Upload and download files
    void store(MultipartFile file, Path rootLocation);

    // Get upload file
    Resource loadFile(File file);

    // Job of batch process
    BatchStatus fileToDatabaseJob(JobCompletionNotificationListener listener, Path rootLocation, File file) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException;

    // TODO: Implementar metodo en rest controller para eliminar archivos subidos
    void deleteAll(Path rootLocation);
    // TODO: Implementar metodo en rest controller para crear directorio de carga de archivos
    void init(Path rootLocation);

}