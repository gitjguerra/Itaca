package com.csi.itaca.users.endPoint.rest;

import com.csi.itaca.common.rest.ItacaRestController;
import com.csi.itaca.users.api.UserManagementServiceProxy;
import com.csi.itaca.users.exception.InvalidCredentialsException;
import com.csi.itaca.users.exception.UserNotAuthorisedException;
import com.csi.itaca.users.exception.UserNotFoundException;
import com.csi.itaca.users.model.dto.UserDTO;
import com.csi.itaca.users.service.UserManagementService;
import com.csi.itaca.common.utils.beaner.Beaner;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RESTful interface for the user management service.
 */
@RestController
public class UserManagementRestController extends ItacaRestController implements UserManagementServiceProxy {

    private static Logger logger = Logger.getLogger(UserManagementRestController.class);

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private Beaner beaner;

    @Override
    @RequestMapping(value = AUTH, method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserDTO> auth(@RequestParam(UserManagementServiceProxy.AUTH_USERNAME_PARAM) String username,
                                        @RequestParam(UserManagementServiceProxy.AUTH_PASSWORD_PARAM) String password)
            throws InvalidCredentialsException, UserNotAuthorisedException  {

        UserDTO user = userManagementService.auth(username, password);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = LIST, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userManagementService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = GET_PROFILE, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
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
    public ResponseEntity getSave(@RequestParam(UserManagementServiceProxy.USER_TO_SAVE_PARAM) UserDTO user) {
        userManagementService.saveUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

}
