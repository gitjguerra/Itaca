package com.csi.itaca.users.service;

import com.csi.itaca.tools.utils.beaner.BeanerImpl;
import com.csi.itaca.users.api.ErrorConstants;
import com.csi.itaca.users.businessLogic.UserManagementBusinessLogic;

import com.csi.itaca.users.model.dao.UserEntity;
import com.csi.itaca.users.model.dao.UserLanguageEntity;
import com.csi.itaca.users.model.dto.UserDTO;
import com.csi.itaca.users.model.dto.UserLanguageDTO;
import com.csi.itaca.users.repository.UserLanguageRepository;
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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

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
    private UserLanguageRepository userLanguageRepository;

    @Mock
    private UserManagementBusinessLogic userManBusiness;

    @Mock
    private BeanerImpl beaner;

    @InjectMocks
    private UserManagementService impl = new UserManagementServiceImpl();

    // Test credentials
    private static final String TEST_ADMIN_USERNAME = "admin";
    private static final String TEST_ADMIN_PASSWORD = "12345";
    private static final String TEST_ADMIN_CREDENTIAL_FAIL = "bad_password";
    private static final String BLOCKED_USERNAME = "blockedUser";
    private static final String BLOCKED_PASSWORD = "blockedUserPassword";
    private static final String TEST_FAIL_USER_USERNAME = "failuser";


    /* Admin test user. */
    private UserEntity adminUserEnity;
    private UserDTO adminUserDTO;

    /**
     * Blocked test user.
     */
    private UserEntity blockedUserEntity;
    private UserDTO blockedUserDTO;

    @Before
    public void init() {

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
        verify(errorTracking, times(0)).rejectValue(any(String.class), any(String.class));
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
        verify(errorTracking, times(0)).rejectValue(any(String.class), any(String.class));
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
        verify(errorTracking, times(0)).rejectValue(any(), any());
    }

    /**
     * Get all users test.
     */
    @Test
    public void getUsersTest() {
        // prepare mocks
        ArrayList<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(adminUserEnity);
        userEntityList.add(blockedUserEntity);
        when(userRepository.findAll(Matchers.any(Specification.class))).thenReturn(userEntityList);
        ArrayList<UserDTO> userDTOList = new ArrayList<>();
        userDTOList.add(adminUserDTO);
        userDTOList.add(blockedUserDTO);
        when(beaner.transform(userEntityList, UserDTO.class)).thenReturn(userDTOList);

        // do test
        List<UserDTO> userList = impl.getUsers(null, null, null);
        Assert.assertNotNull(userList);
        Assert.assertEquals(2, userList.size());
        Assert.assertEquals(TEST_ADMIN_USERNAME, userList.get(0).getUsername());
        Assert.assertEquals(BLOCKED_USERNAME, userList.get(1).getUsername());
    }

    /**
     * Get user
     */
    @Test
    public void getUserTest() {
        when(userRepository.findByUsername(TEST_ADMIN_USERNAME)).thenReturn(adminUserEnity);
        when(beaner.transform(adminUserEnity, UserDTO.class)).thenReturn(adminUserDTO);

        UserDTO user = impl.getUser(TEST_ADMIN_USERNAME, null);
        Assert.assertNotNull(user);
        Assert.assertEquals(TEST_ADMIN_USERNAME, user.getUsername());
    }

    /**
     * Get user (not found)
     */
    @Test
    public void getUserNotFoundTest() {
        Errors errorTracking = new BeanPropertyBindingResult("", "");
        UserDTO user = impl.getUser(TEST_FAIL_USER_USERNAME, errorTracking);
        Assert.assertNull(user);
        Assert.assertEquals(1, errorTracking.getErrorCount());
        Assert.assertEquals(ErrorConstants.VALIDATION_USER_NOT_FOUND, errorTracking.getAllErrors().get(0).getCode());
    }


    /**
     * Test for user count
     */
    @Test
    public void getCountUsersTest() {
        // prepare mocks
        when(userRepository.count(Matchers.any(Specification.class))).thenReturn(2L);

        // do test
        Long noOfUsers = impl.countUsers(null);
        Assert.assertEquals(2, noOfUsers.longValue());
    }


    /**
     * Delete user test
     */
    @Test
    public void getDeleteUserTest() {
        // Prepare mocks
        when(userRepository.findByUsername(TEST_ADMIN_USERNAME)).thenReturn(adminUserEnity);

        // do test
        Errors errorTracking = new BeanPropertyBindingResult("", "");
        impl.deleteUser(TEST_ADMIN_USERNAME, errorTracking);
        Assert.assertEquals(0, errorTracking.getErrorCount());
    }


    /**
     * Delete user test for not found user.
     */
    @Test
    public void getDeleteUserNotFoundTest() {
        // do test
        Errors errorTracking = new BeanPropertyBindingResult("", "");
        impl.deleteUser(TEST_ADMIN_USERNAME, errorTracking);
        Assert.assertEquals(1, errorTracking.getErrorCount());
        Assert.assertEquals(ErrorConstants.VALIDATION_USER_NOT_FOUND, errorTracking.getAllErrors().get(0).getCode());
    }


    /**
     * get languages test.
     */
    @Test
    public void getLanguagesTest() {

        // Mock up languages..
        UserLanguageEntity lang0Entity = new UserLanguageEntity();
        lang0Entity.setId(0L);
        lang0Entity.setCode("ES");
        lang0Entity.setValue("Spanish");

        UserLanguageEntity lang1Entity = new UserLanguageEntity();
        lang1Entity.setId(1L);
        lang1Entity.setCode("EN");
        lang1Entity.setValue("English");

        List<UserLanguageEntity> userLanguageEntityList = new ArrayList<>();
        userLanguageEntityList.add(lang0Entity);
        userLanguageEntityList.add(lang1Entity);
        when(userLanguageRepository.findAll()).thenReturn(userLanguageEntityList);

        UserLanguageDTO lang0DTO = new UserLanguageDTO();
        lang0DTO.setId(0L);
        lang0DTO.setCode("ES");
        lang0DTO.setValue("Spanish");

        UserLanguageDTO lang1DTO = new UserLanguageDTO();
        lang1DTO.setId(1L);
        lang1DTO.setCode("EN");
        lang1DTO.setValue("English");
        List<UserLanguageDTO> userLanguageDTOList = new ArrayList<>();
        userLanguageDTOList.add(lang0DTO);
        userLanguageDTOList.add(lang1DTO);
        when(beaner.transform(userLanguageEntityList, UserLanguageDTO.class)).thenReturn(userLanguageDTOList);

        // Do test
        List<UserLanguageDTO> userLanguageDTOResult = impl.getUserLanguages();
        Assert.assertNotNull(userLanguageDTOResult);
        Assert.assertEquals(2, userLanguageDTOResult.size());
        Assert.assertEquals("ES", userLanguageDTOResult.get(0).getCode());
        Assert.assertEquals("EN", userLanguageDTOResult.get(1).getCode());
    }
}