package com.csi.itaca.users.exception;

import com.csi.itaca.common.exception.BusinessOperationException;


public class InvalidCredentialsException extends BusinessOperationException {

	private static final long serialVersionUID = -8770323004600628294L;


	public static final String I18N_ERROR_KEY = "validation.invalidCredentials.error";

	public InvalidCredentialsException() {
		super(I18N_ERROR_KEY);
	}

	public InvalidCredentialsException(Throwable cause) {
		super(I18N_ERROR_KEY, cause);
	}
}
