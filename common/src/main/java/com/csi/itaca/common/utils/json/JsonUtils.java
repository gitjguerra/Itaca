package com.csi.itaca.common.utils.json;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON Utilities.
 */
public class JsonUtils {

    /**
     * Converts object to JSON string
     * @param obj the object to convert
     * @return the JSON string
     */
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
