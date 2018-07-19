package com.csi.itaca.load.endpoint;

import com.csi.itaca.common.endpoint.ItacaBaseRestController;
import com.csi.itaca.load.api.LoadManagementServiceProxy;
import com.csi.itaca.load.model.dao.LoadFileEntity;
import com.csi.itaca.load.model.dto.LoadFileDTO;
import com.csi.itaca.load.model.dto.PreloadDataDTO;
import com.csi.itaca.load.model.dto.PreloadDefinitionDTO;
import com.csi.itaca.load.service.*;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@EnableBatchProcessing
@RestController
public class LoadManagementRestController extends ItacaBaseRestController implements LoadManagementServiceProxy {

    List<String> files = new ArrayList<>();

    @Autowired
    private LoadManagementService loadManagementService;

    // Upload directory - Set with resource in the applioation.yml  example:     fileUploadDirectory: "/temp"
    @Value("${spring.batch.job.fileUploadDirectory}")
    private String fileUploadDirectory;

    @Override
    @RequestMapping(value = LOAD_FILE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity upload(@RequestParam(LoadManagementServiceProxy.FILE_UPLOAD) MultipartFile multipartFile) {
        HttpStatus status = loadManagementService.upload(multipartFile);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOAD_CREATE, method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoadFileDTO> Create(@RequestParam(LoadManagementServiceProxy.FILE_UPLOAD) String filename,
                                              @RequestParam(LoadManagementServiceProxy.PRELOAD_DEF_ID) Long preloadDefinitionId) {

        BindingResult errTracking = createErrorTracker();
        LoadFileDTO loadFileDTO = new LoadFileDTO();

        try {
            Path rootLocation = Paths.get(fileUploadDirectory);
            File fileToImport = new File(rootLocation + File.separator + filename);

            // Create Job
            loadFileDTO = loadManagementService.create(rootLocation, fileToImport, preloadDefinitionId, errTracking);

        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        }
        return buildResponseEntity(loadFileDTO, errTracking);
    }

    @Override
    @RequestMapping(value = LOAD_START_CONTINUE_LOAD, method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity startOrContinueLoad(@RequestParam(LoadManagementServiceProxy.FILE_UPLOAD) String filename,
                                              @RequestParam(LoadManagementServiceProxy.PRELOAD_PROCESS_ID) String loadProcessId,
                                              @RequestParam(LoadManagementServiceProxy.PRELOAD_FILE_ID) String loadFileId) {

        BatchStatus status = loadManagementService.executeJob(filename, loadProcessId, loadFileId);

        return new ResponseEntity(status.getBatchStatus(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOAD_CANCEL_LOAD, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity cancelLoad(@RequestParam(LoadManagementServiceProxy.PRELOAD_JOB_NAME) String jobName) {
        BatchStatus status = loadManagementService.stopJob(jobName);
        return new ResponseEntity(status.getBatchStatus(), HttpStatus.OK);
    }

    /*
    @Override
    @RequestMapping(value = LOAD_LIST_FILE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<LoadFileDTO>> loadFile(@RequestParam(LoadManagementServiceProxy.PROCESS_ID) LoadProcessEntity loadProcessId, @RequestParam(LoadManagementServiceProxy.LOAD_FILE_ID) Long loadFileId) {

        List<LoadFileDTO> loadFileDTOS = null;

        loadFileDTOS = loadManagementService.getFile(loadProcessId, loadFileId);

        return new ResponseEntity<>(loadFileDTOS, HttpStatus.OK);

    }
*/


 /*
    @Override
    @RequestMapping(value = LOAD_PRELOAD_DATA, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<PreloadDataDTO>> getData(@RequestParam(LoadManagementServiceProxy.LOAD_FILE_ID) LoadFileEntity loadFileId, PreloadDataOrderPaginFilter filter) {

        List<PreloadDataDTO> preloadDataDTOS = null;
        if (filter != null) {
            preloadDataDTOS = loadManagementService.getPreloadData(loadFileId, filter.getPagination(),filter.getOrder());
        } else {
            preloadDataDTOS = loadManagementService.getPreloadData(loadFileId, filter.getPagination(),filter.getOrder());
        }
        return new ResponseEntity<>(preloadDataDTOS, HttpStatus.OK);
    }
*/

/*
    @Override
    @RequestMapping(value = LOAD_ALL_FILE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<LoadFileDTO>> loadFile(@RequestParam(LoadManagementServiceProxy.PROCESS_ID) Long loadProcessId, LoadFileOrderPaginFilter filter) {

        List<LoadFileDTO> loadFileDTOS = null;
        if (filter != null) {
            loadFileDTOS = loadManagementService.getFile(loadProcessId, filter.getPagination(),filter.getOrder());
        } else {
            loadFileDTOS = loadManagementService.getFile(loadProcessId, filter.getPagination(),filter.getOrder());
        }
        return new ResponseEntity<>(loadFileDTOS, HttpStatus.OK);
    }
    */

    //Este metodo es el primer paso
    @Override
    @RequestMapping(value = LOAD_PRELOAD_DATAFILE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<PreloadDataDTO>> getDataFile(@RequestParam(LoadManagementServiceProxy.PRELOAD_PROCESS_ID) Long loadProcessId,
                                                            @RequestParam(LoadManagementServiceProxy.PRELOAD_FILE_ID) LoadFileEntity loadFileId) {

        List<PreloadDataDTO> preloadDataDTOS = null;

        preloadDataDTOS = loadManagementService.preloadedRowList(loadProcessId, loadFileId);

        return new ResponseEntity<>(preloadDataDTOS, HttpStatus.OK);

    }

    /** Preload definition list. */
    @Override
    @RequestMapping(value = LOAD_GET_PRELOAD_TYPE_LIST, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PreloadDefinitionDTO>> getPreloadDefinitionList() {
        return new ResponseEntity(loadManagementService.getPreloadDefinitionList(), HttpStatus.OK);
    }

    // Get upload files
    @Override
    @RequestMapping(value = LOAD_GET_FILE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getListFiles(Model model) {
        List<String> fileNames = files
                .stream().map(fileName -> MvcUriComponentsBuilder
                        .fromMethodName(LoadManagementRestController.class, "getFile", fileName).build().toString())
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(fileNames);
    }

    // Get upload file
    @Override
    @RequestMapping(value = LOAD_GET_FILE_ID, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        File origin_file = new File(filename.toString());
        Resource file = loadManagementService.loadFile(origin_file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}







