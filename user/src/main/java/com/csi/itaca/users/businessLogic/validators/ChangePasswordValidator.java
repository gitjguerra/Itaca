package com.csi.itaca.users.businessLogic.validators;

import com.csi.itaca.users.api.ErrorConstants;
import com.csi.itaca.users.model.dto.ChangePasswordDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validate for password change functionality.
 * @author bboothe
 */
@Component
public class ChangePasswordValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {
		return ChangePasswordDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ChangePasswordDTO updatePasswordDTO = (ChangePasswordDTO) target;

		// user id is required.
		if (!isUsernameValid(updatePasswordDTO)) {
			errors.rejectValue(ChangePasswordDTO.USER_NAME, ErrorConstants.VALIDATION_USER_NAME_REQUIRED);
		}

		// current password required.
		if (!isCurrentPasswordValid(updatePasswordDTO)) {
			errors.rejectValue(ChangePasswordDTO.CURRENT_PASSWORD, ErrorConstants.VALIDATION_CURRENT_PASSWORD_REQUIRED);
		}

		// new password required.
		if (!isNewPasswordValid(updatePasswordDTO)) {
			errors.rejectValue(ChangePasswordDTO.NEW_PASSWORD, ErrorConstants.VALIDATION_NEW_PASSWORD_REQUIRED);
		}

        // confirm password required.
        if (!isConfirmPasswordValid(updatePasswordDTO)) {
            errors.rejectValue(ChangePasswordDTO.CONFIRM_PASSWORD_FIELD, ErrorConstants.VALIDATION_CONFIRM_PASSWORD_REQUIRED);
        }

        // Ensure the new password and confirmation passwords match.
        if(isNewPasswordValid(updatePasswordDTO) && !isNewConfirmPasswordMatch(updatePasswordDTO)) {
            errors.reject(ErrorConstants.VALIDATION_NEW_CONFIRM_PASSWORDS_MUST_MATCH);
        }

		// New and old password words must not match.
		if(isNewAndOldPasswordsMatch(updatePasswordDTO)) {
			errors.reject(ErrorConstants.VALIDATION_NEW_OLD_PASSWORDS_MUST_NOT_MATCH);
		}

	}

	public static boolean isNewAndOldPasswordsMatch(ChangePasswordDTO value) {
		return value.getCurrentPassword() != null && value.getCurrentPassword().equals(value.getNewPassword());
	}

	public static boolean isUsernameValid(ChangePasswordDTO value) {
		return value.getUsername() != null;
	}

	public static boolean isCurrentPasswordValid(ChangePasswordDTO value) {
		return value.getCurrentPassword() != null && !value.getCurrentPassword().isEmpty();
	}

	public static  boolean isNewPasswordValid(ChangePasswordDTO value) {
		return value.getNewPassword() != null && !value.getNewPassword().isEmpty();
	}

    public static  boolean isConfirmPasswordValid(ChangePasswordDTO value) {
        return value.getConfirmationPassword() != null && !value.getConfirmationPassword().isEmpty();
    }

    public static  boolean isNewConfirmPasswordMatch(ChangePasswordDTO value) {
		return value.getNewPassword().equals(value.getConfirmationPassword());
	}
}
