package com.csi.itaca.people.service;

import com.csi.itaca.people.model.dto.*;
import com.csi.itaca.people.model.filters.AccountSearchFilter;
import com.csi.itaca.people.model.filters.BankCardSearchFilter;
import com.csi.itaca.people.model.filters.ContactSearchFilter;
import com.csi.itaca.people.model.filters.PeopleSearchFilter;
import com.csi.itaca.tools.utils.jpa.Order;
import com.csi.itaca.tools.utils.jpa.Pagination;
import org.springframework.validation.Errors;
import java.util.List;

/**
 * People management service.
 * @author bboothe
 */
public interface PeopleManagementService {

    /**
     * Retrieves a specific person.
     * @param id the person id.
     * @param errTracking error tracker. Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     * @return the person if found otherwise null.
     */
    PersonDTO getPerson(Long id, Errors errTracking);

    /**
     * Finds people or a single person based on the supplied filter. Where there will be a single person or a list of
     * people will be based on the configuration.
     * @param filter the search filter.
     * @param errTracking error tracker. Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     * @return a list containing 1 more people depend upon the configuration. Will return an empty list if
     * no people were found.
     */
    List<? extends PersonDTO> findPeople(PeopleSearchFilter filter, Errors errTracking);

    /**
     * Saves or updates the provided person.
     * @param personToSaveOrUpdate the person to save/update.
     * @param errTracking error tracker. Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     */
    PersonDTO saveOrUpdatePerson(PersonDTO personToSaveOrUpdate, Errors errTracking);

    /**
     * Delete the person associated to the provided ID
     * @param personId the id of the person to delete.
     * @param errTracking error tracker. Will advise if person not found.
     *                    Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     */
     void deletePerson(Long personId, Errors errTracking);

    /**
     * Checks if the provided external reference code already exists in the database.
     * @param externalReferenceCode the external reference code to check.
     * @return true if already exists otherwise false.
     */
    Boolean doseExternalReferenceAlreadyExist(String externalReferenceCode);

    /**
     * Finds a list of person detail based on the supplied search criteria.
     * @param criteria search criteria contains the  filter to apply pagination and order properties.
     * @param errTracking error tracker. Will advise if person not found.
     *                    Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     * @return a list of person details.
     */
    List<? extends PersonDetailDTO> findPersonDetails(PeopleSearchFilter criteria, Errors errTracking);

    /**
     * Counts the list of person detail based on the supplied search criteria.
     * @param filter filter to apply
     * @return the number of person details.
     */
    Long countPersonDetails(PeopleSearchFilter filter);

    /**
     * Get a person detail.
     * @param personDetailId the ID of the person detail to retrieve.
     * @param errTracking error tracker. Will advise if person not found.
     *                    Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     * @return The person detail DTO, null if not found.
     */
    PersonDetailDTO getPersonDetail(Long personDetailId, Errors errTracking);

    /**
     * Finds a list of  duplicate person details based on the supplied search criteria.
     * @param criteria search criteria contains the  filter to apply pagination and order properties.
     * @param errTracking error tracker. Will advise if person not found.
     *                    Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     * @return a list of duplicate person details.
     */
    List<? extends PersonDetailDTO> findDuplicatePersonDetails(PeopleSearchFilter criteria, Errors errTracking);

    /**
     * Counts the list of duplicate person details based on the supplied search criteria.
     * @param filter filter to apply
     * @return the number of person details.
     */
    Long countDuplicatePersonDetails(PeopleSearchFilter filter);

    /**
     * Saves or updates the provided account.
     * @param accountToSaveOrUpdate the person to save/update.
     * @param errTracking error tracker. Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     */
    AccountDTO saveOrUpdateAccount(AccountDTO accountToSaveOrUpdate, Errors errTracking);

    /**
     * Counts the list of bank cards detail based on the supplied search criteria.
     * @param idPersonDetail filter to apply
     * @return the number of banks card.
     */
    Long countBankCards(Long idPersonDetail);

    /**
     * Retrieves a specific account.
     * @param id the account id.
     * @param errTracking error tracker. Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     * @return the account if found otherwise null.
     */
    AccountDTO getAccount(Long id, Errors errTracking);

    /**
     * Counts the list of account based on the supplied search criteria.
     * @param idPersonDetail filter to apply
     * @return the number of accounts.
     */
    Long countAccount(Long idPersonDetail);

