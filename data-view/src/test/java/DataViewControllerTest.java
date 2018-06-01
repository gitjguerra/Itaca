import com.csi.itaca.common.model.dto.CountryDTO;
import com.csi.itaca.dataview.controller.DataViewController;
import com.csi.itaca.dataview.service.EntityProvider;
import com.csi.itaca.tools.utils.jpa.Pagination;
import com.csi.itaca.tools.utils.json.JsonUtils;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.queryoption.expression.Expression;
import org.apache.olingo.server.core.uri.UriInfoImpl;
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
public class DataViewControllerTest {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    // To be initialised in the setup method.
    private MockMvc mockMvc;

    @Mock
    private EntityProvider service;

    @InjectMocks
    private DataViewController controller = new DataViewController();

    @Before
    public void setup() {

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    /**
     * Get Entity test.
     */
    @Test
    public void getEntity() throws Exception {
        EntityCollection entityCollection = new EntityCollection();
        Mockito.when(service.getEntitySet(any())).thenReturn(entityCollection);
        mockMvc.perform(get("http://localhost:8081/itaca/odata/PER_BANK")).andExpect(status().isNotFound());
    }

}