package com.csi.itaca.tools.utils.json;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * JSON Utilities.
 */
public class JsonUtils {

    /**
     * Converts object to JSON string.
     * @param obj the object to convert.
     * @return the JSON string.
     */
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();

            // Write times as a String instead of a Long so its human readable.
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.registerModule(new JavaTimeModule());

            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
