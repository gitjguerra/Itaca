package com.csi.itaca.users.service;

import com.csi.itaca.common.utils.jpa.Order;
import com.csi.itaca.common.utils.jpa.Pagination;
import com.csi.itaca.users.exception.UserNotFoundException;
import com.csi.itaca.users.model.UserConfig;
import com.csi.itaca.users.model.dto.ChangePasswordDTO;
import com.csi.itaca.users.model.User;
import com.csi.itaca.users.exception.InvalidCredentialsException;
import com.csi.itaca.users.exception.UserNotAuthorisedException;
import com.csi.itaca.users.model.dto.PersonalPreferencesDTO;
import com.csi.itaca.users.model.dto.UserConfigDTO;
import com.csi.itaca.users.model.dto.UserDTO;
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
     * @return the user object if the user was correctly authenticated.
     * @throws InvalidCredentialsException if the user crediencial are incorrect
     * @throws UserNotAuthorisedException if the user is blocked.
     */
    UserDTO auth(String username, String password) throws InvalidCredentialsException, UserNotAuthorisedException;

    /**
     * Save user. Creates or updates user.
     * @param user users to save.
     */
    void saveUser(User user);

    /**
     * Gets user with the given user Id.
     * @param username the username of the user to retrieve.
     * @return the user if found otherwise null.
     */
    UserDTO getUser(String username) throws UserNotFoundException;

    /**
     * Deletes the user with the specified user id
     * @param username the username of user to delete.
     */
    void deleteUser(String username) throws UserNotFoundException;

    /**
     * Updates the users password.
     * @param passwordChange credentials and new password.
     * @return true if password change was successful.
     */
    Boolean updatePassword(ChangePasswordDTO passwordChange, Errors result);

    /**
     * Updates the user preferences.
     * @param preferences preferences to update.
     * @param result holds any potential errors.
     * @return true if update was successful.
     */
    Boolean updatePersonalPreferences(PersonalPreferencesDTO preferences, Errors result);

    /**
     * Saves the users configuration.
     * @param userConfigToSave the new configuration.
     */
    void saveUserConfig(UserConfig userConfigToSave);

    /**
     * Gets all the user configuration associated to a user.
     * @param userId the user's id.
     * @return a list user configs.
     */
    List<UserConfigDTO> getUserConfig(Long userId);

    /**
     * Gets all the user configuration associated to a user.
     * @param userId the user's id.
     * @param pagination pagination config.
     * @return a list user configs.
     */
    List<UserConfigDTO> getUserConfig(Long userId, Pagination pagination);

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
    List<UserDTO> getUsers(UserSearchFilterDTO userFilter, Pagination pagination, Order order) ;

}
