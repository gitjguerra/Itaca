package com.csi.itaca.users.exception;

import com.csi.itaca.common.exception.BusinessOperationException;

public class UsernameDuplicatedException extends BusinessOperationException {
	
	private static final long serialVersionUID = 7707188292121122241L;

	public static final String I18N_ERROR_KEY = "validation.userDuplicated.error";
	
	public UsernameDuplicatedException() {
		super(I18N_ERROR_KEY);
	}
	
	public UsernameDuplicatedException(Throwable cause) {
		super(I18N_ERROR_KEY, cause);
	}

}
