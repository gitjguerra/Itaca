package com.csi.itaca.users.endPoint.rest;

import com.csi.itaca.common.exception.ApiGlobalRestExceptionHandler;
import com.csi.itaca.users.api.UserManagementServiceProxy;
import com.csi.itaca.users.businessLogic.validators.ChangePasswordValidator;
import com.csi.itaca.users.exception.InvalidCredentialsException;
import com.csi.itaca.users.exception.UserNotAuthorisedException;
import com.csi.itaca.users.exception.UserNotFoundException;
import com.csi.itaca.users.model.dto.ChangePasswordDTO;
import com.csi.itaca.users.model.dto.PersonalPreferencesDTO;
import com.csi.itaca.users.model.dto.UserConfigDTO;
import com.csi.itaca.users.model.dto.UserDTO;
import com.csi.itaca.users.model.filters.UserFilterPaginationOrderDTO;
import com.csi.itaca.users.model.filters.UserSearchFilterDTO;
import com.csi.itaca.users.service.UserManagementService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * RESTful interface for the user management service.
 */
@RestController
public class UserManagementRestController implements UserManagementServiceProxy {

    /** Logger */
    private static Logger logger = Logger.getLogger(UserManagementRestController.class);

    /** The user service for this rest controller. */
    @Autowired
    private UserManagementService userManagementService;

    /** The password validator routines. */
    @Autowired
    private ChangePasswordValidator passwordChangeValidator;

    /**
     * Indicate to the spring framework which validators we'll be using.
     */
    @InitBinder
    public void setupBinder(WebDataBinder binder) {
        binder.addValidators(passwordChangeValidator);
    }

    @Override
    @RequestMapping(value = AUTH, method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserDTO> auth(@RequestParam(UserManagementServiceProxy.AUTH_USERNAME_PARAM) String username,
                                        @RequestParam(UserManagementServiceProxy.AUTH_PASSWORD_PARAM) String password)
            throws InvalidCredentialsException, UserNotAuthorisedException  {

        UserDTO user = userManagementService.auth(username, password);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = LIST, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<UserDTO>> getUsers(@RequestBody UserFilterPaginationOrderDTO criteria) {
        List<UserDTO> users = userManagementService.getUsers(criteria.getFilter(),
                                                             criteria.getPagination(),
                                                             criteria.getOrder());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = GET_USER, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserDTO> getProfile(@RequestParam(UserManagementServiceProxy.USER_NAME_PARAM) String username)
            throws UserNotFoundException {
        UserDTO user = userManagementService.getUser(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = DELETE_USER, method = RequestMethod.POST,
                    consumes = { MediaType.APPLICATION_JSON_VALUE },
                    produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getDelete(@RequestParam(UserManagementServiceProxy.USER_ID_PARAM) String username)
            throws UserNotFoundException {
        userManagementService.deleteUser(username);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = SAVE_USER, method = RequestMethod.POST,
                    consumes = { MediaType.APPLICATION_JSON_VALUE },
                    produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getSave(@RequestBody UserDTO user) {
        userManagementService.saveUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = CHANGE_PASSWORD, method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity changePassword(@Valid @RequestBody ChangePasswordDTO updatePassword, BindingResult result) {

        // Are there any validation errors?
        if (!result.hasErrors()) {
            // Attempt password update
            boolean success = userManagementService.updatePassword(updatePassword, result);
            if (success) {
                return new ResponseEntity(HttpStatus.OK);
            }
        }

        return ApiGlobalRestExceptionHandler.buildApiErrorsView(result);
    }

    @Override
    @RequestMapping(value = UPDATE_PREFERENCES, method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity updateUserPreferences(@Valid @RequestBody PersonalPreferencesDTO preferences, BindingResult result) {

        // Are there any validation errors?
        if (!result.hasErrors()) {
            // Attempt preferences update
            boolean success = userManagementService.updatePersonalPreferences(preferences, result);
            if (success) {
                return new ResponseEntity(HttpStatus.OK);
            }
        }

        return ApiGlobalRestExceptionHandler.buildApiErrorsView(result);
    }

    @RequestMapping(value = SAVE_USER_CONFIG, method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<UserDTO>> saveUserConfig(@RequestBody UserConfigDTO userConfig) {
        userManagementService.saveUserConfig(userConfig);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = GET_USER_CONFIG, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<UserConfigDTO>> getUserConfig(@RequestParam(UserManagementServiceProxy.USER_ID_PARAM) Long userId) {
        List<UserConfigDTO> userConfigurations =  userManagementService.getUserConfig(userId);
        return new ResponseEntity<>(userConfigurations, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = COUNT, method = RequestMethod.POST,
                    consumes = {MediaType.APPLICATION_JSON_VALUE },
                    produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Long> countUsers(@RequestBody UserSearchFilterDTO userFilter) {
        Long count = userManagementService.countUsers(userFilter);
        return new ResponseEntity<>(count,HttpStatus.OK);
    }
}
