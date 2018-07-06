package com.csi.itaca.users.endPoint;

import com.csi.itaca.common.exception.ApiGlobalRestExceptionHandler;
import com.csi.itaca.common.endpoint.ItacaBaseRestController;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import com.csi.itaca.users.model.dto.MessageDTO;


/**
 * RESTful interface for the user management service.
 */
@RestController
//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@CrossOrigin(origins = "*")
@RequestMapping(value = "/user")
public class UserManagementRestController extends ItacaBaseRestController implements UserManagementServiceProxy {

    /** Logger */
    private static Logger logger = Logger.getLogger(UserManagementRestController.class);

    /** The user service for this rest controller. */
    @Autowired
    private UserManagementService userManagementService;

    /** The change password validator. */
    @Autowired
    private ChangePasswordValidator changePasswordValidator;

    @Resource(name = "tokenServices")
    ConsumerTokenServices tokenServices;

    /**
     * getAllUsers will provide all users in chucks of (not implemented yet)
     * URL /user
     * @param criteria
     * @return list of users
     */
    @RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getAllUsers(@RequestBody(required = false) UserFilterPaginationOrderDTO criteria) {
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
    
    /**
     * getUser will provide one user by id
     * URL /user/id
     * METHOD GET
     * @param id
     * @return user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getUser(@PathVariable("id") Long id) {
        BindingResult errTracking = createErrorTracker();
        UserDTO user = userManagementService.getUserById(id, errTracking);
        return buildResponseEntity(user, errTracking);
    }
    
    /**
     * createUpdateUser persists a single user
     * URL /user 
     * METHOD POST
     * @param user
     * @param errTracking
     * @return ???
     */
    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE },
                                                              produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity createUser(@RequestBody UserDTO user, BindingResult errTracking) {
        UserDTO updatedUser = userManagementService.createUpdateUser(user, errTracking);
        return buildResponseEntity(updatedUser, errTracking);
    }
    
    /**
     * userUpdate updates a single user identified by id
     * URL /user/id
     * METHOD PUT
     * @param id
     * @param user
     * @return ???
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE },
                                                                 produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity updateUser(@PathVariable("id") Long id, @RequestBody UserDTO user, BindingResult errTracking) {
        UserDTO updatedUser = userManagementService.createUpdateUser(user, errTracking);
        return buildResponseEntity(updatedUser, errTracking);
    }
    
    /**
     * userDelete deletes a single user identified by id
     * URL /user/id
     * METHOD DELETE
     * @param id
     * @return ???
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        BindingResult errTracking = createErrorTracker();
        userManagementService.deleteUserById(id, errTracking);
        return buildResponseEntity(errTracking);
    }
//end of basic CRUDs

    @Override
    @RequestMapping(value ="/auth", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity auth(@RequestParam(UserManagementServiceProxy.AUTH_USERNAME_PARAM) String username,
                               @RequestParam(UserManagementServiceProxy.AUTH_PASSWORD_PARAM) String password) {
        BindingResult errTracking = createErrorTracker();
        UserDTO user = userManagementService.auth(username, password, errTracking);
        return buildResponseEntity(user, errTracking);
    }

    @Override
    @RequestMapping(value = "/changePassword", method = RequestMethod.PUT,
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
    
    @RequestMapping(value = "/getLanguages", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getLanguages() {
        List<UserLanguageDTO> languages = userManagementService.getUserLanguages();
        return new ResponseEntity<>(languages, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = UPDATE_PREFERENCES, method = RequestMethod.PUT,
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

    @RequestMapping(value = SAVE_USER_CONFIG, method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE },
                                                                          produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity saveUserConfig(@Valid @RequestBody UserConfigDTO userConfig, BindingResult errTracking) {
        userManagementService.saveUserConfig(userConfig, errTracking);
        return buildResponseEntity(errTracking);
    }

    @RequestMapping(value = "/getConfig", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getUserConfig(@RequestParam("username") String username) {
        BindingResult errTracking = createErrorTracker();
        List<UserConfigDTO> userConfigurations = userManagementService.getUserConfig(username, errTracking);
        return buildResponseEntity(userConfigurations, errTracking);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/count", method = RequestMethod.GET,
                    consumes = {MediaType.APPLICATION_JSON_VALUE },
                    produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity countUsers(@RequestBody(required=false) UserSearchFilterDTO userFilter) {
        CountDTO counts = new CountDTO();
        counts.setUserCount(userManagementService.countUsers(userFilter));
        return new ResponseEntity(counts, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/oauth/token/revokeById/{tokenId}")
    @ResponseBody
    public void revokeToken(HttpServletRequest request, @PathVariable String tokenId) {
        tokenServices.revokeToken(tokenId);
    }
}