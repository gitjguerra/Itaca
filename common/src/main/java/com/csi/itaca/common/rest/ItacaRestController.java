package com.csi.itaca.common.rest;

import com.csi.itaca.common.exception.BusinessOperationException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * Itaca base rest controller.
 */
@RestController
public abstract class ItacaRestController {

    /** logger */
    private static Logger logger = Logger.getLogger(ItacaRestController.class);

    /**
     * Handles application exceptions.
     * @param ex exception generated but the application.
     * @return A response entity containing the error message and I18n error key. The stack trace will be included
     * if the application is in debug mode.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {

        ErrorResponse error = new ErrorResponse();

        // Set the error key...
        if (ex instanceof BusinessOperationException) {
            BusinessOperationException boe = (BusinessOperationException) ex;
            String errorKey = boe.getI18nErrorKey();
            if (errorKey==null || "null".equals(errorKey.trim().toLowerCase())) {
                error.setErrorKey(BusinessOperationException.DEFAULT_ERROR_KEY);
            }
            else {
                error.setErrorKey(errorKey);
            }
        }

        // error message
        error.setMessage(ex.getMessage());

        // include stack trace if debug is enabled.
        if (logger.isDebugEnabled()) {
            error.setException(ex);
        }
        return new ResponseEntity<>(error, HttpStatus.OK);
    }
}
