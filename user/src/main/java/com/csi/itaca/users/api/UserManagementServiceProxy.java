package com.csi.itaca.users.api;

import com.csi.itaca.users.exception.InvalidCredentialsException;
import com.csi.itaca.users.exception.UserNotAuthorisedException;
import com.csi.itaca.users.exception.UserNotFoundException;
import com.csi.itaca.users.model.dto.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * API for managing users within the application.
 * @author bboothe
 */
public interface UserManagementServiceProxy {

    String AUTH_USERNAME_PARAM      = "username";
    String AUTH_PASSWORD_PARAM      = "password";
    String USER_ID_PARAM            = "userId";
    String USER_NAME_PARAM          = "username";
    String USER_TO_SAVE_PARAM       = "user";

    // end point URLs
    String ENTITY                   = "/user";
    String AUTH                     = ENTITY + "/auth";
    String LIST                     = ENTITY + "/list";
    String GET_PROFILE              = ENTITY + "/getProfile";
    String SAVE_USER                = ENTITY + "/save";
    String DELETE_USER              = ENTITY + "/delete";



  /*  String GET_IDIOMAS              = ENTITY + "/getIdiomas";
    String UPDATE_CONFIG_PERSONAL   = ENTITY + "/updateConfigPersonal";
    String SAVE_CONFIG_USUARIO      = ENTITY + "/saveConfig";
    String GET_CONFIG_USUARIO       = ENTITY + "/getConfig";
    String COUNT                    = ENTITY + "/count";
    String INIT                     = ENTITY + "/init";*/


    /**
     * Authenticates the users against the application.
     * @param username the user's name.
     * @param password the users's password.
     * @return a user object if found.
     */
    ResponseEntity<UserDTO> auth(String username, String password)
            throws InvalidCredentialsException, UserNotAuthorisedException;

    /**
     * Get all users.
     * @return a list of users.
     */
    ResponseEntity<List<UserDTO>> getAllUsers();

    /**
     * Gets specific user.
     * @param username the user to retrieve
     * @return the user
     */
    ResponseEntity<UserDTO> getProfile(String username) throws UserNotFoundException;

    /**
     * Deletes a user.
     * @param username the username of the user to delete.
     * @return ok response if successful.
     */
    ResponseEntity getDelete(String username) throws UserNotFoundException;


    /**
     * Saves the user.
     * @param user the user object to save.
     * @return ok response if successful.
     */
    ResponseEntity<UserDTO> getSave(UserDTO user);


}
