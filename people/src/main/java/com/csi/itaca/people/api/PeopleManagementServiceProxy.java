package com.csi.itaca.people.api;

import com.csi.itaca.people.model.dto.*;
import com.csi.itaca.people.model.filters.*;
import com.csi.itaca.people.model.filters.NationalityOrderPaginFilter;
import com.csi.itaca.people.model.filters.PeopleSearchFilter;
import com.csi.itaca.people.model.filters.RelatedPersonSearchFilter;
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
    String ID_ADDRES_PARAM          = "addressId";
    String ID_PUBLIC_PERSON         = "publicpersonId";
    String EXT_REF_PARAM            = "extRefCode";
    String PERSON_DETAIL_ID_PARAM   = "personDetailId";
    String NATIONALITY_ID_PARAM  = "nationalityId";
    String FISCAL_REGIME_ID_PARAM  = "fiscalRegimeId";
    String COUNTRY_ID = "country.id";
    String BYDEFAULT = "bydefault";


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

    // End points for nationalities....
    String NATIONALITY           = RESOURCE + "/nationality";
    String SEARCH_NATIONALITY             = NATIONALITY + "/find";
    String COUNT_NATIONALITY             = NATIONALITY + "/count";
    String DELETE_NATIONALITY             = NATIONALITY + "/delete";
    String SAVE_UPDATE_NATIONALITY             = NATIONALITY + "/save";
    String GET_NATIONALITY             = NATIONALITY + "/get";

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

    // End points for contacts...
    String RESOURCE_CONTACT           = RESOURCE + "/contact";
    String PERSON_CONTACT             = RESOURCE_CONTACT + "/getPersonContact";
    String CONTACT                    = RESOURCE_CONTACT + "/get";
    String DELETE_CONTACT             = RESOURCE_CONTACT + "/delete";
    String COUNT_CONTACT              = RESOURCE_CONTACT +"/count";
    String SAVE_CONTACT               = RESOURCE_CONTACT + "/save";

   /////// End points for Address.... AG
    String RESOURCE_ADDRESS               = RESOURCE + "/address";
    String GET_ADDRESFORMAT1              = RESOURCE_ADDRESS +"/get";
    String COUNT_ADDRESFORMAT1            = RESOURCE_ADDRESS +"/count";
    String SAVE_ADDRESFORMAT1             = RESOURCE_ADDRESS + "/save";
    String DELETE_ADDRESFORMAT1           = RESOURCE_ADDRESS + "/delete";

    /////// End points for Public Person... AG
    String RESOURCE_PUBLIC               = RESOURCE + "/Public";
    String GET_PUBLICPERSON              = RESOURCE_PUBLIC +"/getPublicPerson";
    String COUNT_PUBLICPERSON            = RESOURCE_PUBLIC +"/counPublicPerson";
    String SAVE_PUBLICPERSON             = RESOURCE_PUBLIC + "/savePublicPerson";
    String DELETE_PUBLICPERSON           = RESOURCE_PUBLIC + "/deletePublicPerson";

    // End points for fiscal regime....
    String FISCAL_REGIME           = RESOURCE + "/regime";
    String SEARCH_FISCAL_REGIME             = FISCAL_REGIME + "/find";
    String COUNT_FISCAL_REGIME             = FISCAL_REGIME + "/count";
    String DELETE_FISCAL_REGIME             = FISCAL_REGIME + "/delete";
    String SAVE_UPDATE_FISCAL_REGIME             = FISCAL_REGIME + "/save";
    String GET_FISCAL_REGIME             = FISCAL_REGIME + "/get";

    // End points for relations...
    String RESOURCE_REL           = RESOURCE + "/relation";
    String SAVE_REL               = RESOURCE_REL + "/save";
    String DELETE_REL             = RESOURCE_REL + "/delete";
    String SEARCH_REL             = RESOURCE_REL + "/search";
    String COUNT_PERSON_REL       = RESOURCE_REL +"/count";
    String GET_REL                = RESOURCE_REL +"/get";

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
    ResponseEntity saveOrUpdatePerson(PersonDTO personToSaveOrUpdate, BindingResult errTracking);
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


    // nationalities

    ResponseEntity<List<NationalityDTO>> getPeopleNationalities(Long personDetailId, NationalityOrderPaginFilter filter);

    ResponseEntity<Long>  countNationalities(Long personDetailId);

    ResponseEntity deleteNationality(Long idNationality);

    ResponseEntity saveOrUpdateNationality(NationalityDTO nationality);

    ResponseEntity<NationalityDTO> getNationality(Long idNationality);

    /**
     * counts bank cards.
     * @param idPersonDetail the filter to find bank cards.
     */
    ResponseEntity<Long> countBankCards(Long idPersonDetail);

    /**
     * Gets a account.
     * @param id the account id.
     * @return a response body containing the requested account json object.
     */
    ResponseEntity getAccount(Long id);

    /**
     * counts accounts.
     * @param idPersonDetail the filter to find bank cards.
     */
    ResponseEntity<Long> countAccount(Long idPersonDetail);

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

    // Regime Fiscal
    /**
     * Gets a contact.
     * @param idContact the contact id.
     * @return a response body containing the requested contact json object.
     */
    ResponseEntity getContact(Long idContact);

    /**
     * counts Fiscal Regime.
     * @param personDetailId the filter to find Fiscal Regime.
     */
    ResponseEntity<Long> countFiscalRegime(Long personDetailId);

    /**
     * Deletes a Fiscal Regime together with associated details
     * @param idFicalRegime the id of the Fiscal Regime to delete.
     * @return status ok response if the delete was successful.
     */
    ResponseEntity deleteFiscalRegime(Long idFicalRegime);

    /**
     * Returns a count the number of Fiscal Regime detail items based on the supplied search criteria.
     * @param filter search filter.
     * @return People Fiscal Regime details.
     */
    ResponseEntity<List<DetPersonFiscalRegimeDTO>> getPeopleFiscalRegime(Long personDetailId, FilterDetailPersonFiscalRegimePaginationOrder filter);

    /**
     * Saves or updates FiscalRegime.
     * @param detPersonFiscalRegime the Fiscal Regime to save/update.
     */
    ResponseEntity<DetPersonFiscalRegimeDTO> saveOrUpdateFiscalRegime(DetPersonFiscalRegimeDTO detPersonFiscalRegime);

    /**
     * Gets a fiscal regime.
     * @param idFicalRegime the fiscal regime id.
     * @return a response body containing the requested fiscal regime json object.
     */
    ResponseEntity<DetPersonFiscalRegimeDTO> getFiscalRegime(Long idFicalRegime);


    /**
     * Gets a contact.
     * @param criteria the contact id.
     * @return a response body containing the requested contact json object.
     */
    ResponseEntity<List<? extends ContactDTO>> getPersonContact(ContactSearchFilter criteria);

    /**
     * Deletes a contact
     * @param idContact the contact id .
     * @return status ok response if the delete was successful.
     */
    ResponseEntity deleteContact(Long idContact);

    /**
     * counts contact.
     * @param idPersonDetail the filter to find contact.
     */
    ResponseEntity<Long> countContact(Long idPersonDetail);

    /**
     * Saves or updates contact.
     * @param contactToSaveOrUpdate the contact to save/update.
     * @param errTracking error tracking.
     */
    ResponseEntity saveOrUpdateContact(ContactDTO contactToSaveOrUpdate,BindingResult errTracking);


    ResponseEntity getAddresFormat1(Long id);

    ResponseEntity<Long> countAddresFormat1(Long addressId);

    ResponseEntity saveOrUpdateAddresFotmat(AddressFormat1DTO addresFotmatToSaveOrUpdate,BindingResult errTracking);

    ResponseEntity deleteaddresformat1(Long id);


    ResponseEntity getPublicPerson(Long id);

    ResponseEntity<Long> counPublicPerson(Long publicpersonId);

    ResponseEntity saveOrUpdatePublicPerson(PublicPersonDTO publicPersonFotmatToSaveOrUpdate,BindingResult errTracking);

    ResponseEntity DeletePublicPerson(Long id);


    /**
     * counts accounts.
     * @param idPersonDetail the filter to find bank cards.
     */
    ResponseEntity<Long> countPersonRelations(Long idPersonDetail);

    /**
     * Deletes a related person together with associated details
     * @param idRelatedPerson the id of the related person to delete.
     * @return status ok response if the delete was successful.
     */
    ResponseEntity deleteRelatedPerson(Long idRelatedPerson);

    /**
     * Saves or updates person.
     * @param relatedPersonToSaveOrUpdate the person to save/update.
     * @param errTracking error tracking.
     */
    ResponseEntity saveOrUpdateRelatedPerson(RelatedPersonDTO relatedPersonToSaveOrUpdate, BindingResult errTracking);

    /**
     * Gets a person.
     * @param idCode the person id.
     * @return a response body containing the requested person json object.
     */
    ResponseEntity<List<? extends PersonDetailDTO>> findByPersonId(Long idCode);

    /**
     * Gets a related person.
     * @param filter search filter.
     * @return a response body containing the requested person json object.
     */
    ResponseEntity<? extends RelatedPersonDTO> getRelatedPerson(RelatedPersonSearchFilter filter, BindingResult errTracking);

}
