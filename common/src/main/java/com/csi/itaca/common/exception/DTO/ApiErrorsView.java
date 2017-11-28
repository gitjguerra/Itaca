package com.csi.itaca.common.exception.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ApiErrorsView {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ApiFieldError> fieldErrors;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ApiGlobalError> globalErrors;

    public ApiErrorsView(List<ApiFieldError> fieldErrors, List<ApiGlobalError> globalErrors) {
        this.fieldErrors = fieldErrors;
        this.globalErrors = globalErrors;
    }
}