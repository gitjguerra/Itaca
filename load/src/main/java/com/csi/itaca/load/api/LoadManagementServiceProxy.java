package com.csi.itaca.load.api;

import org.springframework.http.ResponseEntity;

/**
 * Load core management service proxy interface.
 * @author jguerra
 */
public interface LoadManagementServiceProxy {

    // Parameters...
    String ID_PARAM                 = "id";

    /**
     * Gets a load id process.
     * @return a response body containing the requested load json object.
     */
    void handle() throws Exception;

}
