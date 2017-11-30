package com.csi.itaca.common.rest;

import com.csi.itaca.common.exception.ApiGlobalRestExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class ItacaBaseRestController {

    /**
     * Build a response entity with the <code>responseBodyContent</code>.
     * @param errTracking will be used to construct the response entity if errors were detected otherwise response
     *                    body will be empty.
     * @return new response entity containing <code>responseBodyContent</code> if no error was found in
     *         <code>errTracking</code>.
     */
    public ResponseEntity buildResponseEnity(BindingResult errTracking) {
        return buildResponseEnity(null,errTracking);
    }

    /**
     * Build a response entity with the <code>responseBodyContent</code>. Take in to account any errors that may
     * have occurred.
     * @param responseBodyContent will be used to construct the response entity if no error were detected.
     * @param errTracking will be used to construct the response entity if errors were detected.
     * @return new response entity containing <code>responseBodyContent</code> if no error was found in
     *         <code>errTracking</code>
     */
    public ResponseEntity buildResponseEnity(Object responseBodyContent, BindingResult errTracking) {
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
