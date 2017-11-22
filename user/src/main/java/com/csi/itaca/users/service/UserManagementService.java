package com.csi.itaca.users.service;

import com.csi.itaca.users.exception.UserNotFoundException;
import com.csi.itaca.users.model.User;
import com.csi.itaca.users.exception.InvalidCredentialsException;
import com.csi.itaca.users.exception.UserNotAuthorisedException;
import com.csi.itaca.users.model.dto.UserDTO;

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
     * All returns a list with all users.
     * @return a list with all users. if there are no users the list will be empty.
     */
    List<UserDTO> getAllUsers();

    /**
     * All returns a list with all users.
     * @param pageSize the size of a single page.
     * @param pageNo the page number to return.
     * @return a page with the all users list.
     */
    List<UserDTO> getAllUsers(int pageSize, int pageNo);

    /**
     * Finds users where the <code>searchText</code> matches all or part of the first name, last name and/or email
     * address.
     * @param searchText text to match against all or part of the first name, last name and/or email address.
     * @return a list of users
     */
    List<UserDTO> findByNameEmail(String searchText);

    /**
     * Finds users where the <code>searchText</code> matches all or part of the first name, last name and/or email
     * address.
     * @param searchText text to match against all or part of the first name, last name and/or email address.
     * @param pageSize the size of a single page.
     * @param pageNo the page number to return.
     * @return a list of users
     */
    List<UserDTO> findByNameEmail(String searchText, int pageSize, int pageNo);


}
