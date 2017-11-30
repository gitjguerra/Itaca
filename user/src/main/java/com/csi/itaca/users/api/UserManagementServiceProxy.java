package com.csi.itaca.users.api;


import com.csi.itaca.users.model.dto.ChangePasswordDTO;
import com.csi.itaca.users.model.dto.PersonalPreferencesDTO;
import com.csi.itaca.users.model.dto.UserDTO;
import com.csi.itaca.users.model.filters.UserFilterPaginationOrderDTO;
import com.csi.itaca.users.model.filters.UserSearchFilterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

/**
 * API for managing users within the application.
 * @author bboothe
 */
public interface UserManagementServiceProxy {

    String AUTH_USERNAME_PARAM      = "username";
    String AUTH_PASSWORD_PARAM      = "password";
    String USER_NAME_PARAM          = "username";

    // end point URLs
    String ENTITY                   = "/user";
    String AUTH                     = ENTITY + "/auth";
    String LIST                     = ENTITY + "/list";
    String GET_USER                 = ENTITY + "/get";
    String SAVE_USER                = ENTITY + "/save";
    String DELETE_USER              = ENTITY + "/delete";
    String CHANGE_PASSWORD          = ENTITY + "/changePassword";
    String UPDATE_PREFERENCES       = ENTITY + "/updatePreferences";
    String GET_USER_CONFIG          = ENTITY + "/getConfig";
    String SAVE_USER_CONFIG         = ENTITY + "/saveConfig";
    String COUNT                    = ENTITY + "/count";


    /**
     * Authenticates the users against the application.
     * @param username the user's name.
     * @param password the users's password.
     * @return a user object if found.
     */
    ResponseEntity<UserDTO> auth(String username, String password);

    /**
     * Gets specific user.
     * @param username the user to retrieve.
     * @return the user
     */
    ResponseEntity<UserDTO> getUser(String username);

    /**
     * Deletes a user.
     * @param username the username of the user to delete.
     * @return ok response if successful.
     */
    ResponseEntity getDelete(String username);

    /**
     * Saves the user.
     * @param user the user object to save.
     * @param errTracking error tracking.
     * @return ok response if successful.
     */
    ResponseEntity<UserDTO> getSave(UserDTO user, BindingResult errTracking);

    /**
     * Changes the password for a given user
     * @param changePassword the password change details
     * @param errTracking error tracking
     * @return ok response if successful.
     */
    ResponseEntity changePassword(ChangePasswordDTO changePassword, BindingResult errTracking);

    /**
     * Updates the preferences for a given user
     * @param preferences the password change details
     * @param result error tracking
     * @return ok response if successful.
     */
    ResponseEntity updateUserPreferences(PersonalPreferencesDTO preferences, BindingResult result);

    /**
     * Returns a list of user based on the specified criteria.
     * @param criteria search criteria.
     * @return
     */
    ResponseEntity getUsers(UserFilterPaginationOrderDTO criteria);

    /**
     * Counts the number of users based on a given filer.
     * @param userFilter the user filter to be applied.
     * @return the number of user found based on the the search criteria.
     */
    ResponseEntity countUsers(UserSearchFilterDTO userFilter);

    /**
     * Get user configuration
     * @param username the user's configuration to retrieve.
     * @return the user's configuration
     */
    ResponseEntity getUserConfig(String username);

}
