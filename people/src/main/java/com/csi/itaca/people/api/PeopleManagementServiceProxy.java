package com.csi.itaca.people.api;

import com.csi.itaca.people.model.dto.*;
import com.csi.itaca.people.model.filters.AccountSearchFilter;
import com.csi.itaca.people.model.filters.BankCardSearchFilter;
import com.csi.itaca.people.model.filters.PeopleSearchFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * People core management service proxy interface.
 * @author bboothe
 */
public interface PeopleManagementServiceProxy {

    // Parameters...
    String ID_PARAM                 = "id";
    String EXT_REF_PARAM            = "extRefCode";
    String PERSON_DETAIL_ID_PARAM   = "personDetailId";


    // End points for people...
    String RESOURCE                 = "/people";
    String GET_PERSON               = RESOURCE + "/get";
    String SEARCH_PEOPLE            = RESOURCE + "/search";
    String SAVE_PERSON              = RESOURCE + "/save";
    String DELETE_PERSON            = RESOURCE + "/delete";
    String EXT_REF_EXISTS           = RESOURCE + "/extRefExists";

    // End points for person detail...
    String PERSON_DETAIL_RESOURCE           = RESOURCE + "/detail";
    String SEARCH_PERSON_DETAIL             = PERSON_DETAIL_RESOURCE + "/find";
    String COUNT_PERSON_DETAIL              = PERSON_DETAIL_RESOURCE + "/count";
    String SEARCH_DUPLICATE_PERSON_DETAIL   = PERSON_DETAIL_RESOURCE + "/findDuplicates";
    String COUNT_DUPLICATE_PERSON_DETAIL    = PERSON_DETAIL_RESOURCE + "/countDuplicates";
    String GET_PERSON_DETAIL                = PERSON_DETAIL_RESOURCE + "/get";

    // End points for account...
    String RESOURCE_ACCOUNT           = RESOURCE + "/account";
    String SAVE_ACCOUNT               = RESOURCE_ACCOUNT + "/save";
    String DELETE_ACCOUNT             = RESOURCE_ACCOUNT + "/delete";
    String SEARCH_ACCOUNT             = RESOURCE_ACCOUNT + "/search";
    String COUNT_ACCOUNT              = RESOURCE_ACCOUNT +"/count";
    String GET_ACCOUNT                = RESOURCE_ACCOUNT +"/get";

    String COUNT_BANK_CARD            = RESOURCE_ACCOUNT +"/countCard";
    String GET_BANK_CARD              = RESOURCE_ACCOUNT +"/getCard";
    String SAVE_BANK_CARD             = RESOURCE_ACCOUNT +"/saveCard";

    /**
     * Gets a person.
     * @param id the person id.
     * @return a response body containing the requested person json object.
     */
    ResponseEntity getPerson(Long id);

    /**
     * Find a person based on the supplied key.
     * @param filter the filter to apply.
     * @param errTracking error tracking.
     * @return a list containing 1 more people depend upon the configuration. Will return an empty list if
     * no people were found.
     */
    ResponseEntity findPeople(PeopleSearchFilter filter, BindingResult errTracking);

    /**
     * Saves or updates person.
     * @param personToSaveOrUpdate the person to save/update.
     * @param errTracking error tracking.
     */
    ResponseEntity saveOrUpdatePerson(PersonDTO personToSaveOrUpdate,
                                      BindingResult errTracking);
    /**
     * Deletes a person together with associated details
     * @param id the id of the person to delete.
     * @return status ok response if the delete was successful.
     */
    ResponseEntity deletePerson(Long id);

    /**
     * Checks if provided external reference code is in use.
     * @param externalReferenceCode external reference code to check.
     * @return true if provided external reference code exists, otherwise false.
     */
    ResponseEntity<Boolean> checkExternalReferenceExists(String externalReferenceCode);


    //////////////////////// Person detail ...

    /**
     * Retrieves a list person detail items based on the supplied search criteria.
     * @param criteria search criteria.
     * @return a list of people.
     */
    ResponseEntity<List<? extends PersonDetailDTO>> findPersonDetails(PeopleSearchFilter criteria);

    /**
     * Returns a count the number of person detail items based on the supplied search criteria.
     * @param filter search filter.
     * @return counts person details.
     */
    ResponseEntity<Long> countPersonDetails(PeopleSearchFilter filter);

    /**
     * Retrieves a list duplicate person detail items based on the supplied search criteria.
     * @param criteria search criteria.
     * @return a list of duplicate people.
     */
    ResponseEntity<List<? extends PersonDetailDTO>> findDuplicatePersonDetails(PeopleSearchFilter criteria);

    /**
     * Returns a counts duplicate person detail items based on the supplied search criteria.
     * @param filter search filter.
     * @return number of duplicated people.
     */
    ResponseEntity<Long> countDuplicatePersonDetails(PeopleSearchFilter filter);

    /**
     * Gets a person detail item.
     * @param detailId the ID of the person detail to retrieve.
     * @return the person.
     */
    ResponseEntity<? extends PersonDetailDTO> getPersonDetail(Long detailId);

    /**
     * Saves or updates account.
     * @param accountToSaveOrUpdate the account to save/update.
     * @param errTracking error tracking.
     */
    ResponseEntity saveOrUpdateAccount(AccountDTO accountToSaveOrUpdate,BindingResult errTracking);

    /**
     * counts bank cards.
     * @param filter the filter to find bank cards.
     */
    ResponseEntity<Long> countBankCards(BankCardSearchFilter filter);

    /**
     * Gets a account.
     * @param id the account id.
     * @return a response body containing the requested account json object.
     */
    ResponseEntity getAccount(Long id);

    /**
     * counts accounts.
     * @param filter the filter to find bank cards.
     */
    ResponseEntity<Long> countAccount(AccountSearchFilter filter);

    /**
     * Saves or updates account.
     * @param bankCardToSaveOrUpdate the bank card to save/update.
     * @param errTracking error tracking.
     */
    ResponseEntity saveOrUpdateBankCard(BankCardDTO bankCardToSaveOrUpdate,BindingResult errTracking);

    /**
     * Gets a Bank Card.
     * @param id the bank card id.
     * @return a response body containing the requested person json object.
     */
    ResponseEntity getBankCard(Long id);

}
