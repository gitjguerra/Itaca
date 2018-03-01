package com.csi.itaca.people.endpoint;

import com.csi.itaca.common.model.dto.CountryDTO;
import com.csi.itaca.people.api.PeopleManagementServiceProxy;
import com.csi.itaca.people.model.AccountClasification;
import com.csi.itaca.people.model.AddressFormat1;
import com.csi.itaca.people.model.dto.*;
import com.csi.itaca.people.model.filters.IndividualSearchFilter;
import com.csi.itaca.people.service.PeopleManagementService;
import com.csi.itaca.tools.utils.jpa.Pagination;
import com.csi.itaca.tools.utils.json.JsonUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Matchers.any;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;


/**
 * Test for the people management rest controller.
 * @author bboothe
 */
@SuppressWarnings("unchecked")
@RunWith(PowerMockRunner.class)
public class PeopleManagementRestControllerTest {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    // To be initialised in the setup method.
    private MockMvc mockMvc;

    @Mock
    private PeopleManagementService service;

    @InjectMocks
    private PeopleManagementServiceProxy controller = new PeopleManagementRestController();

    private IndividualDTO testIndividualDTO;
    private IndividualDetailDTO testIndividualDetailDTO;

    private BankCardDTO bankCardDTO;
    private AccountDTO accountDTO;
    private static final String CARD = "card";
    private static final String ACCOUNT = "id";
    private static final String ID_CARD = "idBankCard";


    private AddressFormat1DTO addressFormat1DTO;
    //private AccountDTO accountDTO;
    //private static final String CARD = "card";
    //private static final String ACCOUNT = "id";
    //private static final String ID_CARD = "idBankCard";

    private static final String EXTERNAL_REFERENCE_CODE_FIELD = "externalReferenceCode";
    private static final String ID_CODE_FIELD = "identificationCode";

    @Before
    public void setup() {

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

        testIndividualDTO = new IndividualDTO();
        testIndividualDTO.setId(1L);
        testIndividualDTO.setDateOfBirth(LocalDate.of(1987, 06, 12));
        testIndividualDTO.setExternalReferenceCode("Ref123");
        testIndividualDTO.setIdentificationCode("IDCode123");

        IDTypeDTO idTypeDTO = new IDTypeDTO();
        idTypeDTO.setId(2L);
        testIndividualDTO.setIdType(idTypeDTO);

        GenderDTO genderDTO = new GenderDTO();
        genderDTO.setId(1L);
        testIndividualDTO.setGender(genderDTO);

        testIndividualDetailDTO = new IndividualDetailDTO();
        testIndividualDetailDTO.setId(1L);
        testIndividualDetailDTO.setName1("Elon");
        testIndividualDetailDTO.setName1("");
        testIndividualDetailDTO.setSurname1("Musk");
        testIndividualDetailDTO.setSurname2("");

        CivilStatusDTO civilStatusDTO = new CivilStatusDTO();
        civilStatusDTO.setId(3L);
        testIndividualDetailDTO.setCivilStatus(civilStatusDTO);

        PersonStatusDTO personStatusDTO = new PersonStatusDTO();
        personStatusDTO.setId(1L);
        testIndividualDetailDTO.setPersonStatus(personStatusDTO);

        LanguageDTO languageDTO = new LanguageDTO();
        languageDTO.setId(5L);
        testIndividualDetailDTO.setLanguage(languageDTO);

        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setId(1L);
        testIndividualDetailDTO.setCountry(countryDTO);

        testIndividualDetailDTO.setPerson(testIndividualDTO);

        testIndividualDTO.setDetails(Collections.singletonList(testIndividualDetailDTO));
    }

