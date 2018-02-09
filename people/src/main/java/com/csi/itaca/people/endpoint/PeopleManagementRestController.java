package com.csi.itaca.people.endpoint;

import com.csi.itaca.common.endpoint.ItacaBaseRestController;
import com.csi.itaca.people.api.PeopleManagementServiceProxy;
import com.csi.itaca.people.model.dto.*;
import com.csi.itaca.people.model.filters.AccountSearchFilter;
import com.csi.itaca.people.model.filters.BankCardSearchFilter;
import com.csi.itaca.people.model.filters.PeopleSearchFilter;
import com.csi.itaca.people.service.PeopleManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * RESTful controller for the people management service.
 * @author bboothe
 */
@SuppressWarnings("unchecked")
@RestController
public class PeopleManagementRestController extends ItacaBaseRestController implements PeopleManagementServiceProxy {

    /** The people service. */
    @Autowired
    private PeopleManagementService peopleManagementService;

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

    ////////////////////////////////////////////////////////////////////////////////////////////// Person detail ...
    @Override
    @RequestMapping(value = SEARCH_PERSON_DETAIL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
                                                                              produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<? extends PersonDetailDTO>> findPersonDetails(@RequestBody PeopleSearchFilter criteria) {

        BindingResult errTracking = createErrorTracker(criteria);
        List<? extends PersonDetailDTO> personDetailsDTOs = peopleManagementService.findPersonDetails(criteria, errTracking);
        return buildResponseEntity(personDetailsDTOs, errTracking);
    }

    @Override
    @RequestMapping(value = COUNT_PERSON_DETAIL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> countPersonDetails(@RequestBody PeopleSearchFilter filter) {
        return new ResponseEntity<>(peopleManagementService.countPersonDetails(filter), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = SEARCH_DUPLICATE_PERSON_DETAIL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
                                                                                        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<? extends PersonDetailDTO>> findDuplicatePersonDetails(@RequestBody PeopleSearchFilter criteria) {

        BindingResult errTracking = createErrorTracker(criteria);
        List<? extends PersonDetailDTO> personDetailsDTOs = peopleManagementService.findDuplicatePersonDetails(criteria, errTracking);
        return buildResponseEntity(personDetailsDTOs, errTracking);
    }

    @Override
    @RequestMapping(value = COUNT_DUPLICATE_PERSON_DETAIL, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> countDuplicatePersonDetails(@RequestBody PeopleSearchFilter filter) {
        return new ResponseEntity<>(peopleManagementService.countDuplicatePersonDetails(filter), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = GET_PERSON_DETAIL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends PersonDetailDTO> getPersonDetail(@RequestParam(PeopleManagementServiceProxy.PERSON_DETAIL_ID_PARAM) Long detailId) {
        BindingResult errTracking = createErrorTracker();
        PersonDetailDTO personDetailDTO = peopleManagementService.getPersonDetail(detailId,errTracking);
        return buildResponseEntity(personDetailDTO, errTracking);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////// Person detail end ...



    ////////////////////////////////////////////////////////////////////////////////////////////// Account ...

    @Override
    @RequestMapping(value = SAVE_ACCOUNT, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveOrUpdateAccount(@Valid @RequestBody AccountDTO accountToSaveOrUpdate,
                                              BindingResult errTracking) {
        AccountDTO accountDTO = peopleManagementService.saveOrUpdateAccount(accountToSaveOrUpdate, errTracking);
        return buildResponseEntity(accountDTO, errTracking);
    }

    @Override
    @RequestMapping(value = SAVE_BANK_CARD, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveOrUpdateBankCard(@Valid @RequestBody BankCardDTO bankCardToSaveOrUpdate, BindingResult errTracking) {
        BankCardDTO bankCardDTO = peopleManagementService.saveOrUpdateBankCard(bankCardToSaveOrUpdate, errTracking);
        return buildResponseEntity(bankCardDTO, errTracking);
    }

    @Override
    @RequestMapping(value = GET_ACCOUNT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAccount(@RequestParam(PeopleManagementServiceProxy.ID_PARAM) Long id) {

        BindingResult errTracking = createErrorTracker();
        AccountDTO accountGet = peopleManagementService.getAccount(id, errTracking);
        return buildResponseEntity(accountGet, errTracking);
    }

    @Override
    @RequestMapping(value = COUNT_ACCOUNT, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> countAccount(@RequestParam(PeopleManagementServiceProxy.PERSON_DETAIL_ID_PARAM) Long id) {
        return new ResponseEntity<>(peopleManagementService.countAccount(id), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = GET_BANK_CARD, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getBankCard(@RequestParam(PeopleManagementServiceProxy.ID_PARAM) Long id) {

        BindingResult errTracking = createErrorTracker();
        BankCardDTO bankCard = peopleManagementService.getBankCard(id, errTracking);
        return buildResponseEntity(bankCard, errTracking);
    }

    @Override
    @RequestMapping(value = COUNT_BANK_CARD, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> countBankCards(@RequestParam(PeopleManagementServiceProxy.PERSON_DETAIL_ID_PARAM) Long idPersonDetail) {
        return new ResponseEntity<>(peopleManagementService.countBankCards(idPersonDetail), HttpStatus.OK);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////// Account end ...
    /////////////////////////////////////////////////////////////////////////////////////////////  address ini ...

    @Override
    @RequestMapping(value = GET_ADDRESFORMAT1, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAddresFormat1(@RequestParam(PeopleManagementServiceProxy.ID_PARAM) Long id) {

        BindingResult errTracking = createErrorTracker();
        AddressFormat1DTO user = peopleManagementService.getAddresformat1(id, errTracking);
        return buildResponseEntity(user, errTracking);
    }

    @Override
    @RequestMapping(value = COUNT_ADDRESFORMAT1, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> countAddresFormat1(@RequestParam(PeopleManagementServiceProxy.ID_ADDRES_PARAM) Long addressId) {
        return new ResponseEntity<>(peopleManagementService.countAddresformat1(addressId), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = SAVE_ADDRESFORMAT1, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveOrUpdateAddresFotmat(@Valid @RequestBody AddressFormat1DTO addresFotmatToSaveOrUpdate,
                                              BindingResult errTracking) {
        AddressFormat1DTO addressFormat1DTO = peopleManagementService.saveOrUpdateAddresFotmat(addresFotmatToSaveOrUpdate, errTracking);
        return buildResponseEntity(addressFormat1DTO, errTracking);
    }

    @Override
    @RequestMapping(value = DELETE_ADDRESFORMAT1, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteaddresformat1(@RequestParam(PeopleManagementServiceProxy.ID_PARAM) Long id) {

        BindingResult errTracking = createErrorTracker();
        peopleManagementService.deleteaddresformat1(id,errTracking);
        return buildResponseEntity(errTracking);
    }

}