    /**
     * Saves or updates the provided account.
     * @param bankCardToSaveOrUpdate the person to save/update.
     * @param errTracking error tracker. Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     */
    BankCardDTO saveOrUpdateBankCard(BankCardDTO bankCardToSaveOrUpdate, Errors errTracking);

    /**
     * Retrieves a specific bank card.
     * @param id the bank card id.
     * @param errTracking error tracker. Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     * @return the bank card if found otherwise null.
     */
    BankCardDTO getBankCard(Long id, Errors errTracking);

    /**
     * Retrieves a specific contact.
     * @param idContact the contact id.
     * @param errTracking error tracker. Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     * @return the contact if found otherwise null.
     */
    ContactDTO getContact(Long idContact, Errors errTracking);

    /**
     * Retrieves a specific contact.
     * @param criteria the contact id.
     * @param errTracking error tracker. Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     * @return the contact if found otherwise null.
     */
    List<? extends ContactDTO>  getPersonContact(ContactSearchFilter criteria, Errors errTracking);

    /**
     * Delete the Contact associated to the Contact ID
     * @param contactId the id of the Contact to delete.
     * @param errTracking error tracker. Will advise if Contact not found.
     *                    Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     */
    void deleteContact(Long contactId, Errors errTracking);

    /**
     * the list of Contact based on the supplied search criteria.
     * @param idPersonDetail filter to apply
     * @return the number of Contact.
     */
    Long countContacts(Long idPersonDetail);

    /**
     * Saves or updates contact.
     * @param contactToSaveOrUpdate the contact to save/update.
     * @param errTracking error tracker. Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     */
    ContactDTO saveOrUpdateContact(ContactDTO contactToSaveOrUpdate, Errors errTracking);



    //address
    AddressFormat1DTO getAddresformat1(Long id, Errors errTracking);

    Long countAddresformat1(Long addressId);

    AddressFormat1DTO saveOrUpdateAddresFotmat(AddressFormat1DTO addresFotmatToSaveOrUpdate, Errors errTracking);

    void deleteaddresformat1(Long personId, Errors errTracking);
//person

    PublicPersonDTO getPublicPerson(Long id, Errors errTracking);

    Long counPublicPerson(Long publicpersonId);

    PublicPersonDTO saveOrUpdatePublicPerson(PublicPersonDTO publicPersonFotmatToSaveOrUpdate, Errors errTracking);

    void deletePublicPerson(Long publicpersonId, Errors errTracking);


    /**
     * Returns a count the number of Fiscal Regime detail items based on the supplied search criteria.
     * @param personDetailId the filter to find Fiscal Regime.
     * @return People Fiscal Regime details.
     */
    List<DetPersonFiscalRegimeDTO> getPeopleFiscalRegime(Long personDetailId);

    /**
     * Returns a count the number of Fiscal Regime detail items based on the supplied search criteria.
     * @param personDetailId the filter to find Fiscal Regime.
     * @return People Fiscal Regime details.
   */
    List<DetPersonFiscalRegimeDTO> getPeopleFiscalRegime(Long personDetailId, Pagination pagination, Order order);

    /**
     * Counts the list of Fiscal Regime based on the supplied search criteria.
     * @param personDetailId filter to apply
     * @return the number of accounts.
     */
    Long countFiscalRegime(Long personDetailId);

    /**
     * Deletes a Fiscal Regime together with associated details
     * @param idFicalRegime the id of the Fiscal Regime to delete.
     * @param errTracking error tracker. Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     * @return status ok response if the delete was successful.
     */
    boolean deleteFiscalRegime(Long idFicalRegime, Errors errTracking);
    /**
     * Saves or updates FiscalRegime.
     * @param detPersonFiscalRegimeDTO the Fiscal Regime to save/update.
     */
    DetPersonFiscalRegimeDTO saveOrUpdateDetPeopleFiscalRegime(DetPersonFiscalRegimeDTO detPersonFiscalRegimeDTO, Errors errTracking);

/**
 * Gets a fiscal regime.
 * @param idFicalRegime the fiscal regime id.
 * @return a response body containing the requested fiscal regime json object.
 */
    DetPersonFiscalRegimeDTO getFiscalRegime(Long idFicalRegime, Errors errTracking);


}
