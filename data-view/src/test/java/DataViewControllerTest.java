import com.csi.itaca.dataview.controller.DataViewController;
import com.csi.itaca.dataview.service.EntityProvider;
import org.apache.olingo.commons.api.data.EntityCollection;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Matchers.any;

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