    /**
     * Get person test.
     */
    @Test
    public void getPerson() throws Exception {
        Mockito.when(service.getPerson(any(), any(Errors.class))).thenReturn(testIndividualDTO);
        mockMvc.perform(get(PeopleManagementServiceProxy.GET_PERSON)
                .param(PeopleManagementServiceProxy.ID_PARAM, Long.toString(1L)))
                .andDo(print())
                .andExpect(jsonPath(EXTERNAL_REFERENCE_CODE_FIELD, is(testIndividualDTO.getExternalReferenceCode())))
                .andExpect(jsonPath(ID_CODE_FIELD, is(testIndividualDTO.getIdentificationCode())))
                .andExpect(status().isOk())
                .andDo(document(
                        "get-person",
                        requestParameters(parameterWithName(PeopleManagementServiceProxy.ID_PARAM).description("The ID of the person to retrieve.")),
                        responseFields(individualFieldsDoc("", true, true))
                ));
    }

    /**
     * Delete person test.
     */
    @Test
    public void getDeletePerson() throws Exception {

        Mockito.doNothing().when(service).deletePerson(any(), any(Errors.class));

        mockMvc.perform(delete(PeopleManagementServiceProxy.DELETE_PERSON)
                .param(PeopleManagementServiceProxy.ID_PARAM, Long.toString(1L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "delete-person",
                        requestParameters(parameterWithName(PeopleManagementServiceProxy.ID_PARAM).description("The id of the person to delete. The person can be an individual or company.")
                        )
                ));
    }

    /**
     * Search people test.
     */
    @Test
    public void searchPeople() throws Exception {
        List people = new ArrayList<>();
        people.add(testIndividualDTO);

        Mockito.when(service.findPeople(any(), any(Errors.class))).thenReturn(people);

        StringBuilder requestBody = new StringBuilder();
        requestBody.append("{");
        requestBody.append("\"@type\":").append("\"individual\"");
        requestBody.append(",\"personType\":").append("{\"id\":\"individual\"}");
        requestBody.append(",\"idCode\":").append("\"IDCODE123\"");
        requestBody.append(",\"idType\":").append("{\"id\":\"2\"}");
        requestBody.append("}");

        mockMvc.perform(post(PeopleManagementServiceProxy.SEARCH_PEOPLE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.toString()))
                .andDo(print())
                .andExpect(status().isOk())

                // verify individual
                .andExpect(jsonPath("[0].id", is(testIndividualDTO.getId().intValue())))
                .andExpect(jsonPath("[0].idType.id", is(testIndividualDTO.getIdType().getId().intValue())))

                .andDo(document(
                        "find-person",
                        requestFields(
                                fieldWithPath("@type").description("The type of person to search for. Must be \"individual\" or \"company\".")
                                , fieldWithPath("personType.id").description("The person type. Must be same as @type.")
                                , fieldWithPath("idCode").description("The ID code.")
                                , fieldWithPath("idType.id").description("The ID associated with the identification document type.")
                        ),
                        responseFields(individualFieldsDoc("[]", false, true))
                ));
    }

    @Test
    public void saveOrUpdatePerson() throws Exception {
        Mockito.when(service.saveOrUpdatePerson(any(), any(Errors.class))).thenReturn(testIndividualDTO);

        mockMvc.perform(put(PeopleManagementServiceProxy.SAVE_PERSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.asJsonString(testIndividualDTO)))
                .andDo(print())
                .andExpect(status().isOk())

                // verify individual
                .andExpect(jsonPath("id", is(testIndividualDTO.getId().intValue())))
                .andExpect(jsonPath("idType.id", is(testIndividualDTO.getIdType().getId().intValue())))
                .andExpect(jsonPath("identificationCode", is(testIndividualDTO.getIdentificationCode())))
                .andExpect(jsonPath("externalReferenceCode", is(testIndividualDTO.getExternalReferenceCode())))
                .andExpect(jsonPath("gender.id", is(testIndividualDTO.getGender().getId().intValue())))

                .andDo(document(
                        "save-update-person",
                        requestFields(individualFieldsDoc("", true, true)),
                        responseFields(individualFieldsDoc("", true, true))
                ));
    }


    @Test
    public void getCheckExtRefExists() throws Exception {
        Mockito.when(service.doseExternalReferenceAlreadyExist(any())).thenReturn(true);
        mockMvc.perform(get(PeopleManagementServiceProxy.EXT_REF_EXISTS)
                .param(PeopleManagementServiceProxy.EXT_REF_PARAM, "EXTREF123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andDo(document(
                        "check-ext-ref-exists",
                        requestParameters(parameterWithName(PeopleManagementServiceProxy.EXT_REF_PARAM).description("The external reference to check for."))
                ));
    }


    /**
     * Get person detail test.
     */
    @Test
    public void getPersonDetail() throws Exception {
        Mockito.when(service.getPersonDetail(any(), any(Errors.class))).thenReturn(testIndividualDetailDTO);
        mockMvc.perform(get(PeopleManagementServiceProxy.GET_PERSON_DETAIL)
                .param(PeopleManagementServiceProxy.PERSON_DETAIL_ID_PARAM, Long.toString(1L)))
                .andDo(print())
                .andExpect(jsonPath("id", is(testIndividualDetailDTO.getId().intValue())))
                .andExpect(jsonPath("language.id", is(testIndividualDetailDTO.getLanguage().getId().intValue())))
                .andExpect(jsonPath("name1", is(testIndividualDetailDTO.getName1())))
                .andExpect(jsonPath("name2", is(testIndividualDetailDTO.getName2())))
                .andExpect(jsonPath("surname1", is(testIndividualDetailDTO.getSurname1())))
                .andExpect(jsonPath("surname2", is(testIndividualDetailDTO.getSurname2())))
                .andExpect(status().isOk())
                .andDo(document(
                        "get-person-detail",
                        requestParameters(parameterWithName(PeopleManagementServiceProxy.PERSON_DETAIL_ID_PARAM).description("The ID of the person detail to retrieve.")),
                        responseFields(individualDetailsFieldsDoc("", true))
                ));
    }

    @Test
    public void findPersonDetail() throws Exception {
        List details = new ArrayList<>();
        details.add(testIndividualDetailDTO);
        Mockito.when(service.findPersonDetails(any(), any(Errors.class))).thenReturn(details);

        mockMvc.perform(post(PeopleManagementServiceProxy.SEARCH_PERSON_DETAIL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildPeopleSearchFilter()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "find-person-detail",
                        requestFields(peopleSearchFilterFieldsDoc()),
                        responseFields(individualDetailsFieldsDoc("[]", true))

                ));

    }

    /**
     * Count person detail test.
     */
    @Test
    public void countPersonDetail() throws Exception {

        Mockito.when(service.countPersonDetails(any())).thenReturn(1L);

        mockMvc.perform(post(PeopleManagementServiceProxy.COUNT_PERSON_DETAIL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildPeopleSearchFilter()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(document(
                        "count-person-detail",
                        requestFields(peopleSearchFilterFieldsDoc())
                ));
    }


    @Test
    public void findDuplicatePersonDetail() throws Exception {
        List details = new ArrayList<>();
        details.add(testIndividualDetailDTO);
        Mockito.when(service.findDuplicatePersonDetails(any(), any(Errors.class))).thenReturn(details);

        mockMvc.perform(post(PeopleManagementServiceProxy.SEARCH_DUPLICATE_PERSON_DETAIL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildPeopleSearchFilter()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "find-duplicate-person-detail",
                        requestFields(peopleSearchFilterFieldsDoc()),
                        responseFields(individualDetailsFieldsDoc("[]", true))

                ));

    }

    @Test
    public void countDuplicatePersonDetail() throws Exception {

        Mockito.when(service.countDuplicatePersonDetails(any())).thenReturn(1L);

        mockMvc.perform(post(PeopleManagementServiceProxy.COUNT_DUPLICATE_PERSON_DETAIL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildPeopleSearchFilter()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(document(
                        "count-duplicate-person-detail",
                        requestFields(peopleSearchFilterFieldsDoc())
                ));
    }

    private String buildPeopleSearchFilter() {

        IndividualSearchFilter filter = new IndividualSearchFilter();

        PersonTypeDTO personType = new PersonTypeDTO();
        personType.setId("individual");
        personType.setName(null);
        filter.setPersonType(personType);

        IDTypeDTO idTypeDTO = new IDTypeDTO();
        idTypeDTO.setId(1L);
        filter.setIdType(idTypeDTO);

        Pagination pagination = new Pagination();
        pagination.setItemsPerPage(10);
        pagination.setPageNo(0);
        filter.setPagination(pagination);

        filter.setIdCode("123Code");
        filter.setExternalReference("referenceCode1");
        filter.setName("");
        filter.setName1("Rafa");
        filter.setSurname1("Nadal");
        return JsonUtils.asJsonString(filter);
    }

    private List<FieldDescriptor> peopleSearchFilterFieldsDoc() {
        List<FieldDescriptor> fields = new ArrayList<>();
        fields.add(fieldWithPath("@type").description("The type of person to search for. Must be \"individual\" or \"company\"."));
        fields.add(fieldWithPath("personType.id").description("The person type. Must be same as @type."));
        fields.add(fieldWithPath("idCode").description("The ID code."));
        fields.add(fieldWithPath("idType.id").description("The ID associated with the identification document type."));
        fields.add(fieldWithPath("externalReference").description("The external reference code"));
        fields.add(fieldWithPath("name").description("Concatenated name."));

        fields.add(fieldWithPath("dateOfBirth").description("Date of birth"));
        fields.add(fieldWithPath("name1").description("First name."));
        fields.add(fieldWithPath("name2").description("Second name."));
        fields.add(fieldWithPath("surname1").description("First surname."));
        fields.add(fieldWithPath("surname2").description("Second surname."));

        fields.add(fieldWithPath("pagination").description("Pagination (Applies only to search)"));
        fields.add(fieldWithPath("pagination.itemsPerPage").description("Number of items per page."));
        fields.add(fieldWithPath("pagination.pageNo").description("page number to retrieve"));

        fields.add(fieldWithPath("order").description("Field ordering (Applies only to search)"));

        return fields;
    }


    private List<FieldDescriptor> individualFieldsDoc(String fieldPrefix, boolean includePersonType, boolean includeDetails) {
        List<FieldDescriptor> fields = new ArrayList<>();
        fields.add(fieldWithPath(fieldPrefix + "id").description("Person ID."));
        if (includePersonType)
            fields.add(fieldWithPath(fieldPrefix + "personType").description("Person type ('individual' or 'company')"));
        fields.add(fieldWithPath(fieldPrefix + "identificationCode").description("Identification code"));
        fields.add(fieldWithPath(fieldPrefix + "externalReferenceCode").description("External reference code."));
        fields.add(fieldWithPath(fieldPrefix + "dateOfBirth").description("The person's date of birth."));
        fields.add(fieldWithPath(fieldPrefix + "idType.id").description("Identification type ID."));
        fields.add(fieldWithPath(fieldPrefix + "gender.id").description("Gender ID."));
        if (includeDetails) fields.addAll(individualDetailsFieldsDoc(fieldPrefix + "details[].", false));
        return fields;
    }

    private List<FieldDescriptor> individualDetailsFieldsDoc(String fieldPrefix, boolean includePerson) {
        List<FieldDescriptor> fields = new ArrayList<>();
        fields.add(fieldWithPath(fieldPrefix + "id").description("Detail ID."));
        fields.add(fieldWithPath(fieldPrefix + "name").description("Concatenated name."));
        fields.add(fieldWithPath(fieldPrefix + "name1").description("First name."));
        fields.add(fieldWithPath(fieldPrefix + "name2").description("Second name."));
        fields.add(fieldWithPath(fieldPrefix + "surname1").description("First surname."));
        fields.add(fieldWithPath(fieldPrefix + "surname2").description("Second surname."));
        fields.add(fieldWithPath(fieldPrefix + "civilStatus.id").description("Civil status ID."));
        fields.add(fieldWithPath(fieldPrefix + "personStatus.id").description("Person status ID."));
        fields.add(fieldWithPath(fieldPrefix + "country.id").description("Country ID."));
        fields.add(fieldWithPath(fieldPrefix + "language.id").description("Language ID."));
        if (includePerson) fields.addAll(individualFieldsDoc(fieldPrefix + "person.", true, false));
        return fields;
    }

    @Test
    public void getBankCard() throws Exception {

        BankCardDTO bankCardDTO = new BankCardDTO();
        bankCardDTO.setBankCardId(1L);
        bankCardDTO.setCard("5018782000");
        bankCardDTO.setPersonDetailId(1L);
        bankCardDTO.setBankId(1L);
        bankCardDTO.setAvailable(true);
        bankCardDTO.setPrincipal(true);
        bankCardDTO.setSecurityCode(1L);

        Mockito.when(service.getBankCard(any(), any(Errors.class))).thenReturn(bankCardDTO);
        mockMvc.perform(get(PeopleManagementServiceProxy.GET_BANK_CARD)
                .param(PeopleManagementServiceProxy.ID_PARAM, Long.toString(1L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "get-BankCard",
                        responseFields(fieldWithPath("bankCardId").description("BankCardId type ID.")
                                , fieldWithPath("card").description("Card.")
                                , fieldWithPath("personDetailId").description("idPersonDetail.")
                                , fieldWithPath("cardTypeId").description("idCardType.")
                                , fieldWithPath("principal").description("principal.")
                                , fieldWithPath("available").description("available.")
                                , fieldWithPath("bankId").description("idBank.")
                                , fieldWithPath("expirationDate").description("expirationDate.")
                                , fieldWithPath("securityCode").description("securityCode.")
                        )
                ));
    }


    @Test
    public void getAddresformat1() throws Exception {

        AddressFormat1DTO addressFormat1DTO = new AddressFormat1DTO();
        addressFormat1DTO.setAddressId(1L);
        addressFormat1DTO.setIdpoblacion("00");
        addressFormat1DTO.setIdcodpostal("01");
        addressFormat1DTO.setIdtypevia("02");
        addressFormat1DTO.setNombrevia("03");
        addressFormat1DTO.setNumerovia("04");
        addressFormat1DTO.setComplementos("05");

        Mockito.when(service.getAddresformat1(any(), any(Errors.class))).thenReturn(addressFormat1DTO);
        mockMvc.perform(get(PeopleManagementServiceProxy.GET_ADDRESFORMAT1)
                .param(PeopleManagementServiceProxy.ID_PARAM, Long.toString(1L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "get-AddresFormat1",
                        responseFields(fieldWithPath("addressId").description("IdBankCard type ID.")
                                , fieldWithPath("idpoblacion").description("Card.")
                                , fieldWithPath("idcodpostal").description("idPersonDetail.")
                                , fieldWithPath("idtypevia").description("idCardType.")
                                , fieldWithPath("nombrevia").description("principal.")
                                , fieldWithPath("numerovia").description("available.")
                                , fieldWithPath("complementos").description("idBank.")
                                                       )
                ));
    }



    @Test
    public void getAccount() throws Exception {

        AccountClasificationDTO clasification = new AccountClasificationDTO();

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setAccount("5018782000");
        accountDTO.setAccountClasificationId(1L);
        accountDTO.setPersonDetailId(1L);
        accountDTO.setAvailable(true);
        accountDTO.setPrincipal(true);
        accountDTO.setTypeAccountId(1L);
        accountDTO.setBankId(1L);

        Mockito.when(service.getAccount(any(), any(Errors.class))).thenReturn(accountDTO);
        mockMvc.perform(get(PeopleManagementServiceProxy.GET_ACCOUNT)
                .param(PeopleManagementServiceProxy.ID_PARAM, Long.toString(1)))
                .andDo(print())
                .andExpect(jsonPath(ACCOUNT, is(1)))
                .andExpect(status().isOk())
                .andDo(document(
                        "get-Account",
                        responseFields(fieldWithPath("id").description("Id account.")
                                , fieldWithPath("personDetailId").description("personDetail.")
                                , fieldWithPath("accountClasificationId").description("accountClasification.")
                                , fieldWithPath("typeAccountId").description("typeAccount.")
                                , fieldWithPath("account").description("account.")
                                , fieldWithPath("bankId").description("idBank.")
                                , fieldWithPath("principal").description("principal.")
                                , fieldWithPath("available").description("available.")
                        )
                ));
    }

    @Test
    public void countBankCard() throws Exception {

        Mockito.when(service.countBankCards(any())).thenReturn(1L);

        mockMvc.perform(post(PeopleManagementServiceProxy.COUNT_BANK_CARD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildPeopleSearchFilter())
                .param(PeopleManagementServiceProxy.PERSON_DETAIL_ID_PARAM, Long.toString(1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(document(
                        "count-bank-card",
                        requestFields(peopleSearchFilterFieldsDoc())
                ));
    }

    @Test
    public void countAccount() throws Exception {

        Mockito.when(service.countAccount(any())).thenReturn(1L);

        mockMvc.perform(post(PeopleManagementServiceProxy.COUNT_ACCOUNT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildPeopleSearchFilter())
                .param(PeopleManagementServiceProxy.PERSON_DETAIL_ID_PARAM, Long.toString(1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(document(
                        "count-account",
                        requestFields(peopleSearchFilterFieldsDoc())
                ));
    }

    @Test
    public void saveOrUpdateAccount() throws Exception {

        AccountClasificationDTO clasification = new AccountClasificationDTO();

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setAccount("5018782000");
        accountDTO.setAccountClasificationId(1L);
        accountDTO.setPersonDetailId(1L);
        accountDTO.setAvailable(true);
        accountDTO.setPrincipal(true);
        accountDTO.setTypeAccountId(1L);
        accountDTO.setBankId(1L);

        Mockito.when(service.saveOrUpdateAccount(any(), any(Errors.class))).thenReturn(accountDTO);

        mockMvc.perform(put(PeopleManagementServiceProxy.SAVE_ACCOUNT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.asJsonString(accountDTO)))
                .andDo(print())
                .andExpect(status().isOk())

                .andDo(document(
                        "save-update-account",
                        responseFields(fieldWithPath("id").description("Id account.")
                                , fieldWithPath("personDetailId").description("personDetail.")
                                , fieldWithPath("accountClasificationId").description("accountClasification.")
                                , fieldWithPath("typeAccountId").description("typeAccount.")
                                , fieldWithPath("account").description("account.")
                                , fieldWithPath("bankId").description("idBank.")
                                , fieldWithPath("principal").description("principal.")
                                , fieldWithPath("available").description("available.")
                        )
                ));
    }

    @Test
    public void saveOrUpdateBankCard() throws Exception {

        BankCardDTO bankCardDTO = new BankCardDTO();
        bankCardDTO.setBankCardId(1L);
        bankCardDTO.setCard("5018782000");
        bankCardDTO.setPersonDetailId(1L);
        bankCardDTO.setBankId(1L);
        bankCardDTO.setAvailable(true);
        bankCardDTO.setPrincipal(true);
        bankCardDTO.setSecurityCode(1L);
        bankCardDTO.setExpirationDate(LocalDate.of(1972, 11, 22));

        Mockito.when(service.saveOrUpdateBankCard(any(), any(Errors.class))).thenReturn(bankCardDTO);
        mockMvc.perform(put(PeopleManagementServiceProxy.SAVE_BANK_CARD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.asJsonString(bankCardDTO)))
                .andDo(print())
                .andExpect(status().isOk())

                .andDo(document(
                        "save-update-bankCard",
                        responseFields(fieldWithPath("bankCardId").description("IdBankCard type ID.")
                                , fieldWithPath("card").description("Card.")
                                , fieldWithPath("personDetailId").description("idPersonDetail.")
                                , fieldWithPath("cardTypeId").description("idCardType.")
                                , fieldWithPath("principal").description("principal.")
                                , fieldWithPath("available").description("available.")
                                , fieldWithPath("bankId").description("idBank.")
                                , fieldWithPath("expirationDate").description("expirationDate.")
                                , fieldWithPath("securityCode").description("securityCode.")
                        )
                ));

    }

    /** Count contact test. */
    @Test
    public void countContact() throws Exception {

        Mockito.when(service.countContacts(any())).thenReturn(1L);

        mockMvc.perform(post(PeopleManagementServiceProxy.COUNT_CONTACT)
                .param(PeopleManagementServiceProxy.PERSON_DETAIL_ID_PARAM, Long.toString(1L))
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildPeopleSearchFilter()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(document(
                        "count-contact",
                        requestFields(peopleSearchFilterFieldsDoc())
                ));
    }

    /** Delete contact test. */
    @Test
    public void getDeleteContact() throws Exception {

        Mockito.doNothing().when(service).deleteContact(any(), any(Errors.class));

        mockMvc.perform(delete(PeopleManagementServiceProxy.DELETE_CONTACT)
                .param(PeopleManagementServiceProxy.ID_PARAM, Long.toString(1L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "delete-contact",
                        requestParameters(parameterWithName(PeopleManagementServiceProxy.ID_PARAM).description("The id of the contact to delete.")
                        )
                ));
    }


    /*/ get Contact test/*/
    /*
    PER_CONTACT_ID	    NUMBER(19,0)
    PERSON_DETAIL_ID	NUMBER(19,0)
    CONTACT_TYPE_ID	    NUMBER(19,0)
    ADDRESS_ID	        NUMBER(19,0)
    VALUE_CONTACT	    VARCHAR2(100 CHAR)

    */

    @Test
    public void getContact() throws Exception {

        ContactDTO contactDTO = new ContactDTO();

        Mockito.when(service.getContact(any(), any(Errors.class) )).thenReturn(contactDTO);
        mockMvc.perform(get(PeopleManagementServiceProxy.CONTACT)
                .param(PeopleManagementServiceProxy.ID_PARAM, Long.toString(1L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "get-Contact",
                        requestParameters(parameterWithName(PeopleManagementServiceProxy.ID_PARAM).description("The id of the contact.")),
                        responseFields(fieldWithPath("id").description("IdContact type ID.")
                                ,fieldWithPath("contactType").description("contactType.")
                                ,fieldWithPath("valueContact").description("valueContact.")
                                ,fieldWithPath("idAddress").description("idAddress.")
                                ,fieldWithPath("personDetailId").description("personDetail.")

                        )
                ));
    }

    @Test
    public void saveOrUpdateAddresFotmat() throws Exception {

        AddressFormat1DTO addressFormat1DTO = new AddressFormat1DTO();
        addressFormat1DTO.setAddressId(1L);
        addressFormat1DTO.setIdpoblacion("00");
        addressFormat1DTO.setIdcodpostal("01");
        addressFormat1DTO.setIdtypevia("02");
        addressFormat1DTO.setNombrevia("03");
        addressFormat1DTO.setNumerovia("04");
        addressFormat1DTO.setComplementos("05");

        Mockito.when(service.saveOrUpdateAddresFotmat(any(), any(Errors.class))).thenReturn(addressFormat1DTO);
        mockMvc.perform(put(PeopleManagementServiceProxy.SAVE_ADDRESFORMAT1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.asJsonString(addressFormat1DTO)))
                .andDo(print())
                .andExpect(status().isOk())

                .andDo(document(
                        "save-Address-Format",
                        responseFields(fieldWithPath("addressId").description("Identificador de la ADDRESS")
                                , fieldWithPath("idpoblacion").description("Identificador de la  poblacion.")
                                , fieldWithPath("idcodpostal").description("Identificador del Codigo Postal.")
                                , fieldWithPath("idtypevia").description("Codigo de TYPE de via (1-Calle, 2 - Avenida ...).")
                                , fieldWithPath("nombrevia").description("Nombre de la via.")
                                , fieldWithPath("numerovia").description("Numero de via.")
                                , fieldWithPath("complementos").description("Descripcion complementaria.")

                        )
                ));



    }

}