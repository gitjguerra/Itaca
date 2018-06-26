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
    String LOAD                     = ENTITY_LOAD + "/launchjob";

    /**
     * Gets a load id process.
     * @return a response body containing the requested load json object.
     */
    ResponseEntity handle() throws Exception;

}
