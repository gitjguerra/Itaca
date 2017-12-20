package com.csi.itaca.users.endPoint;

import com.csi.itaca.tools.utils.beaner.BeanerImpl;
import com.csi.itaca.tools.utils.jpa.Order;
import com.csi.itaca.tools.utils.jpa.Pagination;
import com.csi.itaca.tools.utils.json.JsonUtils;
import com.csi.itaca.users.api.ErrorConstants;
import com.csi.itaca.users.api.UserManagementServiceProxy;
import com.csi.itaca.users.businessLogic.validators.ChangePasswordValidator;
import com.csi.itaca.users.model.dto.ChangePasswordDTO;
import com.csi.itaca.users.model.dto.CountDTO;
import com.csi.itaca.users.model.dto.UserDTO;
import com.csi.itaca.users.model.filters.UserFilterPaginationOrderDTO;
import com.csi.itaca.users.model.filters.UserSearchFilterDTO;
import com.csi.itaca.users.service.UserManagementService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Test the user management rest controller.
 * @author bboothe
 */
@RunWith(PowerMockRunner.class)
public class UserManagementRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BeanerImpl beaner = new BeanerImpl();

    @Mock
    private UserManagementService service;

    @Mock
    private ChangePasswordValidator changePasswordValidator = new ChangePasswordValidator();

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
    private static final String JSON_ERROR_KEY_FIELD    = "globalErrors[0].code";

    private UserDTO user;
    private UserDTO user1;
    private UserDTO user2;


    @SuppressWarnings("unchecked")
    @Before
    public void init() {

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // setup all users
        user = new UserDTO();
        user.setId(TEST_ADMIN_USER_ID);
        user.setUsername(TEST_ADMIN_USERNAME);
        user.setFirstName(TEST_ADMIN_USERNAME);

        user1 = new UserDTO();
        user1.setId(TEST_USER1_ID);
        user1.setUsername(TEST_USER1_USERNAME);
        user1.setFirstName(TEST_USER1_FIRST_NAME);
        user1.setLastName(TEST_USER1_LAST_NAME);

        user2 = new UserDTO();
        user2.setId(TEST_USER2_ID);
        user2.setUsername(TEST_USER2_USERNAME);
        user2.setFirstName(TEST_USER2_FIRST_NAME);
        user2.setLastName(TEST_USER2_LAST_NAME);



       //Mockito.when(service.auth(TEST_ADMIN_USERNAME, TEST_ADMIN_PASSWORD)).thenReturn(user);
       // Mockito.when(service.auth(TEST_ADMIN_USERNAME, TEST_ADMIN_CREDENTIAL_FAIL)).thenThrow(new InvalidCredentialsException());
       /// Mockito.when(beaner.transform(service.auth(TEST_ADMIN_USERNAME, TEST_ADMIN_PASSWORD), UserDTO.class)).thenReturn(user);




/*        // get profile
        Mockito.when(service.getUser(TEST_ADMIN_USERNAME)).thenReturn(user);
        Mockito.when(service.getUser(TEST_USER1_USERNAME)).thenReturn(user1);
        Mockito.when(service.getUser(TEST_USER2_USERNAME)).thenReturn(user2);
        Mockito.when(service.getUser(TEST_FAIL_USER100_USERNAME)).thenThrow(new UserNotFoundException());


        // Change password
        ChangePasswordDTO updatePasswordDTO = new ChangePasswordDTO();
        updatePasswordDTO.setUserId(TEST_ADMIN_USER_ID);
        updatePasswordDTO.setCurrentPassword(TEST_ADMIN_USERNAME);*/

    }

    /**
     * Test for successful login.
     */
     @Test
    public void authOKTest() throws Exception {
        Mockito.when(service.auth(Matchers.any(String.class), Matchers.any(String.class), Matchers.any(Errors.class) )).thenReturn(user);
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
        Mockito.when(service.auth(Matchers.any(String.class), Matchers.any(String.class), Matchers.any(Errors.class) )).thenReturn(user);
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
    @PrepareForTest(UserManagementRestController.class)
    public void authBadCredentialsTest() throws Exception {
        BeanPropertyBindingResult result = new BeanPropertyBindingResult("","");
        result.reject(ErrorConstants.VALIDATION_INVALID_CREDENTIALS);
        PowerMockito.whenNew(BeanPropertyBindingResult.class).withAnyArguments().thenReturn(result);

        mockMvc.perform(post(UserManagementServiceProxy.AUTH)
                        .param(UserManagementServiceProxy.AUTH_USERNAME_PARAM, TEST_ADMIN_USERNAME)
                        .param(UserManagementServiceProxy.AUTH_PASSWORD_PARAM, TEST_ADMIN_CREDENTIAL_FAIL))
                .andDo(print())
                .andExpect(jsonPath(JSON_ERROR_KEY_FIELD,is(ErrorConstants.VALIDATION_INVALID_CREDENTIALS)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Get all users test.
     */
    @Test
    public void getUsersTest() throws Exception {

        List<UserDTO> userDTOList = new ArrayList<>();
        userDTOList.add(user);
        userDTOList.add(user1);
        userDTOList.add(user2);
        Mockito.when(service.getUsers(Matchers.any(UserSearchFilterDTO.class),Matchers.any(Pagination.class),Matchers.any(Order.class))).thenReturn(userDTOList);


        UserFilterPaginationOrderDTO criteria = new UserFilterPaginationOrderDTO();

        mockMvc.perform(get(UserManagementServiceProxy.LIST)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.asJsonString(criteria)))
                .andDo(print())

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

                .andExpect(status().isOk());
    }

    /**
     * Get user test.
     */
     @Test
    public void getUserTest() throws Exception {

        // get User 0
        Mockito.when(service.getUser(Matchers.any(String.class), Matchers.any(Errors.class))).thenReturn(user);
        mockMvc.perform(get(UserManagementServiceProxy.GET_USER)
                .param(UserManagementServiceProxy.USER_NAME_PARAM, TEST_ADMIN_USERNAME))
                .andDo(print())
                .andExpect(jsonPath(JSON_USER_ID_FIELD,is(0)))
                .andExpect(jsonPath(JSON_USERNAME_FIELD,is(TEST_ADMIN_USERNAME)))
                .andExpect(status().isOk());

        // get User 1
        Mockito.when(service.getUser(Matchers.any(String.class), Matchers.any(Errors.class))).thenReturn(user1);
        mockMvc.perform(get(UserManagementServiceProxy.GET_USER)
                .param(UserManagementServiceProxy.USER_NAME_PARAM, TEST_USER1_USERNAME))
                .andDo(print())
                .andExpect(jsonPath(JSON_USER_ID_FIELD,is(1)))
                .andExpect(jsonPath(JSON_USERNAME_FIELD,is(TEST_USER1_USERNAME)))
                .andExpect(jsonPath(JSON_FIRST_NAME_FIELD,is(TEST_USER1_FIRST_NAME)))
                .andExpect(jsonPath(JSON_LAST_NAME_FIELD,is(TEST_USER1_LAST_NAME)))
                .andExpect(status().isOk());
    }

    /**
     * Test for user not found.
     */
    @Test
    @PrepareForTest(UserManagementRestController.class)
    public void getUserUserNotFoundTest() throws Exception {
         BeanPropertyBindingResult result = new BeanPropertyBindingResult("","");
         result.reject(ErrorConstants.VALIDATION_USER_NOT_FOUND);
         PowerMockito.whenNew(BeanPropertyBindingResult.class).withAnyArguments().thenReturn(result);
         Mockito.when(service.getUser(Matchers.any(String.class), Matchers.any(Errors.class))).thenReturn(null);

         // get User 100
         mockMvc.perform(get(UserManagementServiceProxy.GET_USER)
                .param(UserManagementServiceProxy.USER_NAME_PARAM, TEST_FAIL_USER100_USERNAME))
                .andDo(print())
                .andExpect(jsonPath(JSON_ERROR_KEY_FIELD,is(ErrorConstants.VALIDATION_USER_NOT_FOUND)))
                .andExpect(status().isBadRequest());

    }


    /**
     * Get update password.
     */
     @Test
    public void updatePassword() throws Exception {

        Mockito.when(service.changePassword(Matchers.any(ChangePasswordDTO.class),Matchers.any(Errors.class))).thenReturn(true);

        ChangePasswordDTO updatePasswordDTO = new ChangePasswordDTO();
        updatePasswordDTO.setUsername(TEST_ADMIN_USERNAME);
        updatePasswordDTO.setCurrentPassword(TEST_ADMIN_PASSWORD);
        updatePasswordDTO.setNewPassword("test");
        updatePasswordDTO.setConfirmationPassword("test");

        mockMvc.perform(post(UserManagementServiceProxy.CHANGE_PASSWORD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.asJsonString(updatePasswordDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print());
    }


    /**
     * Get all users test.
     */
    @Test
    public void countTest() throws Exception {

        Mockito.when(service.countUsers(Matchers.any(UserSearchFilterDTO.class))).thenReturn(88L);

        UserSearchFilterDTO criteria = new UserSearchFilterDTO();

        mockMvc.perform(get(UserManagementServiceProxy.COUNT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.asJsonString(criteria)))
                .andDo(print())
                .andExpect(jsonPath(CountDTO.USER_COUNT,is(88)))
                .andExpect(status().isOk());
    }


}
