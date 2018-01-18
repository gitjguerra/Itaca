package com.csi.itaca.people.endpoint;

import com.csi.itaca.common.endpoint.ItacaBaseRestController;
import com.csi.itaca.people.api.PeopleLookupServiceProxy;
import com.csi.itaca.people.model.dto.*;
import com.csi.itaca.people.service.PeopleLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * RESTful controller for people lookup items.
 * @author bboothe
 */
@RestController
public class PeopleLookupRestController extends ItacaBaseRestController implements PeopleLookupServiceProxy {

    /** lookups service. */
    @Autowired
    private PeopleLookupService peopleLookupService;

    @Override
    @RequestMapping(value = LOOKUP_CIVIL_STATUS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CivilStatusDTO>> lookupCivilStatus() {
        return new ResponseEntity(peopleLookupService.lookupCivilStatus(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOOKUP_PERSON_STATUS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonStatusDTO>> lookupPersonStatus() {
        return new ResponseEntity(peopleLookupService.lookupPersonStatus(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOOKUP_GENDER, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GenderDTO>> lookupGender() {
        return new ResponseEntity(peopleLookupService.lookupGender(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOOKUP_LANGUAGES, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LanguageDTO>> lookupLanguages() {
        return new ResponseEntity(peopleLookupService.lookupLanguages(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOOKUP_ID_TYPES, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IDTypeDTO>> lookupIdTypes() {
        return new ResponseEntity(peopleLookupService.lookupIdTypes(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOOKUP_COMPANY_TYPES, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CompanyTypeDTO>> lookupCompanyTypes() {
        return new ResponseEntity(peopleLookupService.lookupCompanyTypes(), HttpStatus.OK);
    }
}
