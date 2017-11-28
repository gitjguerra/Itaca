package com.csi.itaca.users.businessLogic;

import com.csi.itaca.users.api.ErrorConstants;
import com.csi.itaca.users.model.dao.UserEntity;
import com.csi.itaca.users.model.dto.ChangePasswordDTO;
import com.csi.itaca.users.model.dto.PersonalPreferencesDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class UserManagementBusinessLogicImpl implements UserManagementBusinessLogic {

    @Override
    public boolean isUserAuthorisedToLogOn(UserEntity user) {
        return !user.isBlocked();
    }

    public boolean canChangeUserPassword(UserEntity user, ChangePasswordDTO passwordChange, Errors result) {

        // do we have a user?
        if(user==null) {
            result.reject(ErrorConstants.VALIDATION_USER_NOT_FOUND);
        }
        else {
            // Check if current password is correct
            if (!passwordChange.getCurrentPassword().equals(user.getPassword())) {
                result.reject(ErrorConstants.VALIDATION_CURRENT_PASSWORD_INCORRECT);
            }
        }

        return result.hasErrors();
    }


    public boolean canChangeUserPreferences(UserEntity user, PersonalPreferencesDTO preferences, Errors result) {

        // do we have a user?
        if(user==null) {
            result.reject(ErrorConstants.VALIDATION_USER_NOT_FOUND);
        }

        return result.hasErrors();
    }
}
