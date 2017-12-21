package com.csi.itaca.common.endpoint;

import com.csi.itaca.common.exception.ApiGlobalRestExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.RestController;

/**
 * Itaca RESTful client base controller.
 * @author bboothe
 */
@RestController
public abstract class ItacaBaseRestController {

    private static final String EMPTY_STRING = "";

    /**
     * Create a new error tracking object
     * @return new error tracker.
     */
    public BindingResult createErrorTracker() {
        return new BeanPropertyBindingResult(EMPTY_STRING,EMPTY_STRING);
    }

    /**
     * Build a response entity with the <code>responseBodyContent</code>.
     * @param errTracking will be used to construct the response entity if errors were detected otherwise response
     *                    body will be empty.
     * @return new response entity containing <code>responseBodyContent</code> if no error was found in
     *         <code>errTracking</code>.
     */
    public ResponseEntity buildResponseEntity(BindingResult errTracking) {
        return buildResponseEntity(null,errTracking);
    }

    /**
     * Build a response entity with the <code>responseBodyContent</code>. Take in to account any errors that may
     * have occurred.
     * @param responseBodyContent will be used to construct the response entity if no error were detected.
     * @param errTracking will be used to construct the response entity if errors were detected.
     * @return new response entity containing <code>responseBodyContent</code> if no error was found in
     *         <code>errTracking</code>
     */
    public ResponseEntity buildResponseEntity(Object responseBodyContent, BindingResult errTracking) {
        if (!errTracking.hasErrors()) {
            if (responseBodyContent!=null) {
                return new ResponseEntity(responseBodyContent, HttpStatus.OK);
            }
            else {
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        else {
            return ApiGlobalRestExceptionHandler.buildApiErrorsView(errTracking);
        }
    }
}
