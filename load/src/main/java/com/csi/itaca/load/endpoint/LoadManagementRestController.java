package com.csi.itaca.load.endpoint;

import com.csi.itaca.common.endpoint.ItacaBaseRestController;
import com.csi.itaca.load.api.LoadManagementServiceProxy;
import com.csi.itaca.load.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
@RestController
public class LoadManagementRestController extends ItacaBaseRestController implements LoadManagementServiceProxy {

    private final String fileUploadDirectory = "C:\\temp";
    private final File fileUpload = new File("C:\\temp\\prueba_load.csv");
    private final Path rootLocation = Paths.get(fileUploadDirectory);

    final static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(LoadManagementRestController.class);
    List<String> files = new ArrayList<String>();

    /*
    @Autowired
    JobLauncher jobLauncher;
    @Autowired
    Job job;
    */

    @Autowired
    private LoadManagementService loadManagementService;

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