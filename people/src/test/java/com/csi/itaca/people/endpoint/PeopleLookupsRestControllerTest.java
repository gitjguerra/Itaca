package com.csi.itaca.people.endpoint;

import com.csi.itaca.people.api.PeopleLookupServiceProxy;
import com.csi.itaca.people.api.PeopleManagementServiceProxy;
import com.csi.itaca.people.model.AccountClasification;
import com.csi.itaca.people.model.RelationType;
import com.csi.itaca.people.model.dto.*;
import com.csi.itaca.people.service.PeopleLookupService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for the look up end points in the people management rest controller.
 * @author bboothe
 */
@RunWith(PowerMockRunner.class)
public class PeopleLookupsRestControllerTest {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    // To be initialised in the setup method.
    private MockMvc mockMvc;

    @Mock
    private PeopleLookupService lookupService;

    @InjectMocks
    private PeopleLookupServiceProxy controller = new PeopleLookupRestController();

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                                 .apply(documentationConfiguration(this.restDocumentation))
                                 .build();
    }


    /** look up gender test. */
    @Test
    public void genderLookup() throws Exception {

        List<GenderDTO> genders = new ArrayList<>();

        GenderDTO genderDTO = new GenderDTO();
        genderDTO.setId(1L);
        genderDTO.setName("Male");
        genderDTO.setMale(true);
        genderDTO.setFemale(false);
        genderDTO.setOther(false);
        genders.add(genderDTO);

        GenderDTO genderDTO2 = new GenderDTO();
        genderDTO2.setId(2L);
        genderDTO2.setName("Female");
        genderDTO2.setMale(false);
        genderDTO2.setFemale(true);
        genderDTO2.setOther(false);
        genders.add(genderDTO2);

        GenderDTO genderDTO3 = new GenderDTO();
        genderDTO3.setId(3L);
        genderDTO3.setName("Other");
        genderDTO3.setMale(false);
        genderDTO3.setFemale(true);
        genderDTO3.setOther(true);
        genders.add(genderDTO3);

        Mockito.when(lookupService.lookupGender()).thenReturn(genders);

        mockMvc.perform(get(PeopleLookupServiceProxy.LOOKUP_GENDER))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "lookup-gender",
                        responseFields(fieldWithPath("[].id").description("Gender ID."),
                                       fieldWithPath("[].name").description("Name of gender."),
                                       fieldWithPath("[].male").description("'true' indicates male gender item."),
                                       fieldWithPath("[].female").description("'true' indicates female gender item."),
                                       fieldWithPath("[].other").description("'true' indicates male gender item.")
                        )
                        ));

    }

    /** look up civil status test. */
    @Test
    public void civilStatusLookup() throws Exception {

        List<CivilStatusDTO> civilStatuses = new ArrayList<>();

        CivilStatusDTO civilStatusDTO1 = new CivilStatusDTO();
        civilStatusDTO1.setId(1L);
        civilStatusDTO1.setName("married");
        civilStatuses.add(civilStatusDTO1);

        CivilStatusDTO civilStatusDTO2 = new CivilStatusDTO();
        civilStatusDTO2.setId(2L);
        civilStatusDTO2.setName("widowed");
        civilStatuses.add(civilStatusDTO2);

        CivilStatusDTO civilStatusDTO3 = new CivilStatusDTO();
        civilStatusDTO3.setId(3L);
        civilStatusDTO3.setName("separated");
        civilStatuses.add(civilStatusDTO3);

        CivilStatusDTO civilStatusDTO4 = new CivilStatusDTO();
        civilStatusDTO4.setId(4L);
        civilStatusDTO4.setName("single");
        civilStatuses.add(civilStatusDTO4);

        Mockito.when(lookupService.lookupCivilStatus()).thenReturn(civilStatuses);

        mockMvc.perform(get(PeopleLookupServiceProxy.LOOKUP_CIVIL_STATUS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id",is(civilStatusDTO1.getId().intValue())))
                .andExpect(jsonPath("[0].name",is(civilStatusDTO1.getName())))
                .andExpect(jsonPath("[1].id",is(civilStatusDTO2.getId().intValue())))
                .andExpect(jsonPath("[1].name",is(civilStatusDTO2.getName())))
                .andExpect(jsonPath("[2].id",is(civilStatusDTO3.getId().intValue())))
                .andExpect(jsonPath("[2].name",is(civilStatusDTO3.getName())))
                .andExpect(jsonPath("[3].id",is(civilStatusDTO4.getId().intValue())))
                .andExpect(jsonPath("[3].name",is(civilStatusDTO4.getName())))

                .andDo(document(
                        "lookup-civil-status",
                        responseFields(fieldWithPath("[].id").description("Civil Status ID.")
                                      ,fieldWithPath("[].name").description("Name of civil status.")
                        )
                ));
    }


    /** look up identificatio types test. */
    @Test
    public void IdTypesLookup() throws Exception {

        List<IDTypeDTO> idTypes = new ArrayList<>();

        IDTypeDTO idTypeDTO1 = new IDTypeDTO();
        idTypeDTO1.setId(1L);
        idTypeDTO1.setName("birth.certificate");
        idTypeDTO1.setIndividual(true);
        idTypeDTO1.setCompany(false);
        idTypes.add(idTypeDTO1);

        IDTypeDTO idTypeDTO2 = new IDTypeDTO();
        idTypeDTO2.setId(1L);
        idTypeDTO2.setName("company.registration");
        idTypeDTO2.setIndividual(true);
        idTypeDTO2.setCompany(false);
        idTypes.add(idTypeDTO2);

        IDTypeDTO idTypeDTO3 = new IDTypeDTO();
        idTypeDTO3.setId(1L);
        idTypeDTO3.setName("drivers.license");
        idTypeDTO3.setIndividual(true);
        idTypeDTO3.setCompany(false);
        idTypes.add(idTypeDTO3);


        Mockito.when(lookupService.lookupIdTypes()).thenReturn(idTypes);

        mockMvc.perform(get(PeopleLookupServiceProxy.LOOKUP_ID_TYPES))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id",is(idTypeDTO1.getId().intValue())))
                .andExpect(jsonPath("[0].name",is(idTypeDTO1.getName())))
                .andExpect(jsonPath("[0].individual",is(idTypeDTO1.getIndividual())))
                .andExpect(jsonPath("[0].company",is(idTypeDTO1.getCompany())))
                .andExpect(jsonPath("[1].id",is(idTypeDTO2.getId().intValue())))
                .andExpect(jsonPath("[1].name",is(idTypeDTO2.getName())))
                .andExpect(jsonPath("[1].individual",is(idTypeDTO2.getIndividual())))
                .andExpect(jsonPath("[1].company",is(idTypeDTO2.getCompany())))
                .andExpect(jsonPath("[2].id",is(idTypeDTO3.getId().intValue())))
                .andExpect(jsonPath("[2].name",is(idTypeDTO3.getName())))
                .andExpect(jsonPath("[2].individual",is(idTypeDTO3.getIndividual())))
                .andExpect(jsonPath("[2].company",is(idTypeDTO3.getCompany())))

                .andDo(document(
                        "lookup-id-types",
                        responseFields(fieldWithPath("[].id").description("ID of the identification type.")
                                      ,fieldWithPath("[].name").description("Name of the identification type.")
                                      ,fieldWithPath("[].individual").description("'true' if this item is associated with individual.")
                                      ,fieldWithPath("[].company").description("'true' if this item is associated with company.")
                        )
                ));
    }

    /** look up company type test. */
    @Test
    public void companyTypesLookup() throws Exception {

        List<CompanyTypeDTO> companyTypes = new ArrayList<>();

        CompanyTypeDTO companyType1 = new CompanyTypeDTO();
        companyType1.setId(1L);
        companyType1.setName("reinsurance.company");
        companyTypes.add(companyType1);

        CompanyTypeDTO companyType2 = new CompanyTypeDTO();
        companyType2.setId(2L);
        companyType2.setName("company");
        companyTypes.add(companyType2);

        CompanyTypeDTO companyType3 = new CompanyTypeDTO();
        companyType3.setId(3L);
        companyType3.setName("broker");
        companyTypes.add(companyType3);

        Mockito.when(lookupService.lookupCompanyTypes()).thenReturn(companyTypes);

        mockMvc.perform(get(PeopleLookupServiceProxy.LOOKUP_COMPANY_TYPES))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id",is(companyType1.getId().intValue())))
                .andExpect(jsonPath("[0].name",is(companyType1.getName())))
                .andExpect(jsonPath("[1].id",is(companyType2.getId().intValue())))
                .andExpect(jsonPath("[1].name",is(companyType2.getName())))
                .andExpect(jsonPath("[2].id",is(companyType3.getId().intValue())))
                .andExpect(jsonPath("[2].name",is(companyType3.getName())))

                .andDo(document(
                        "lookup-company-types",
                        responseFields(fieldWithPath("[].id").description("Company type ID.")
                                      ,fieldWithPath("[].name").description("Company type name.")
                        )
                ));
    }


    /** look up languages test. */
    @Test
    public void languagesLookup() throws Exception {

        List<LanguageDTO> languages = new ArrayList<>();

        LanguageDTO languageDTO1 = new LanguageDTO();
        languageDTO1.setId(1L);
        languageDTO1.setI18nKey("es-ES");
        languages.add(languageDTO1);

        LanguageDTO languageDTO2 = new LanguageDTO();
        languageDTO2.setId(2L);
        languageDTO2.setI18nKey("en-GB");
        languages.add(languageDTO2);
        
        Mockito.when(lookupService.lookupLanguages()).thenReturn(languages);

        mockMvc.perform(get(PeopleLookupServiceProxy.LOOKUP_LANGUAGES))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id",is(languageDTO1.getId().intValue())))
                .andExpect(jsonPath("[0].i18nKey",is(languageDTO1.getI18nKey())))
                .andExpect(jsonPath("[1].id",is(languageDTO2.getId().intValue())))
                .andExpect(jsonPath("[1].i18nKey",is(languageDTO2.getI18nKey())))

                .andDo(document(
                        "lookup-languages",
                        responseFields(fieldWithPath("[].id").description("Language ID.")
                                ,fieldWithPath("[].i18nKey").description("Language i18nKey.")
                        )
                ));
    }


    /** look up person status test. */
    @Test
    public void personStatusLookup() throws Exception {

        List<PersonStatusDTO> personStatuses = new ArrayList<>();

        PersonStatusDTO personStatus1 = new PersonStatusDTO();
        personStatus1.setId(1L);
        personStatus1.setName("alive");
        personStatus1.setCompany(false);
        personStatus1.setIndividual(false);
        personStatuses.add(personStatus1);

        PersonStatusDTO personStatus2 = new PersonStatusDTO();
        personStatus2.setId(2L);
        personStatus2.setName("died");
        personStatus2.setCompany(false);
        personStatus2.setIndividual(false);
        personStatuses.add(personStatus2);

        PersonStatusDTO personStatus3 = new PersonStatusDTO();
        personStatus3.setId(2L);
        personStatus3.setName("quote.only");
        personStatus3.setCompany(false);
        personStatus3.setIndividual(false);
        personStatuses.add(personStatus3);

        Mockito.when(lookupService.lookupPersonStatus()).thenReturn(personStatuses);

        mockMvc.perform(get(PeopleLookupServiceProxy.LOOKUP_PERSON_STATUS))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id",is(personStatus1.getId().intValue())))
                .andExpect(jsonPath("[0].name",is(personStatus1.getName())))
                .andExpect(jsonPath("[0].individual",is(personStatus1.getIndividual())))
                .andExpect(jsonPath("[0].company",is(personStatus1.getCompany())))

                .andDo(document(
                        "lookup-person-status",
                        responseFields(fieldWithPath("[].id").description("Person status ID.")
                                      ,fieldWithPath("[].name").description("Person status name.")
                                      ,fieldWithPath("[].individual").description("'true' if this item is associated with a individual.")
                                      ,fieldWithPath("[].company").description("'true' if this item is associated with a company.")
                        )
                ));
    }


    /** look up company person type test. */
    @Test
    public void companyPersonTypeLookup() throws Exception {

        List<CompanyPersonTypeDTO> list = new ArrayList<>();

        CompanyPersonTypeDTO item1 = new CompanyPersonTypeDTO();
        item1.setId(1L);
        item1.setLiteral("Company person type 1");
        list.add(item1);

        CompanyPersonTypeDTO item2 = new CompanyPersonTypeDTO();
        item2.setId(2L);
        item2.setLiteral("Company person type 2");
        list.add(item2);

        CompanyPersonTypeDTO item3 = new CompanyPersonTypeDTO();
        item3.setId(3L);
        item3.setLiteral("Company person type 3");
        list.add(item3);


        Mockito.when(lookupService.lookupCompanyPersonTypes()).thenReturn(list);

        mockMvc.perform(get(PeopleLookupServiceProxy.LOOKUP_COMPANY_PERSON_TYPES))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id",is(item1.getId().intValue())))
                .andExpect(jsonPath("[0].literal",is(item1.getLiteral())))

                .andDo(document(
                        "lookup-company-person-type",
                        responseFields(fieldWithPath("[].id").description("Company person type ID.")
                                      ,fieldWithPath("[].literal").description("Literal value")
                        )
                ));
    }



    /** look up contact type test. */
    @Test
    public void contactTypeLookup() throws Exception {

        List<ContactTypeDTO> list = new ArrayList<>();

        ContactTypeDTO item1 = new ContactTypeDTO();
        item1.setId(1L);
        item1.setValue("Telephone");
        list.add(item1);

        ContactTypeDTO item2 = new ContactTypeDTO();
        item2.setId(2L);
        item2.setValue("Email");
        list.add(item2);

        ContactTypeDTO item3 = new ContactTypeDTO();
        item3.setId(3L);
        item3.setValue("Fax");
        list.add(item3);


        Mockito.when(lookupService.lookupContactTypes()).thenReturn(list);

        mockMvc.perform(get(PeopleLookupServiceProxy.LOOKUP_CONTACT_TYPES))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id",is(item1.getId().intValue())))
                .andExpect(jsonPath("[0].value",is(item1.getValue())))

                .andDo(document(
                        "lookup-contact-type",
                        responseFields(fieldWithPath("[].id").description("Contact type ID.")
                                ,fieldWithPath("[].value").description("Contact type name.")
                        )
                ));
    }


    /** look up relation type test. */
    @Test
    public void ralationTypeLookup() throws Exception {

        List<RelationTypeDTO> list = new ArrayList<>();

        RelationTypeDTO item1 = new RelationTypeDTO();
        item1.setId(1L);
        item1.setLiteral("Relation type 1");
        list.add(item1);

        RelationTypeDTO item2 = new RelationTypeDTO();
        item2.setId(2L);
        item2.setLiteral("Relation type 1");
        list.add(item2);

        RelationTypeDTO item3 = new RelationTypeDTO();
        item3.setId(3L);
        item3.setLiteral("Relation type 1");
        list.add(item3);

        Mockito.when(lookupService.lookupRelationTypes()).thenReturn(list);

        mockMvc.perform(get(PeopleLookupServiceProxy.LOOKUP_RELATION_TYPES))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id",is(item1.getId().intValue())))
                .andExpect(jsonPath("[0].literal",is(item1.getLiteral())))

                .andDo(document(
                        "lookup-relation-type",
                        responseFields(fieldWithPath("[].id").description("Relation type ID.")
                                ,fieldWithPath("[].literal").description("literal value.")
                        )
                ));
    }

    /** look up bank test. */
    @Test
    public void bankLookup() throws Exception {

        List<BankDTO> list = new ArrayList<>();

        BankDTO item1 = new BankDTO();
        item1.setId(1L);
        item1.setBankName("BVBA");
        item1.setDraftBank(1L);
        item1.setCode("001");
        item1.setBic("1");
        list.add(item1);

        BankDTO item2 = new BankDTO();
        item2.setId(2L);
        item2.setBankName("ZURICH");
        item2.setDraftBank(2L);
        item2.setCode("002");
        item2.setBic("2");
        list.add(item2);

        BankDTO item3 = new BankDTO();
        item3.setId(3L);
        item3.setBankName("BANESCO");
        item3.setDraftBank(3L);
        item3.setCode("003");
        item3.setBic("3");
        list.add(item3);

        Mockito.when(lookupService.lookupBanks()).thenReturn(list);

        mockMvc.perform(get(PeopleLookupServiceProxy.LOOKUP_BANK))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id",is(item1.getId().intValue())))
                .andExpect(jsonPath("[0].bankName",is(item1.getBankName())))
                .andExpect(jsonPath("[0].draftBank",is(item1.getDraftBank().intValue())))
                .andExpect(jsonPath("[0].code",is(item1.getCode())))
                .andExpect(jsonPath("[0].bic",is(item1.getBic())))

                .andDo(document(
                        "lookup-bank",
                        responseFields(fieldWithPath("[].id").description("Bank type ID.")
                                ,fieldWithPath("[0].bankName").description("Bank type name.")
                                ,fieldWithPath("[0].draftBank").description("Bank Draf.")
                                ,fieldWithPath("[0].bic").description("Bank bic.")
                                ,fieldWithPath("[0].code").description("Bank code.")
                        )
                ));
    }

    /** look up Account type test. */
    @Test
    public void accountTypeLookup() throws Exception {

        List<AccountTypeDTO> list = new ArrayList<>();

        AccountTypeDTO item1 = new AccountTypeDTO();
        item1.setId(1L);
        item1.setValor("valor1");
        list.add(item1);

        AccountTypeDTO item2 = new AccountTypeDTO();
        item2.setId(2L);
        item2.setValor("valor2");
        list.add(item2);

        AccountTypeDTO item3 = new AccountTypeDTO();
        item3.setId(3L);
        item3.setValor("valor3");
        list.add(item3);


        Mockito.when(lookupService.lookupAccountTypes()).thenReturn(list);

        mockMvc.perform(get(PeopleLookupServiceProxy.LOOKUP_ACCOUNT_TYPE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id",is(item1.getId().intValue())))
                .andExpect(jsonPath("[0].valor",is(item1.getValor())))

                .andDo(document(
                        "lookup-account-type",
                        responseFields(fieldWithPath("[].id").description("Account type ID.")
                                ,fieldWithPath("[].valor").description("Literal value")
                        )
                ));
    }

    /** look up Account clasification test. */
    @Test
    public void accountClasificationLookup() throws Exception {

        List<AccountClasificationDTO> list = new ArrayList<>();

        AccountClasificationDTO item1 = new AccountClasificationDTO();
        item1.setId(1L);
        item1.setValue("1");
        list.add(item1);

        AccountClasificationDTO item2 = new AccountClasificationDTO();
        item2.setId(2L);
        item2.setValue("2");
        list.add(item2);

        AccountClasificationDTO item3 = new AccountClasificationDTO();
        item3.setId(3L);
        item3.setValue("3");
        list.add(item3);

        Mockito.when(lookupService.lookupAccountClasifications()).thenReturn(list);

        mockMvc.perform(get(PeopleLookupServiceProxy.LOOKUP_ACCOUNT_CLASIFIED))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id",is(item1.getId().intValue())))
                .andExpect(jsonPath("[0].value",is(item1.getValue())))

                .andDo(document(
                        "lookup-account-clasification",
                        responseFields(fieldWithPath("[].id").description("Account Clasified ID.")
                                ,fieldWithPath("[].value").description("Account Clasified value.")
                        )
                ));
    }

}