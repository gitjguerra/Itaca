package com.csi.itaca.users.service;

import com.csi.itaca.common.utils.beaner.BeanerImpl;
import com.csi.itaca.users.api.ErrorConstants;
import com.csi.itaca.users.businessLogic.UserManagementBusinessLogic;

import com.csi.itaca.users.model.User;
import com.csi.itaca.users.model.dao.UserEntity;
import com.csi.itaca.users.model.dto.UserDTO;
import com.csi.itaca.users.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


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



    // Test credentials
    private static final String TEST_ADMIN_USERNAME         = "admin";
    private static final String TEST_ADMIN_PASSWORD         = "12345";
    private static final String TEST_ADMIN_CREDENTIAL_FAIL  = "bad_password";
    private static final String BLOCKED_USERNAME            = "blockedUser";
    private static final String BLOCKED_PASSWORD            = "blockedUserPassword";

    private static final String TEST_FAIL_USER_USERNAME = "failuser";



    /* Admin test user. */
    private UserEntity adminUserEnity;
    private UserDTO adminUserDTO;

    /** Blocked test user. */
    private UserEntity blockedUserEntity;
    private UserDTO blockedUserDTO;

    @Before
    public void init(){

        // Admin user setup.
        adminUserDTO = new UserDTO();
        adminUserDTO.setUsername(TEST_ADMIN_USERNAME);
        adminUserEnity = new UserEntity();
        adminUserEnity.setUsername(TEST_ADMIN_USERNAME);
        adminUserEnity.setBlocked(false);


        // Blocked user setup.
        blockedUserDTO = new UserDTO();
        blockedUserDTO.setUsername(BLOCKED_USERNAME);
        blockedUserEntity = new UserEntity();
        blockedUserEntity.setUsername(BLOCKED_USERNAME);
        blockedUserEntity.setBlocked(true);
        when(userRepository.findByUsernameAndPassword(BLOCKED_USERNAME, BLOCKED_PASSWORD)).thenReturn(blockedUserEntity);
        when(userManBusiness.isUserAuthorisedToLogOn(blockedUserEntity)).thenReturn(!blockedUserEntity.isBlocked());

        /*
        // Get users.
        ArrayList<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(adminUser);
        userEntityList.add(blockedUser);
        when(userRepository.findAll(Matchers.any(Specification.class))).thenReturn(userEntityList);
        ArrayList<UserDTO> userDTOList = new ArrayList<>();
        userDTOList.add(adminUserDTO);
        userDTOList.add(blockedUserDTO);
        when(beaner.transform(userEntityList, UserDTO.class)).thenReturn(userDTOList);


        //Get user
        when(userRepository.findByUsername(TEST_ADMIN_USERNAME)).thenReturn(adminUser);
        when(beaner.transform(adminUser, UserDTO.class)).thenReturn(adminUserDTO);
        when(userRepository.findByUsername(TEST_FAIL_USER_USERNAME)).thenReturn(null);
        */
    }

    /**
     * Test adminUser authentication.
     */
    @Test
    public void okAuthTest() {
        // Mock all the calls the service will make.
        Mockito.when(userRepository.findByUsernameAndPassword(TEST_ADMIN_USERNAME, TEST_ADMIN_PASSWORD)).thenReturn(adminUserEnity);
        Mockito.when(userManBusiness.isUserAuthorisedToLogOn(adminUserEnity)).thenReturn(!adminUserEnity.isBlocked());
        Mockito.when(beaner.transform(adminUserEnity, UserDTO.class)).thenReturn(adminUserDTO);

        BindingResult errorTracking = mock(BindingResult.class);
        UserDTO usersResult = impl.auth(TEST_ADMIN_USERNAME, TEST_ADMIN_PASSWORD, errorTracking);
        verify(errorTracking, times(0)).reject(any(String.class));
        verify(errorTracking, times(0)).rejectValue(any(String.class),any(String.class));
        Assert.assertNotNull(usersResult);
        Assert.assertEquals(adminUserDTO.getUsername(), usersResult.getUsername());
    }

    /**
     * Failed login with bad credentials.
     */
    @Test
    public void badCredentialsAuthTest() {
        // Mock all the calls the service will make.
        when(userRepository.findByUsernameAndPassword(TEST_ADMIN_USERNAME, TEST_ADMIN_CREDENTIAL_FAIL)).thenReturn(null);

        BindingResult errorTracking = mock(BindingResult.class);
        impl.auth(TEST_ADMIN_USERNAME, TEST_ADMIN_CREDENTIAL_FAIL, errorTracking);
        verify(errorTracking, times(1)).reject(ErrorConstants.VALIDATION_INVALID_CREDENTIALS);
        verify(errorTracking, times(0)).rejectValue(any(String.class),any(String.class));
    }

    /**
     * Blocked user logon test.
     */
    @Test
    public void blockedUserLogonAuthTest() {
        // Mock all the calls the service will make.
        when(userRepository.findByUsernameAndPassword(BLOCKED_USERNAME, BLOCKED_PASSWORD)).thenReturn(blockedUserEntity);
        when(userManBusiness.isUserAuthorisedToLogOn(blockedUserEntity)).thenReturn(!blockedUserEntity.isBlocked());

        BindingResult errorTracking = mock(BindingResult.class);
        impl.auth(BLOCKED_USERNAME, BLOCKED_PASSWORD, errorTracking);

        verify(errorTracking, times(1)).reject(ErrorConstants.VALIDATION_USER_NOT_AUTHORISED);
        verify(errorTracking, times(0)).rejectValue(any(),any());
    }

    /**
     * Get all users test.
     */
    /*@Test
    public void getUsersTest() {
        List<UserDTO> userList = impl.getUsers(null,null,null);
        Assert.assertNotNull(userList);
        Assert.assertEquals(2,userList.size());
        Assert.assertEquals(TEST_ADMIN_USERNAME,userList.get(0).getUsername());
        Assert.assertEquals(BLOCKED_USERNAME,userList.get(1).getUsername());
    }*/


    /**
     * Get user
     */
   /* @Test
    public void getUserTest() {
        UserDTO user = impl.getUser(TEST_ADMIN_USERNAME);
        Assert.assertNotNull(user);
        Assert.assertEquals(TEST_ADMIN_USERNAME,user.getUsername());
    }*/

    /**
     * Get user (not found)
     */
    /*public void getFailUserTest() {
        impl.getUser(TEST_FAIL_USER_USERNAME);
    }*/



}
