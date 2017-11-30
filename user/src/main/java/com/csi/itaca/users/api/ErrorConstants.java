package com.csi.itaca.users.api;


public class ErrorConstants {

    // Authentication errors
    public static final String VALIDATION_INVALID_CREDENTIALS               = "validation.invalidCredentialsException";
    public static final String VALIDATION_USER_NOT_AUTHORISED               = "validation.userNotAuthorised";

    // Change password errors
    public static final String VALIDATION_USER_NOT_FOUND                    = "validation.userNotFound";
    public static final String VALIDATION_USER_NAME_REQUIRED                = "validation.username.required";
    public static final String VALIDATION_CURRENT_PASSWORD_REQUIRED         = "validation.currentPassword.required";
    public static final String VALIDATION_NEW_PASSWORD_REQUIRED             = "validation.newPassword.required";
    public static final String VALIDATION_CONFIRM_PASSWORD_REQUIRED         = "validation.confirmationPassword.required";
    public static final String VALIDATION_NEW_CONFIRM_PASSWORDS_MUST_MATCH  = "validation.newAndConfirmPasswordsMustMatch";
    public static final String VALIDATION_CURRENT_PASSWORD_INCORRECT        = "validation.currentPasswordIncorrect";
    public static final String VALIDATION_NEW_OLD_PASSWORDS_MUST_NOT_MATCH  = "validation.newAndOldPassowordsMustNotMatch";



}
