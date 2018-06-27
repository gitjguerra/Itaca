package com.csi.itaca.load.api;

import org.springframework.http.ResponseEntity;

/**
 * Load core management service proxy interface.
 * @author jguerra
 */
public interface LoadManagementServiceProxy {

    // Parameters...
    String ID_PARAM                 = "id";

    String ENTITY_LOAD              = "/load";
    String LOAD_DATA_PRELOAD        = ENTITY_LOAD + "/launchjob";
    String LOAD_FILE                = ENTITY_LOAD + "/upload";
    String LOAD_GET_FILE            = ENTITY_LOAD + "/getallfiles";
    String LOAD_GET_FILE_ID         = ENTITY_LOAD + "/files/{filename:.+}";
    /**
     * Gets a load id process.
     * @return a response body containing the requested load json object.
     */
    ResponseEntity preloadData() throws Exception;

}
