package com.csi.itaca.users.service;

import com.csi.itaca.common.utils.beaner.BeanerImpl;
import com.csi.itaca.users.businessLogic.UserManagementBusinessLogic;
import com.csi.itaca.users.exception.InvalidCredentialsException;
import com.csi.itaca.users.exception.UserNotAuthorisedException;
import com.csi.itaca.users.exception.UserNotFoundException;
import com.csi.itaca.users.model.User;
import com.csi.itaca.users.model.dao.UserEntity;
import com.csi.itaca.users.model.dto.UserDTO;
import com.csi.itaca.users.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Test the user management service.
 * @author bboothe
 */
@RunWith(MockitoJUnitRunner.class)
public class UserManagementServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserManagementBusinessLogic userManBusiness;

    @Mock
    private BeanerImpl beaner;

    @InjectMocks
    private UserManagementService impl = new UserManagementServiceImpl();

    /** Admin test user. */
    private UserEntity adminUser;

    /** Blocked test user. */
    private UserEntity blockedUser;

    // Test credentials
    private static final String TEST_ADMIN_USERNAME = "admin";
    private static final String TEST_ADMIN_PASSWORD = "12345";
    private static final String TEST_ADMIN_CREDENTIAL_FAIL = "bad_password";
    private static final String BLOCKED_USERNAME = "blockedUser";
    private static final String BLOCKED_PASSWORD = "blockedUserPassword";

    private static final String TEST_FAIL_USER_USERNAME = "failuser";


    @Before
    public void init(){

        // Admin user setup.
        UserDTO adminUserDTO = new UserDTO();
        adminUserDTO.setUsername(TEST_ADMIN_USERNAME);
        adminUser = new UserEntity();
        adminUser.setUsername(TEST_ADMIN_USERNAME);
        adminUser.setBlocked(false);
        Mockito.when(userRepository.findByUsernameAndPassword(TEST_ADMIN_USERNAME, TEST_ADMIN_PASSWORD)).thenReturn(adminUser);
        Mockito.when(userRepository.findByUsernameAndPassword(TEST_ADMIN_USERNAME, TEST_ADMIN_CREDENTIAL_FAIL)).thenReturn(null);
        Mockito.when(userManBusiness.isUserAuthorisedToLogOn(adminUser)).thenReturn(!adminUser.isBlocked());
        Mockito.when(beaner.transform(adminUser, UserDTO.class)).thenReturn(adminUserDTO);

        // Blocked user setup.
        UserDTO blockedUserDTO = new UserDTO();
        blockedUserDTO.setUsername(BLOCKED_USERNAME);
        blockedUser = new UserEntity();
        blockedUser.setUsername(BLOCKED_USERNAME);
        blockedUser.setBlocked(true);
        Mockito.when(userRepository.findByUsernameAndPassword(BLOCKED_USERNAME, BLOCKED_PASSWORD)).thenReturn(blockedUser);
        Mockito.when(userManBusiness.isUserAuthorisedToLogOn(blockedUser)).thenReturn(!blockedUser.isBlocked());

        // Get all users.
        ArrayList<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(adminUser);
        userEntityList.add(blockedUser);
        Mockito.when(userRepository.findAll()).thenReturn(userEntityList);
        ArrayList<UserDTO> userDTOList = new ArrayList<>();
        userDTOList.add(adminUserDTO);
        userDTOList.add(blockedUserDTO);
        Mockito.when(beaner.transform(userEntityList, UserDTO.class)).thenReturn(userDTOList);

        //Get user
        Mockito.when(userRepository.findByUsername(TEST_ADMIN_USERNAME)).thenReturn(adminUser);
        Mockito.when(beaner.transform(adminUser, UserDTO.class)).thenReturn(adminUserDTO);
        Mockito.when(userRepository.findByUsername(TEST_FAIL_USER_USERNAME)).thenReturn(null);
    }

    /**
     * Test adminUser authentication.
     */
    @Test
    public void okAuthTest() throws InvalidCredentialsException, UserNotAuthorisedException {
        User usersResult = impl.auth(TEST_ADMIN_USERNAME, TEST_ADMIN_PASSWORD);
        Assert.assertEquals(adminUser.getUsername(), usersResult.getUsername());
    }

    /**
     * Failed login with bad credentials.
     */
    @Test(expected=InvalidCredentialsException.class)
    public void badCredentialsAuthTest() throws InvalidCredentialsException, UserNotAuthorisedException {
        impl.auth(TEST_ADMIN_USERNAME, TEST_ADMIN_CREDENTIAL_FAIL);
    }

    /**
     * Blocked user logon test.
     */
    @Test(expected=UserNotAuthorisedException.class)
    public void blockedUserLogonAuthTest() throws InvalidCredentialsException, UserNotAuthorisedException {
        impl.auth(BLOCKED_USERNAME, BLOCKED_PASSWORD);
    }

    /**
     * Get all users test.
     */
    @Test
    public void getAllUsersTest() {
        List<UserDTO> userList = impl.getUsers(null,null,null);
        Assert.assertNotNull(userList);
        Assert.assertEquals(2,userList.size());
        Assert.assertEquals(TEST_ADMIN_USERNAME,userList.get(0).getUsername());
        Assert.assertEquals(BLOCKED_USERNAME,userList.get(1).getUsername());
    }


    /**
     * Get user
     */
    @Test
    public void getUserTest() throws UserNotFoundException {
        UserDTO user = impl.getUser(TEST_ADMIN_USERNAME);
        Assert.assertNotNull(user);
        Assert.assertEquals(TEST_ADMIN_USERNAME,user.getUsername());
    }

    /**
     * Get user (not found)
     */
    @Test(expected=UserNotFoundException.class)
    public void getFailUserTest() throws UserNotFoundException {
        impl.getUser(TEST_FAIL_USER_USERNAME);
    }



}
