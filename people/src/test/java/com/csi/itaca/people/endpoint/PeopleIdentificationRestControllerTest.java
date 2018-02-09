package com.csi.itaca.people.endpoint;

import com.csi.itaca.common.model.dto.CountryDTO;
import com.csi.itaca.people.api.PeopleIdentificationServiceProxy;
import com.csi.itaca.people.model.dao.IdentificationEntity;
import com.csi.itaca.people.model.dto.*;
import com.csi.itaca.people.service.PeopleIdentificationService;
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
import static org.mockito.Matchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test for the people management rest controller.
 * @author bboothe
 */
@RunWith(PowerMockRunner.class)
public class PeopleIdentificationRestControllerTest {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    // To be initialised in the setup method.
    private MockMvc mockMvc;

    @Mock
    private PeopleIdentificationService service;

    @InjectMocks
    private PeopleIdentificationServiceProxy controller = new PeopleIdentificationRestController();

    /** Our test identification bean. */
    private IdentificationDTO testIdDTO;

    @Before
    public void setup() {

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                                 .apply(documentationConfiguration(this.restDocumentation))
                                 .build();

        testIdDTO = new IdentificationDTO();
        testIdDTO.setId(1L);
        testIdDTO.setIdentificationCode("124LD");
        testIdDTO.setPersonDetailId(8678L);
        testIdDTO.setIssueDate(LocalDate.of(2004,06,01) );
        CountryDTO country = new CountryDTO();
        country.setId(500L);
        testIdDTO.setCountry(country);
        IDTypeDTO idTypeDTO = new IDTypeDTO();
        idTypeDTO.setId(12L);
        testIdDTO.setIdType(idTypeDTO);
    }

    /** Get identification test. */
    @Test
    public void getIdentification() throws Exception {
        Mockito.when(service.getIdentification(any(), any(Errors.class) )).thenReturn(testIdDTO);
        mockMvc.perform(get(PeopleIdentificationServiceProxy.GET_IDENTIFICATIONS)
                .param(PeopleIdentificationServiceProxy.IDENTIFICATION_ID_PARAM, Long.toString(1L)))
                .andDo(print())
                .andExpect(jsonPath(IdentificationEntity.ID, is((testIdDTO.getId().intValue()))))
                .andExpect(jsonPath(IdentificationEntity.DETAIL_PERSON_ID, is(testIdDTO.getPersonDetailId().intValue())))
                .andExpect(jsonPath(IdentificationEntity.IDENTIFICATION_CODE,is(testIdDTO.getIdentificationCode())))
                .andExpect(status().isOk())
                .andDo(document(
                        "get-identification",
                        requestParameters(parameterWithName(PeopleIdentificationServiceProxy.IDENTIFICATION_ID_PARAM).description("The ID of the identification to retrieve.")),
                        responseFields(identificationBeanFieldsDoc())

                ));
    }

    /** Save/update identification test. */
    @Test
    public void saveOrUpdateIdentification() throws Exception {
        Mockito.when(service.saveOrUpdateIdentification(any(), any(Errors.class) )).thenReturn(testIdDTO);
        mockMvc.perform(put(PeopleIdentificationServiceProxy.SAVE_UPDATE_IDENTIFICATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.asJsonString(testIdDTO)))
                .andDo(print())
                .andExpect(jsonPath(IdentificationEntity.ID, is((testIdDTO.getId().intValue()))))
                .andExpect(jsonPath(IdentificationEntity.DETAIL_PERSON_ID, is(testIdDTO.getPersonDetailId().intValue())))
                .andExpect(jsonPath(IdentificationEntity.IDENTIFICATION_CODE,is(testIdDTO.getIdentificationCode())))
                .andExpect(status().isOk())
                .andDo(document(
                        "save-identification",
                        requestFields(identificationBeanFieldsDoc()),
                        responseFields(identificationBeanFieldsDoc())
                ));
    }

    /** Delete identification test. */
    @Test
    public void deleteIdentification() throws Exception {
        Mockito.when(service.deleteIdentification(any(), any(Errors.class) )).thenReturn(true);
        mockMvc.perform(delete(PeopleIdentificationServiceProxy.DELETE_IDENTIFICATIONS)
                .param(PeopleIdentificationServiceProxy.IDENTIFICATION_ID_PARAM, Long.toString(88L)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        "delete-identification",
                        requestParameters(parameterWithName(PeopleIdentificationServiceProxy.IDENTIFICATION_ID_PARAM).description("The ID of the identification to delete."))
                ));
    }


    /** Count identification test. */
    @Test
    public void countIdentification() throws Exception {
        Mockito.when(service.countIdentification( any() )).thenReturn(5L);
        mockMvc.perform(get(PeopleIdentificationServiceProxy.COUNT_IDENTIFICATIONS)
                .param(PeopleIdentificationServiceProxy.PERSON_DETAIL_ID_PARAM, Long.toString(423L)))
                .andDo(print())
                .andExpect(content().string("5"))
                .andExpect(status().isOk())
                .andDo(document(
                        "count-identifications",
                        requestParameters(parameterWithName(PeopleIdentificationServiceProxy.PERSON_DETAIL_ID_PARAM).description("The person detail id."))
                ));
    }


    /** Find identification test. */
    @Test
    public void findIdentification() throws Exception {
        Mockito.when(service.listIdentifications(any())).thenReturn(Collections.singletonList(testIdDTO));
        mockMvc.perform(get(PeopleIdentificationServiceProxy.SEARCH_IDENTIFICATIONS)
                .contentType(MediaType.APPLICATION_JSON)
                .param(PeopleIdentificationServiceProxy.PERSON_DETAIL_ID_PARAM, Long.toString(1L)))
                .andDo(print())
                .andExpect(jsonPath("[0]"+IdentificationEntity.ID, is((testIdDTO.getId().intValue()))))
                .andExpect(jsonPath("[0]"+IdentificationEntity.DETAIL_PERSON_ID, is(testIdDTO.getPersonDetailId().intValue())))
                .andExpect(jsonPath("[0]"+IdentificationEntity.IDENTIFICATION_CODE,is(testIdDTO.getIdentificationCode())))
                .andExpect(status().isOk())
                .andDo(document(
                        "find-identifications",
                        requestParameters(parameterWithName(PeopleIdentificationServiceProxy.PERSON_DETAIL_ID_PARAM).description("The person detail id.")),
                        responseFields(identificationBeanFieldsDoc("[]"))

                ));
    }

    /**
     * Returns a list of field descriptors associated to the Identification bean.
     * @return the list of document fields.
     */
    private List<FieldDescriptor> identificationBeanFieldsDoc() {
        return identificationBeanFieldsDoc("");
    }


    /**
     * Returns a list of field descriptors associated to the Identification bean.
     * @param prefix string value to insert before each field.
     * @return the list of document fields.
     */
    private List<FieldDescriptor> identificationBeanFieldsDoc(String prefix) {
        List<FieldDescriptor> fields = new ArrayList<>();
        fields.add(fieldWithPath(prefix+"id").description("The unique identifier."));
        fields.add(fieldWithPath(prefix+"personDetailId").description("The ID associated with the identification document type."));
        fields.add(fieldWithPath(prefix+"idType.id").description("The ID associated with the identification document type."));
        fields.add(fieldWithPath(prefix+"country.id").description("The ID issuing country."));
        fields.add(fieldWithPath(prefix+"identificationCode").description("ID code for the identification."));
        fields.add(fieldWithPath(prefix+"issueDate").description("Date the identification was issued."));
        return fields;
    }

}