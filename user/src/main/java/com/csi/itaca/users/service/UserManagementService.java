package com.csi.itaca.users.service;

import com.csi.itaca.tools.utils.jpa.Order;
import com.csi.itaca.tools.utils.jpa.Pagination;
import com.csi.itaca.users.model.UserConfig;
import com.csi.itaca.users.model.dao.UserEntity;
import com.csi.itaca.users.model.dto.*;
import com.csi.itaca.users.model.filters.UserSearchFilterDTO;
import org.springframework.validation.Errors;

import java.util.List;

/**
 * Interface for the management of users.
 * @author bboothe
 */
public interface UserManagementService {

    /**
     * Authenticates the user
     * @param username the username.
     * @param password new password.
     * @param errTracking error tracking.
     * @return the user object if the user was correctly authenticated.
     */
    UserDTO auth(String username, String password, Errors errTracking);

    /**
     * Gets user with the given username.
     * @param username the username of the user to retrieve.
     * @param errTracking error tracking.
     * @return the user if found otherwise null.
     */
    UserDTO getUser(String username, Errors errTracking);
    
    /**
     * Gets user with the given user Id.
     * @param id
     * @param errTracking
     * @return user found by id
     */
    UserDTO getUserById(Long id, Errors errTracking);

    /**
     * Save user. Creates or updates user.
     * @param userToSave users to save.
     * @param errTracking error tracking.
     */
    UserDTO createUpdateUser(UserDTO userToSave, Errors errTracking);

    /**
     * Deletes the user with the specified user username
     * @param username the username of user to delete.
     * @param errTracking error tracking.
     */
    void deleteUser(String username, Errors errTracking);
    
    /**
     * Deletes the user with the specified user id
     * @param id
     * @param errTracking 
     */
    void deleteUserById(Long id, Errors errTracking);

    /**
     * Updates the users password.
     * @param passwordChange credentials and new password.
     * @param errTracking error tracking.
     * @return true if password change was successful.
     */
    Boolean changePassword(ChangePasswordDTO passwordChange, Errors errTracking);

    /**
     * Updates the user preferences.
     * @param preferences preferences to update.
     * @param errTracking error tracking.
     * @return true if update was successful.
     */
    Boolean updatePersonalPreferences(PersonalPreferencesDTO preferences, Errors errTracking);

    /**
     * Saves the users configuration.
     * @param userConfigToSave the new configuration.
     * @param errTracking error tracking.
     */
    void saveUserConfig(UserConfig userConfigToSave, Errors errTracking);

    /**
     * Gets all the user configuration associated to a user.
     * @param username the user's username.
     * @return a list user configs.
     */
    List<UserConfigDTO> getUserConfig(String username, Errors errTracking) ;

    /**
     * Gets all the user configuration associated to a user.
     * @param username the user's username.
     * @param pagination pagination config.
     * @return a list user configs.
     */
    List<UserConfigDTO> getUserConfig(String username, Pagination pagination, Errors errTracking);

    /**
     * Counts number of users based on the supplied filter.
     * @param userFilter the filer to be applied.
     * @return number of users found.
     */
    Long countUsers(UserSearchFilterDTO userFilter);

    /**
     * Gets a list of users.
     * @param userFilter filter
     * @param pagination pagination
     * @param order the order to apply
     * @return the specified page of users.
     */
    List<UserDTO> getUsers(UserSearchFilterDTO userFilter, Pagination pagination, Order order);

    /**
     * Gets a list of users.
     * @return the specified page of users.
     */
    List<UserDTO> getUsers();

    /**
     * Gets the user languages.
     * @return a list of user languages.
     */
    List<UserLanguageDTO> getUserLanguages();

    // **********************  Test ************************
    void delete(long id);

    List<UserEntity> findAll();

    UserEntity save(UserEntity user);
    // **********************  Test ************************
}
