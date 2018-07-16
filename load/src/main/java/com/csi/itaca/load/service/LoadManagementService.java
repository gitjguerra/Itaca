package com.csi.itaca.load.service;

import com.csi.itaca.load.model.dto.PreloadDefinitionDTO;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import com.csi.itaca.load.model.dao.LoadFileEntity;
import com.csi.itaca.load.model.dao.LoadProcessEntity;
import com.csi.itaca.load.model.dao.PreloadRowTypeEntity;
import com.csi.itaca.load.model.dto.LoadFileDTO;
import com.csi.itaca.load.model.dto.PreloadDataDTO;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public interface LoadManagementService {

    // Upload and download files
    void store(MultipartFile file, Path rootLocation);

    // Get upload file
    Resource loadFile(File file);

    // Job of batch process
    BatchStatus fileToDatabaseJob(Path rootLocation, File file) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException;

    // TODO: Implementar metodo en rest controller para eliminar archivos subidos
    void deleteAll(Path rootLocation);
    // TODO: Implementar metodo en rest controller para crear directorio de carga de archivos
    void init(Path rootLocation);

    //Metodos LOAD

    List<LoadFileDTO> getFile(LoadProcessEntity loadProcessId, Long loadFileId);
  //  List<LoadFileDTO> getFile(Long loadProcessId, Pagination pagination, Order order);
  //  List<PreloadDataDTO> getPreloadData(LoadFileEntity loadFileId, Pagination pagination, Order order);

    List<PreloadDataDTO> preloadedRowList(Long loadProcessId, LoadFileEntity loadFileId);

    // Find preload process
    List<PreloadRowTypeEntity> rowTypesServices(Long loadProcessId);

    /**
     * Preload definition list
     * @return list of preload definition list
     */
    List<PreloadDefinitionDTO> getPreloadDefinitionList();
}