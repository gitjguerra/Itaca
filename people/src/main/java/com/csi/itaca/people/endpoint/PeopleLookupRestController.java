package com.csi.itaca.people.endpoint;

import com.csi.itaca.common.endpoint.ItacaBaseRestController;
import com.csi.itaca.people.api.PeopleLookupServiceProxy;
import com.csi.itaca.people.model.AccountClasification;
import com.csi.itaca.people.model.AccountType;
import com.csi.itaca.people.model.CardType;
import com.csi.itaca.people.model.dto.*;
import com.csi.itaca.people.service.PeopleLookupService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
@SuppressWarnings("unchecked")
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

    @Override
    @RequestMapping(value = LOOKUP_COMPANY_PERSON_TYPES, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CompanyPersonTypeDTO>> lookupCompanyPersonTypes() {
        return new ResponseEntity(peopleLookupService.lookupCompanyPersonTypes(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOOKUP_CONTACT_TYPES, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ContactTypeDTO>> lookupContactTypes() {
        return new ResponseEntity(peopleLookupService.lookupContactTypes(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOOKUP_RELATION_TYPES, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RelationTypeDTO>> lookupRelationTypes() {
        return new ResponseEntity(peopleLookupService.lookupRelationTypes(), HttpStatus.OK);
    }

    /** lookups bank and account. */
    @Override
    @RequestMapping(value = LOOKUP_BANK, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BankDTO>> lookupBanks() {
        return new ResponseEntity(peopleLookupService.lookupBanks(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOOKUP_ACCOUNT_TYPE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<? extends AccountType>> lookupAccountTypes() {
        return new ResponseEntity(peopleLookupService.lookupAccountTypes(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOOKUP_ACCOUNT_CLASIFIED, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<? extends AccountClasification>> lookupAccountClasifications() {
        return new ResponseEntity(peopleLookupService.lookupAccountClasifications(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOOKUP_CARD_TYPE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<? extends CardTypeDTO>> lookupCardTypes() {
        return new ResponseEntity(peopleLookupService.lookupCardTypes(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = LOOKUP_ADDRESFORMAT1, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AddressFormat1DTO>> lookupAddresFormat1() {
        return new ResponseEntity(peopleLookupService.lookupAddresFormat1(), HttpStatus.OK);
    }


    @Override
    @RequestMapping(value = LOOKUP_PUBLICPERSON, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PublicPersonDTO>> lookupPublicPerson() {
        return new ResponseEntity(peopleLookupService.lookupPublicPerson(), HttpStatus.OK);
    }


}
