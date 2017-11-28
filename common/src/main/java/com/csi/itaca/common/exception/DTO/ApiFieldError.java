package com.csi.itaca.common.exception.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ApiFieldError {

    private String field;

    private String code;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Object rejectedValue;

    public ApiFieldError(String field, String code, Object rejectedValue) {
        this.field = field;
        this.code = code;
        this.rejectedValue = rejectedValue;
    }
}