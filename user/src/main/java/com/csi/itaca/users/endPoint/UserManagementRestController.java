package com.csi.itaca.users.endPoint;

import com.csi.itaca.common.exception.ApiGlobalRestExceptionHandler;
import com.csi.itaca.common.rest.ItacaBaseRestController;
import com.csi.itaca.users.api.UserManagementServiceProxy;
import com.csi.itaca.users.businessLogic.validators.ChangePasswordValidator;
import com.csi.itaca.users.model.dto.*;
import com.csi.itaca.users.model.filters.UserFilterPaginationOrderDTO;
import com.csi.itaca.users.model.filters.UserSearchFilterDTO;
import com.csi.itaca.users.service.UserManagementService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * RESTful interface for the user management service.
 */
@RestController
public class UserManagementRestController extends ItacaBaseRestController implements UserManagementServiceProxy {

    /** Logger */
    private static Logger logger = Logger.getLogger(UserManagementRestController.class);

    /** The user service for this rest controller. */
    @Autowired
    private UserManagementService userManagementService;

    /** The change password validator. */
    @Autowired
    private ChangePasswordValidator changePasswordValidator;

    @Override
    @RequestMapping(value = AUTH, method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity auth(@RequestParam(UserManagementServiceProxy.AUTH_USERNAME_PARAM) String username,
                               @RequestParam(UserManagementServiceProxy.AUTH_PASSWORD_PARAM) String password) {

        BindingResult errTracking = createErrorTracker();
        UserDTO user = userManagementService.auth(username, password, errTracking);
        return buildResponseEntity(user, errTracking);
    }

    @RequestMapping(value = GET_USER, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getUser(@RequestParam(UserManagementServiceProxy.USER_NAME_PARAM) String username) {

        BindingResult errTracking = createErrorTracker();
        UserDTO user = userManagementService.getUser(username, errTracking);
        return buildResponseEntity(user, errTracking);
    }

    @RequestMapping(value = SAVE_USER, method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getSave(@RequestBody UserDTO user, BindingResult errTracking) {
        userManagementService.saveUser(user, errTracking);
        return buildResponseEntity(errTracking);
    }

    @RequestMapping(value = DELETE_USER, method = RequestMethod.POST,
                    consumes = { MediaType.APPLICATION_JSON_VALUE },
                    produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getDelete(@RequestParam(UserManagementServiceProxy.USER_NAME_PARAM) String username) {

        BindingResult errTracking = createErrorTracker();
        userManagementService.deleteUser(username, errTracking);
        return buildResponseEntity(errTracking);
    }

    @RequestMapping(value = LIST, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getUsers(@RequestBody(required = false) UserFilterPaginationOrderDTO criteria) {

        List<UserDTO> users = null;
        if (criteria!=null) {
            users = userManagementService.getUsers(criteria.getFilter(),
                                                   criteria.getPagination(),
                                                   criteria.getOrder());
        }
        else {
            users = userManagementService.getUsers();
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = CHANGE_PASSWORD, method = RequestMethod.POST,
                    consumes = { MediaType.APPLICATION_JSON_VALUE },
                    produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity changePassword(@Valid @RequestBody ChangePasswordDTO changePassword,
                                         BindingResult errTracking) {

        ValidationUtils.invokeValidator(changePasswordValidator, changePassword, errTracking);

        // Are there any validation errors?
        if (!errTracking.hasErrors()) {
            // Attempt password update
            boolean success = userManagementService.changePassword(changePassword, errTracking);
            if (success) {
                return new ResponseEntity(HttpStatus.OK);
            }
        }

        return ApiGlobalRestExceptionHandler.buildApiErrorsView(errTracking);
    }

    @Override
    @RequestMapping(value = UPDATE_PREFERENCES, method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity updateUserPreferences(@Valid @RequestBody PersonalPreferencesDTO preferences,
                                                BindingResult errTracking) {

        // Are there any validation errors?
        if (!errTracking.hasErrors()) {
            // Attempt preferences update
            boolean success = userManagementService.updatePersonalPreferences(preferences, errTracking);
            if (success) {
                return new ResponseEntity(HttpStatus.OK);
            }
        }

        return ApiGlobalRestExceptionHandler.buildApiErrorsView(errTracking);
    }

    @RequestMapping(value = SAVE_USER_CONFIG, method = RequestMethod.POST,
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity saveUserConfig(@Valid @RequestBody UserConfigDTO userConfig, BindingResult errTracking) {
        userManagementService.saveUserConfig(userConfig, errTracking);
        return buildResponseEntity(errTracking);
    }

    @RequestMapping(value = GET_USER_CONFIG, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getUserConfig(@RequestParam(UserManagementServiceProxy.USER_NAME_PARAM) String username) {
        BindingResult errTracking = createErrorTracker();
        List<UserConfigDTO> userConfigurations = userManagementService.getUserConfig(username, errTracking);
        return buildResponseEntity(userConfigurations, errTracking);
    }

    @Override
    @RequestMapping(value = COUNT, method = RequestMethod.GET,
                    consumes = {MediaType.APPLICATION_JSON_VALUE },
                    produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity countUsers(@RequestBody(required=false) UserSearchFilterDTO userFilter) {
        CountDTO counts = new CountDTO();
        counts.setUserCount(userManagementService.countUsers(userFilter));
        return new ResponseEntity(counts, HttpStatus.OK);
    }

    @RequestMapping(value = GET_LANGUAGES, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getLanguages() {
        List<UserLanguageDTO> languages = userManagementService.getUserLanguages();
        return new ResponseEntity<>(languages, HttpStatus.OK);
    }

}
