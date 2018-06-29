package com.csi.itaca.load.api;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * Load core management service proxy interface.
 * @author jguerra
 */
public interface LoadManagementServiceProxy {

    // Parameters...
    String ID_PARAM                 = "id";

    String ENTITY_LOAD              = "/load";
    String LOAD_CREATE              = ENTITY_LOAD + "/Create";
    String LOAD_startOrContinueLoad = ENTITY_LOAD + "/startOrContinueLoad";
    String LOAD_cancelLoad          = ENTITY_LOAD + "/cancelLoad";
    String LOAD_getload             = ENTITY_LOAD + "/getload";
    String LOAD_getloads            = ENTITY_LOAD + "/getloads";

    /**
     * Gets a file upload.
     * @return a response body containing the requested load json object.
     */
    ResponseEntity<List<String>> Create(Model model);

    /**
     * Gets a file upload.
     * @return a response body containing the requested load json object.
     */
    ResponseEntity<List<String>> startOrContinueLoad(Model model);

    /**
     * Gets a file upload.
     * @return a response body containing the requested load json object.
     */
    ResponseEntity<List<String>> cancelLoad(Model model);

}
