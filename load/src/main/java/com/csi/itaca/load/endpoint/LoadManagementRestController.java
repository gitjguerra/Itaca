package com.csi.itaca.load.endpoint;

import com.csi.itaca.common.endpoint.ItacaBaseRestController;
import com.csi.itaca.load.api.LoadManagementServiceProxy;
import com.csi.itaca.load.service.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@EnableBatchProcessing
@RestController
public class LoadManagementRestController extends ItacaBaseRestController implements LoadManagementServiceProxy {

    private final String fileUploadDirectory = "C:\\temp";
    private final File fileUpload = new File(fileUploadDirectory + "/itaca_preload.csv");

    /*
    fileUploadDirectory in yml

    @Value("${batch.fileUploadDirectory}")
    private String fileUploadDirectory;
    */

    private final Path rootLocation = Paths.get(fileUploadDirectory);

    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(LoadManagementRestController.class);
    List<String> files = new ArrayList<String>();

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job sqlExecuteJob;

    @Autowired
    private LoadManagementService loadManagementService;

    @Override
    @RequestMapping(value=LOAD_FILE, method=RequestMethod.POST)
    public ResponseEntity preloadData1(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        //Save multipartFile file in a temporary physical folder
        //String path = new ClassPathResource("/").getURL().getPath();//it's assumed you have a folder called temp in the resources folder
        File fileToImport = new File(rootLocation + File.separator + multipartFile.getOriginalFilename());
        OutputStream outputStream = new FileOutputStream(fileToImport);
        IOUtils.copy(multipartFile.getInputStream(), outputStream);
        outputStream.flush();
        outputStream.close();

        //Launch the Batch Job
        try {
            //JobExecution jobExecution = jobLauncher.run(sqlExecuteJob, new JobParametersBuilder()
            //        .addString("fullPathFileName", fileToImport.getAbsolutePath())
            //        .toJobParameters());

            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(sqlExecuteJob, jobParameters);

        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOAD_GET_FILE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getListFiles(Model model) {
        List<String> fileNames = files
                .stream().map(fileName -> MvcUriComponentsBuilder
                        .fromMethodName(LoadManagementRestController.class, "getFile", fileName).build().toString())
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(fileNames);
    }

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
