package com.csi.itaca.common.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the JSON that will be sent back to the calling process in the event of an error.
 */
@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

    private String errorKey;

    private String message;

    private Exception exception;
}