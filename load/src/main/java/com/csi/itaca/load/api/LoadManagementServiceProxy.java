package com.csi.itaca.load.api;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

/**
 * Load core management service proxy interface.
 * @author jguerra
 */
public interface LoadManagementServiceProxy {

    // Parameters...
    String ID_PARAM = "id";

    String ENTITY_LOAD = "/load";
    String LOAD_CREATE = ENTITY_LOAD + "/Create";
    String LOAD_startOrContinueLoad = ENTITY_LOAD + "/startOrContinueLoad";
    String LOAD_cancelLoad = ENTITY_LOAD + "/cancelLoad";
    String LOAD_getload = ENTITY_LOAD + "/getload";
    String LOAD_getloads = ENTITY_LOAD + "/getloads";
    String LOAD_DATA_PRELOAD = ENTITY_LOAD + "/launchjob";
    String LOAD_FILE = ENTITY_LOAD + "/upload";
    String LOAD_GET_FILE = ENTITY_LOAD + "/getallfiles";
    String LOAD_GET_FILE_ID = ENTITY_LOAD + "/files/{filename:.+}";


    /**
     * Upload process.
     *
     * @return a response body containing the requested load json object.
     */
    ResponseEntity<String> preloadData(MultipartFile multipartFile) throws IOException, JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException;

    /**
     * Gets a file upload.
     *
     * @return a response body containing the requested load json object.
     */
    ResponseEntity<List<String>> getListFiles(Model model);

    /**
     * Gets a file upload by filename.
     *
     * @return a response body containing the requested load json object.
     */
    ResponseEntity<Resource> getFile(String filename);

    /**
     * Gets a file upload.
     *
     * @return a response body containing the requested load json object.
     */
    ResponseEntity<List<String>> Create(Model model);

    /**
     * Gets a file upload.
     *
     * @return a response body containing the requested load json object.
     */
    ResponseEntity<List<String>> startOrContinueLoad(Model model);

    /**
     * Gets a file upload.
     *
     * @return a response body containing the requested load json object.
     */
    ResponseEntity<List<String>> cancelLoad(Model model);

}
