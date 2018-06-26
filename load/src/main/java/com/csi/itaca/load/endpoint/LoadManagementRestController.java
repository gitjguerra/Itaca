package com.csi.itaca.load.endpoint;

import com.csi.itaca.common.endpoint.ItacaBaseRestController;
import com.csi.itaca.load.api.LoadManagementServiceProxy;
import com.csi.itaca.load.service.JobCompletionNotificationListener;
import com.csi.itaca.load.service.LoadManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.csi.itaca.load.api.LoadManagementServiceProxy.ENTITY_LOAD;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

@SuppressWarnings("unchecked")
@RestController
public class LoadManagementRestController extends ItacaBaseRestController implements LoadManagementServiceProxy {

    /*
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    Job job;
    */

    @Autowired
    private LoadManagementService loadManagementService;

    @Autowired
    private JobCompletionNotificationListener jobCompletionNotificationListener;

    @Override
    @RequestMapping(value = LOAD, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity preloadData() throws Exception {
        HttpStatus success = null;
        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {

            success = loadManagementService.fileToDatabaseJob(jobCompletionNotificationListener);

            /*
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(fileToDatabaseJob, jobParameters);
            */
        } catch (Exception e) {
            logger.info(e.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}