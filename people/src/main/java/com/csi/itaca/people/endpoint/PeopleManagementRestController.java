package com.csi.itaca.people.endpoint;

import com.csi.itaca.common.endpoint.ItacaBaseRestController;
import com.csi.itaca.people.api.PeopleManagementServiceProxy;
import com.csi.itaca.people.model.dto.GenderDTO;
import com.csi.itaca.people.model.dto.PersonDTO;
import com.csi.itaca.people.model.filters.PeopleSearchFilter;
import com.csi.itaca.people.service.PeopleLookupService;
import com.csi.itaca.people.service.PeopleManagementService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * RESTful interface for the people management service.
 * @author bboothe
 */
@RestController
public class PeopleManagementRestController extends ItacaBaseRestController implements PeopleManagementServiceProxy {

    /** Logger */
    private static Logger logger = Logger.getLogger(PeopleManagementRestController.class);

    /** The people service for this rest controller. */
    @Autowired
    private PeopleManagementService peopleManagementService;

    /** All lookups provided by this service. */
    @Autowired
    private PeopleLookupService peopleLookupService;


    @Override
    @RequestMapping(value = GET_PERSON, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPerson(@RequestParam(PeopleManagementServiceProxy.ID_PARAM) Long id) {

        BindingResult errTracking = createErrorTracker();
        PersonDTO user = peopleManagementService.getPerson(id, errTracking);
        return buildResponseEntity(user, errTracking);
    }

    @Override
    @RequestMapping(value = SEARCH_PEOPLE, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
                                                                        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findPeople(@Valid @RequestBody PeopleSearchFilter filter, BindingResult errTracking) {

        List<? extends PersonDTO> people = peopleManagementService.findPeople(filter, errTracking);
        return buildResponseEntity(people, errTracking);
    }

    @Override
    @RequestMapping(value = SAVE_PERSON, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
                                                                      produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveOrUpdatePerson(@Valid @RequestBody PersonDTO personToSaveOrUpdate,
                                             BindingResult errTracking) {

        PersonDTO personaDTO = peopleManagementService.saveOrUpdatePerson(personToSaveOrUpdate, errTracking);
        return buildResponseEntity(personaDTO, errTracking);
    }

    @Override
    @RequestMapping(value = DELETE_PERSON, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deletePerson(@RequestParam(PeopleManagementServiceProxy.ID_PARAM) Long id) {

        BindingResult errTracking = createErrorTracker();
        peopleManagementService.deletePerson(id,errTracking);
        return buildResponseEntity(errTracking);
    }

    @Override
    @RequestMapping(value = EXT_REF_EXISTS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkExternalReferenceExists
                                (@RequestParam(PeopleManagementServiceProxy.EXT_REF_PARAM) String externalReferenceCode) {
        boolean refExists = peopleManagementService.doseExternalReferenceAlreadyExist(externalReferenceCode);
        return new ResponseEntity<>(refExists, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOOKUP_CIVIL_STATUS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenderDTO>> lookupCivilStatus() {
        return new ResponseEntity(peopleLookupService.lookupCivilStatus(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOOKUP_PERSON_STATUS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenderDTO>> lookupPersonStatus() {
        return new ResponseEntity(peopleLookupService.lookupPersonStatus(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOOKUP_GENDER, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenderDTO>> lookupGender() {
        return new ResponseEntity(peopleLookupService.lookupGender(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOOKUP_LANGUAGES, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenderDTO>> lookupLanguages() {
        return new ResponseEntity(peopleLookupService.lookupLanguages(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOOKUP_ID_TYPES, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenderDTO>> lookupIdTypes() {
        return new ResponseEntity(peopleLookupService.lookupIdTypes(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOOKUP_COMPANY_TYPES, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenderDTO>> lookupCompanyTypes() {
        return new ResponseEntity(peopleLookupService.lookupCompanyTypes(), HttpStatus.OK);
    }
}
