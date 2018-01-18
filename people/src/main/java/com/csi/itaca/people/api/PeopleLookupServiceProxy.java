package com.csi.itaca.people.api;

import com.csi.itaca.people.model.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * People look up service proxy interface.
 * @author bboothe
 */
public interface PeopleLookupServiceProxy {

    // Lookup end point URLs...
    String LOOKUP                   = PeopleManagementServiceProxy.RESOURCE +"/lookup";
    String LOOKUP_CIVIL_STATUS      = LOOKUP +"/civilStatus";
    String LOOKUP_PERSON_STATUS     = LOOKUP +"/personStatus";
    String LOOKUP_GENDER            = LOOKUP +"/gender";
    String LOOKUP_LANGUAGES         = LOOKUP +"/languages";
    String LOOKUP_ID_TYPES          = LOOKUP +"/idTypes";
    String LOOKUP_COMPANY_TYPES     = LOOKUP +"/companyTypes";

    /** @return a list of civil statuses.*/
    ResponseEntity<List<CivilStatusDTO>> lookupCivilStatus();

    /** @return a list of person statuses*/
    ResponseEntity<List<PersonStatusDTO>> lookupPersonStatus();

    /** @return a list of genders.*/
    ResponseEntity<List<GenderDTO>> lookupGender();

    /** @return a list of languages.*/
    ResponseEntity<List<LanguageDTO>> lookupLanguages();

    /** @return a list of idTypes.*/
    ResponseEntity<List<IDTypeDTO>> lookupIdTypes();

    /** @return a list of company types.*/
    ResponseEntity<List<CompanyTypeDTO>> lookupCompanyTypes();
}
