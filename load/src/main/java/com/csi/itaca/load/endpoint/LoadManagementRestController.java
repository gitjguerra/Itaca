package com.csi.itaca.load.endpoint;

import com.csi.itaca.common.endpoint.ItacaBaseRestController;
import com.csi.itaca.load.api.LoadManagementServiceProxy;
import com.csi.itaca.load.job.JobCompletionNotificationListener;
import com.csi.itaca.load.service.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

    @Value("${spring.batch.job.fileUploadDirectory}")
    private String fileUploadDirectory;

    @Autowired
    private JobCompletionNotificationListener listener;

    @Autowired
    private LoadManagementService loadManagementService;

    // Call Job
    @Override
    @RequestMapping(value=LOAD_FILE, method=RequestMethod.POST)
    public ResponseEntity preloadData(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        Path rootLocation = Paths.get(fileUploadDirectory);
        File fileToImport = new File(rootLocation + File.separator + multipartFile.getOriginalFilename());
        OutputStream outputStream = new FileOutputStream(fileToImport);
        IOUtils.copy(multipartFile.getInputStream(), outputStream);
        outputStream.flush();
        outputStream.close();

        // Execute Job
        HttpStatus status = loadManagementService.fileToDatabaseJob(listener, rootLocation, (File) fileToImport);

        return new ResponseEntity(status);
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

    @Override
    public ResponseEntity<List<String>> Create(Model model) {
        return null;
    }

    @Override
    public ResponseEntity<List<String>> startOrContinueLoad(Model model) {
        return null;
    }

    @Override
    public ResponseEntity<List<String>> cancelLoad(Model model) {
        return null;
    }

}
