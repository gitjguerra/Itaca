package com.csi.itaca.users.exception;

import com.csi.itaca.common.exception.BusinessOperationException;

public class UserNotAuthorisedException extends BusinessOperationException {

	public static final String I18N_ERROR_KEY = "validation.userNotAuthorised.error";

	public UserNotAuthorisedException() {
		super(I18N_ERROR_KEY);
	}

	public UserNotAuthorisedException(Throwable cause) {
		super(I18N_ERROR_KEY, cause);
	}

}
