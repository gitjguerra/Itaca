package com.csi.itaca.users.businessLogic;

import com.csi.itaca.users.model.dao.UserEntity;
import com.csi.itaca.users.model.dto.ChangePasswordDTO;
import com.csi.itaca.users.model.dto.PersonalPreferencesDTO;
import org.springframework.validation.Errors;

public interface UserManagementBusinessLogic {

    /**
     * Checks if users is authorised to login.
     * @param user the user to check.
     * @return true if user is authorised to login.
     */
    boolean isUserAuthorisedToLogOn (UserEntity user);

    /**
     * Checks if a user can change the password.
     * @param user the  users entity object.
     * @param passwordChange password change fields.
     * @param result hold any validation errors that may have occurred.
     * @return true if user can change the password.
     */
    boolean canChangeUserPassword(UserEntity user, ChangePasswordDTO passwordChange, Errors result);


    /**
     * Checks if user can update preferences.
     * @param user the  users entity object.
     * @param preferences the preferences to update to.
     * @param result hold any validation errors that may have occurred.
     * @return true if user can change the password.
     */
    boolean canChangeUserPreferences(UserEntity user, PersonalPreferencesDTO preferences, Errors result);
}
