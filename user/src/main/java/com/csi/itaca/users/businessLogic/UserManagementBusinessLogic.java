package com.csi.itaca.users.businessLogic;


import com.csi.itaca.users.model.User;

public interface UserManagementBusinessLogic {

    /**
     * Checks if users is authorised to login.
     * @param user the user to check
     * @return true if user is authorised to login.
     */
    boolean isUserAuthorisedToLogOn (User user);
}
