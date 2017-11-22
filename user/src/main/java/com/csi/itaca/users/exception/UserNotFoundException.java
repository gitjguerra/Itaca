package com.csi.itaca.users.exception;

import com.csi.itaca.common.exception.BusinessOperationException;

public class UserNotFoundException extends BusinessOperationException {

	public static final String I18N_ERROR_KEY = "validation.userNotFound.error";

	public UserNotFoundException() {
		super(I18N_ERROR_KEY);
	}

	public UserNotFoundException(Throwable cause) {
		super(I18N_ERROR_KEY, cause);
	}

}
