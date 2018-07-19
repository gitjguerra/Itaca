package com.csi.itaca.load.api;

import com.csi.itaca.load.model.dto.LoadFileDTO;
import com.csi.itaca.load.model.dto.PreloadDefinitionDTO;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import com.csi.itaca.load.model.dao.LoadFileEntity;
import com.csi.itaca.load.model.dto.PreloadDataDTO;
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
    String FILE_UPLOAD = "file";
    String EXECUTION_ID_PARAM = "executionId";
    String PRELOAD_DEF_ID = "preloadDefinitionId";
    String PRELOAD_PROCESS_ID = "loadProcessId";
    String PRELOAD_FILE_ID = "loadFileId";
    String PRELOAD_ROW_TYPE_ID = "preloadRowTypeId";
    String LOAD_ROW_OPERATION_ID = "LoadRowOperationId";

    String ENTITY_LOAD = "/load";
    String LOAD_FILE = ENTITY_LOAD + "/upload";
    String LOAD_CREATE = ENTITY_LOAD + "/create";
    String LOAD_START_CONTINUE_LOAD = ENTITY_LOAD + "/startOrContinueLoad";
    String LOAD_CANCEL_LOAD = ENTITY_LOAD + "/cancelLoad";
    String LOAD_DATA_PRELOAD = ENTITY_LOAD + "/launchjob";
    String LOAD_GET_FILE = ENTITY_LOAD + "/getallfiles";
    String LOAD_GET_FILE_ID = ENTITY_LOAD + "/files/{filename:.+}";

    // String LOAD_ALL_FILE = ENTITY_LOAD + "/allfiles";
    // String LOAD_LIST_FILE = ENTITY_LOAD + "/files";
    // String LOAD_PRELOAD_DATA = ENTITY_LOAD + "/data";
    String LOAD_getload = ENTITY_LOAD + "/getload";
    String LOAD_getloads = ENTITY_LOAD + "/getloads";
    String LOAD_PRELOAD_DATAFILE = ENTITY_LOAD + "/dataFile";
    String LOAD_ROW_OPERATION = ENTITY_LOAD + "/loadRow";
    String LOAD_GET_PRELOAD_TYPE_LIST = ENTITY_LOAD + "/loadTypeList";

    /**
     * Upload file.
     *
     * @return a response body containing the requested load json object.
     */
    ResponseEntity upload(MultipartFile multipartFile);

    /**
     * Create a Job.
     *
     * @return a response body containing the requested load json object.
     */
    ResponseEntity<LoadFileDTO> Create(String filename, Long preloadDefinitionId);

    /**
     * Gets a file upload.
     *
     * @return a response body containing the requested load json object.
     */
    ResponseEntity startOrContinueLoad(String file, String loadProcessId, String loadFileId);

    /**
     * Cancel Job.
     *
     * @return a response body containing the requested load json object.
     */
    ResponseEntity<List<String>> cancelLoad(Model model);
    ResponseEntity stopExecution(Long executionId)throws NoSuchJobExecutionException,JobExecutionNotRunningException;

    /**
     * ************************************************************************************** DELETE
     *
     * @return a response body containing the requested load json object.
     */
    ResponseEntity<String> preloadData(MultipartFile multipartFile) throws IOException, JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException;


    //********************** Load actions ****************************
    //ResponseEntity<List<LoadFileDTO>> loadFile(LoadProcessEntity loadProcessId, Long loadFileId);

    //ResponseEntity<List<LoadFileDTO>> loadFile(Long loadProcessId, LoadFileOrderPaginFilter filter);

    //ResponseEntity<List<PreloadDataDTO>> getData(LoadFileEntity loadFileId, PreloadDataOrderPaginFilter filter);

    //ResponseEntity<List<LoadRowOperationDTO>> getRowOperation(Long PreloadRowTypeId);

    ResponseEntity<List<PreloadDataDTO>> getDataFile(Long loadProcessId, LoadFileEntity loadFileId);

    ResponseEntity<List<PreloadDefinitionDTO>> getPreloadDefinitionList();


    //********************** Future actions ****************************
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

}
