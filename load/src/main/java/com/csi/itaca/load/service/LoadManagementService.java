package com.csi.itaca.load.service;

import com.csi.itaca.load.model.dto.LoadProcessDTO;
import com.csi.itaca.load.model.dto.PreloadDefinitionDTO;
import org.springframework.batch.core.BatchStatus;
import com.csi.itaca.load.model.dao.LoadFileEntity;
import com.csi.itaca.load.model.dao.LoadProcessEntity;
import com.csi.itaca.load.model.dao.PreloadRowTypeEntity;
import com.csi.itaca.load.model.dto.LoadFileDTO;
import com.csi.itaca.load.model.dto.PreloadDataDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface LoadManagementService {

    // *********************** Metodos PRELOAD ***********************
    // Upload file
    HttpStatus upload(MultipartFile file);

    // Create Job
    LoadFileDTO create(MultipartFile file, Long preloadDefinitionId, Errors errTracking) throws IOException;

    // Execute Job
    BatchStatus executeJob(Long loadProcessId, Errors errTracking);

    // Cancel Job
    BatchStatus stopJob(Long idJob);

    // Get load Process
    LoadFileDTO getLoadProcess(Long loadProcessId, Errors errTracking);

    // Get load Process by user
    List<? extends LoadProcessDTO> getLoadProcessByUser(Long userId, Errors errTracking);
    // *********************** Metodos PRELOAD ***********************

    // *********************** Metodos LOAD ***********************
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
    // *********************** Metodos LOAD ***********************


    // ***************** For future actions ********************
    // TODO: Implementar metodo en rest controller para eliminar archivos subidos
    void deleteAll(Path rootLocation);
    // TODO: Implementar metodo en rest controller para crear directorio de carga de archivos
    void init(Path rootLocation);
    // Upload and download files
    void store(MultipartFile file, Path rootLocation);
    // Get upload file
    Resource loadFile(File file);
    // ***************** For future actions ********************

}