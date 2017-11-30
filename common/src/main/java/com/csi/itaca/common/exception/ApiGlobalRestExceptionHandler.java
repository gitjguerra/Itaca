package com.csi.itaca.common.exception;

import com.csi.itaca.common.exception.DTO.ApiErrorsView;
import com.csi.itaca.common.exception.DTO.ApiFieldError;
import com.csi.itaca.common.exception.DTO.ApiGlobalError;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class ApiGlobalRestExceptionHandler {

    /** Logger */
    private static Logger logger = Logger.getLogger(ApiGlobalRestExceptionHandler.class);

    /**
     * Handle all argument invalid exception.
     * @param ex MethodArgumentNotValidException.
     * @return an ApiErrorsView contain all field errors.
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiErrorsView> handleArgumentNotValid(MethodArgumentNotValidException ex) {
        return buildApiErrorsView(ex.getBindingResult());
    }

    /**
     * Builds Errors View from binding result.
     * @param bindingResult the bind result
     * @return Api Error DTO
     */
    public static ResponseEntity<ApiErrorsView> buildApiErrorsView(BindingResult bindingResult) {


        List<ApiFieldError> apiFieldErrors = bindingResult.getFieldErrors()
                                                          .stream()
                                                          .map(fieldError ->
                                                                  new ApiFieldError(fieldError.getField(),
                                                                                    fieldError.getCode(),
                                                                                    fieldError.getRejectedValue()))
                                                          .collect(toList());

        List<ApiGlobalError> apiGlobalErrors = bindingResult.getGlobalErrors()
                                                            .stream()
                                                            .map(globalError ->
                                                                   new ApiGlobalError(globalError.getCode()))
                                                            .collect(toList());

        return new ResponseEntity<>(new ApiErrorsView(apiFieldErrors, apiGlobalErrors), HttpStatus.BAD_REQUEST);
    }

    /**
     * General exception handler.
     * @param ex the exception.
     * @return an ApiGlobalError.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorsView> exceptionHandler(Exception ex) {

        logger.error(ex);

        ApiGlobalError apiGlobalError = new ApiGlobalError();

        // Set the error key...
        if (ex instanceof BusinessOperationException) {
            BusinessOperationException boe = (BusinessOperationException) ex;
            String errorKey = boe.getI18nErrorKey();
            if (errorKey==null || "null".equals(errorKey.trim().toLowerCase())) {
                apiGlobalError.setCode(BusinessOperationException.DEFAULT_ERROR_KEY);
            }
            else {
                apiGlobalError.setCode(errorKey);
            }
        }

        // error message
        apiGlobalError.setMessage(ex.getMessage());

        // include stack trace if debug is enabled.
        if (logger.isDebugEnabled()) {
            apiGlobalError.setException(ex);
        }

        List<ApiGlobalError> apiGlobalErrors = new ArrayList<>();
        apiGlobalErrors.add(apiGlobalError);

        ApiErrorsView apiErrorsView = new ApiErrorsView(null, apiGlobalErrors);

        return new ResponseEntity<>(apiErrorsView, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}