package com.csi.itaca.people.api;

import com.csi.itaca.people.model.AccountClasification;
import com.csi.itaca.people.model.AccountType;
import com.csi.itaca.people.model.dto.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * People look up service proxy interface.
 * @author bboothe
 */
public interface PeopleLookupServiceProxy {

    // Lookup end point URLs...
    String LOOKUP                       = PeopleManagementServiceProxy.RESOURCE +"/lookup";
    String LOOKUP_CIVIL_STATUS          = LOOKUP +"/civilStatus";
    String LOOKUP_PERSON_STATUS         = LOOKUP +"/personStatus";
    String LOOKUP_GENDER                = LOOKUP +"/gender";
    String LOOKUP_LANGUAGES             = LOOKUP +"/languages";
    String LOOKUP_ID_TYPES              = LOOKUP +"/idTypes";
    String LOOKUP_COMPANY_TYPES         = LOOKUP +"/companyTypes";
    String LOOKUP_COMPANY_PERSON_TYPES  = LOOKUP +"/companyPersonTypes";
    String LOOKUP_CONTACT_TYPES         = LOOKUP +"/contactTypes";
    String LOOKUP_RELATION_TYPES        = LOOKUP +"/relationTypes";

    String LOOKUP_BANK                  = LOOKUP +"/bank";
    String LOOKUP_ACCOUNT_TYPE          = LOOKUP +"/type";
    String LOOKUP_ACCOUNT_CLASIFIED     = LOOKUP +"/clasified";
    String LOOKUP_CARD_TYPE             = LOOKUP +"/cardType";

    String LOOKUP_CARD_TYPE_CONTACT_LIST                 = LOOKUP +"/contactsList";


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

    /** @return a list of company person types.*/
    ResponseEntity<List<CompanyPersonTypeDTO>> lookupCompanyPersonTypes();

    /** @return a list of contact types.*/
    ResponseEntity<List<ContactTypeDTO>> lookupContactTypes();

    /** @return a list of relation types.*/
    ResponseEntity<List<RelationTypeDTO>> lookupRelationTypes();

    /** @return a list of banks.*/
    ResponseEntity<List<BankDTO>> lookupBanks();

    /** @return a list of AccountType.*/
    ResponseEntity<List<? extends AccountType>> lookupAccountTypes();

    /** @return a list of AccountClassification.*/
    ResponseEntity<List<? extends AccountClasification>> lookupAccountClasifications();

    /** @return a list of CardTypes.*/
    ResponseEntity<List<?  extends CardTypeDTO>> lookupCardTypes();

    /** @return a list of Contacts.*/
    ResponseEntity<Long> listContacts();

}
