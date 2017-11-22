package com.csi.itaca.users.endPoint.rest;

import com.csi.itaca.common.utils.beaner.BeanerImpl;
import com.csi.itaca.users.api.UserManagementServiceProxy;
import com.csi.itaca.users.exception.InvalidCredentialsException;
import com.csi.itaca.users.exception.UserNotAuthorisedException;
import com.csi.itaca.users.exception.UserNotFoundException;
import com.csi.itaca.users.model.dto.UserDTO;
import com.csi.itaca.users.service.UserManagementService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Test the user management rest controller.
 * @author bboothe
 */
@RunWith(MockitoJUnitRunner.class)
public class UserManagementRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BeanerImpl beaner = new BeanerImpl();

    @Mock
    private UserManagementService service;

    @InjectMocks
    private UserManagementServiceProxy controller = new UserManagementRestController();

    // Users details
    private static final long TEST_ADMIN_USER_ID            = 0;
    private static final String TEST_ADMIN_USERNAME         = "admin";
    private static final String TEST_ADMIN_PASSWORD         = "1234";
    private static final String TEST_ADMIN_CREDENTIAL_FAIL  = "bad_password";

    private static final long TEST_USER1_ID                 = 1;
    private static final String TEST_USER1_USERNAME         = "brianboothe";
    private static final String TEST_USER1_FIRST_NAME       = "Brian";
    private static final String TEST_USER1_LAST_NAME        = "Boothe";

    private static final long TEST_USER2_ID                 = 2;
    private static final String TEST_USER2_USERNAME         = "franacuna";
    private static final String TEST_USER2_FIRST_NAME       = "Fran";
    private static final String TEST_USER2_LAST_NAME        = "Acuna";

    private static final String TEST_FAIL_USER100_USERNAME  = "failuser";


    // Json fields
    private static final String JSON_USER_ID_FIELD      = "id";
    private static final String JSON_USERNAME_FIELD     = "username";
    private static final String JSON_PASSWORD_FIELD     = "password";
    private static final String JSON_FIRST_NAME_FIELD   = "firstName";
    private static final String JSON_LAST_NAME_FIELD    = "lastName";
    private static final String JSON_ERROR_KEY_FIELD    = "errorKey";


    @SuppressWarnings("unchecked")
    @Before
    public void init() throws InvalidCredentialsException, UserNotAuthorisedException, UserNotFoundException {

        // mock up the service
        UserDTO user = new UserDTO();
        user.setId(TEST_ADMIN_USER_ID);
        user.setUsername(TEST_ADMIN_USERNAME);
        user.setFirstName(TEST_ADMIN_USERNAME);

        Mockito.when(service.auth(TEST_ADMIN_USERNAME, TEST_ADMIN_PASSWORD)).thenReturn(user);
        Mockito.when(service.auth(TEST_ADMIN_USERNAME, TEST_ADMIN_CREDENTIAL_FAIL)).thenThrow(new InvalidCredentialsException());
        Mockito.when(beaner.transform(service.auth(TEST_ADMIN_USERNAME, TEST_ADMIN_PASSWORD), UserDTO.class)).thenReturn(user);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // all users list
        UserDTO user1 = new UserDTO();
        user1.setId(TEST_USER1_ID);
        user1.setUsername(TEST_USER1_USERNAME);
        user1.setFirstName(TEST_USER1_FIRST_NAME);
        user1.setLastName(TEST_USER1_LAST_NAME);

        UserDTO user2 = new UserDTO();
        user2.setId(TEST_USER2_ID);
        user2.setUsername(TEST_USER2_USERNAME);
        user2.setFirstName(TEST_USER2_FIRST_NAME);
        user2.setLastName(TEST_USER2_LAST_NAME);

        ArrayList<UserDTO> userDTOList = new ArrayList<>();
        userDTOList.add(user);
        userDTOList.add(user1);
        userDTOList.add(user2);

        Mockito.when(service.getAllUsers()).thenReturn(userDTOList);

        // get profile
        Mockito.when(service.getUser(TEST_ADMIN_USERNAME)).thenReturn(user);
        Mockito.when(service.getUser(TEST_USER1_USERNAME)).thenReturn(user1);
        Mockito.when(service.getUser(TEST_USER2_USERNAME)).thenReturn(user2);
        Mockito.when(service.getUser(TEST_FAIL_USER100_USERNAME)).thenThrow(new UserNotFoundException());
    }

    /**
     * Test for successful login.
     */
    @Test
    public void authOKTest() throws Exception {
        mockMvc.perform(post(UserManagementServiceProxy.AUTH)
                        .param(UserManagementServiceProxy.AUTH_USERNAME_PARAM, TEST_ADMIN_USERNAME)
                        .param(UserManagementServiceProxy.AUTH_PASSWORD_PARAM, TEST_ADMIN_PASSWORD))
                    .andDo(print())
                    .andExpect(jsonPath(JSON_USERNAME_FIELD,is(TEST_ADMIN_USERNAME)))
                    .andExpect(jsonPath(JSON_FIRST_NAME_FIELD,is(TEST_ADMIN_USERNAME)))
                    .andExpect(status().isOk());
    }

    /**
     * Users JSON should never return with the password field.
     */
    @Test
    public void passwordHiddenOKTest() throws Exception {
        mockMvc.perform(post(UserManagementServiceProxy.AUTH)
                        .param(UserManagementServiceProxy.AUTH_USERNAME_PARAM, TEST_ADMIN_USERNAME)
                        .param(UserManagementServiceProxy.AUTH_PASSWORD_PARAM, TEST_ADMIN_PASSWORD))
                    .andExpect(jsonPath(JSON_USERNAME_FIELD,is(TEST_ADMIN_USERNAME)))
                    .andExpect(jsonPath(JSON_PASSWORD_FIELD).doesNotExist())
                    .andExpect(status().isOk());
    }

    /**
     * Bad authentication test.
     */
    @Test
    public void authBadCredentialsTest() throws Exception {
        mockMvc.perform(post(UserManagementServiceProxy.AUTH)
                        .param(UserManagementServiceProxy.AUTH_USERNAME_PARAM, TEST_ADMIN_USERNAME)
                        .param(UserManagementServiceProxy.AUTH_PASSWORD_PARAM, TEST_ADMIN_CREDENTIAL_FAIL))
                .andExpect(jsonPath(JSON_ERROR_KEY_FIELD,is(InvalidCredentialsException.I18N_ERROR_KEY)))
                .andExpect(status().isOk());
    }

    /**
     * Get all users test.
     */
    @Test
    public void getAllUsersTest() throws Exception {
        mockMvc.perform(get(UserManagementServiceProxy.LIST))

                // verify admin user
                .andExpect(jsonPath("$[0]."+JSON_USERNAME_FIELD,is(TEST_ADMIN_USERNAME)))
                .andExpect(jsonPath("$[0]."+JSON_FIRST_NAME_FIELD,is(TEST_ADMIN_USERNAME)))

                // verify user 1
                .andExpect(jsonPath("$[1]."+JSON_USERNAME_FIELD,is(TEST_USER1_USERNAME)))
                .andExpect(jsonPath("$[1]."+JSON_FIRST_NAME_FIELD,is(TEST_USER1_FIRST_NAME)))
                .andExpect(jsonPath("$[1]."+JSON_LAST_NAME_FIELD,is(TEST_USER1_LAST_NAME)))

                // verify user 2
                .andExpect(jsonPath("$[2]."+JSON_USERNAME_FIELD,is(TEST_USER2_USERNAME)))
                .andExpect(jsonPath("$[2]."+JSON_FIRST_NAME_FIELD,is(TEST_USER2_FIRST_NAME)))
                .andExpect(jsonPath("$[2]."+JSON_LAST_NAME_FIELD,is(TEST_USER2_LAST_NAME)))

                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Get profile test.
     */
    @Test
    public void getProfileTest() throws Exception {

        // get User 0
        mockMvc.perform(get(UserManagementServiceProxy.GET_PROFILE)
                .param(UserManagementServiceProxy.USER_NAME_PARAM, TEST_ADMIN_USERNAME))
                .andDo(print())
                .andExpect(jsonPath(JSON_USER_ID_FIELD,is(0)))
                .andExpect(jsonPath(JSON_USERNAME_FIELD,is(TEST_ADMIN_USERNAME)))
                .andExpect(status().isOk());

        // get User 1
        mockMvc.perform(get(UserManagementServiceProxy.GET_PROFILE)
                .param(UserManagementServiceProxy.USER_NAME_PARAM, TEST_USER1_USERNAME))
                .andDo(print())
                .andExpect(jsonPath(JSON_USER_ID_FIELD,is(1)))
                .andExpect(jsonPath(JSON_USERNAME_FIELD,is(TEST_USER1_USERNAME)))
                .andExpect(jsonPath(JSON_FIRST_NAME_FIELD,is(TEST_USER1_FIRST_NAME)))
                .andExpect(jsonPath(JSON_LAST_NAME_FIELD,is(TEST_USER1_LAST_NAME)))
                .andExpect(status().isOk());
    }

    /**
     * Get profile fail test.
     */
    @Test
    public void getProfileFailTest() throws Exception {

        // get User 100
        mockMvc.perform(get(UserManagementServiceProxy.GET_PROFILE)
                .param(UserManagementServiceProxy.USER_NAME_PARAM, TEST_FAIL_USER100_USERNAME))
                .andDo(print())
                .andExpect(jsonPath(JSON_ERROR_KEY_FIELD,is(UserNotFoundException.I18N_ERROR_KEY)))
                .andExpect(status().isOk());

    }


}
