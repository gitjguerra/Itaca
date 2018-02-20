package com.csi.itaca.people.endpoint;

import com.csi.itaca.common.endpoint.ItacaBaseRestController;
import com.csi.itaca.people.api.PeopleManagementServiceProxy;
import com.csi.itaca.people.model.dto.*;
import com.csi.itaca.people.model.filters.*;
import com.csi.itaca.people.service.PeopleManagementService;
import com.csi.itaca.people.service.PeopleNationalitiesBusinessService;
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

    @Autowired
    private PeopleNationalitiesBusinessService peopleNationalitiesBusinessService;

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


    ////////////////////////////////////////////////////////////////////////////////////////////// Nationalities ...

    @Override
    @RequestMapping(value = SEARCH_NATIONALITY, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NationalityDTO>> getPeopleNationalities(@RequestParam(PERSON_DETAIL_ID_PARAM) Long personDetailId, NationalityOrderPaginFilter filter) {
        List<NationalityDTO> nationalityDTOS = null;
        if (filter!= null) {
            nationalityDTOS = peopleNationalitiesBusinessService.getPeopleNationalities(personDetailId, filter.getPagination(),filter.getOrder());
        }
        else {
            nationalityDTOS = peopleNationalitiesBusinessService.getPeopleNationalities(personDetailId);
        }
        return new ResponseEntity<>(nationalityDTOS, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = COUNT_NATIONALITY , method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> countNationalities(@RequestParam(PERSON_DETAIL_ID_PARAM) Long personDetailId) {
        return new ResponseEntity<>(peopleNationalitiesBusinessService.countNationalities(personDetailId), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = DELETE_NATIONALITY, method = RequestMethod.DELETE)
    public ResponseEntity deleteNationality(@RequestParam(NATIONALITY_ID_PARAM) Long idNationality) {
        BindingResult errTracking = createErrorTracker();
        peopleNationalitiesBusinessService.deleteNationality(idNationality,errTracking);
        return buildResponseEntity(errTracking);
    }

    @Override
    @RequestMapping(value = SAVE_UPDATE_NATIONALITY, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveOrUpdateNationality(@RequestBody NationalityDTO nationality) {
        BindingResult errTracking = createErrorTracker();
        NationalityDTO nationalityDTOSaved = peopleNationalitiesBusinessService.saveOrUpdateNationality(nationality,errTracking);
        return buildResponseEntity(nationalityDTOSaved, errTracking);
    }

    @Override
    @RequestMapping(value = GET_NATIONALITY, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NationalityDTO> getNationality(@RequestParam(NATIONALITY_ID_PARAM) Long idNationality) {
        BindingResult errTracking = createErrorTracker();
        NationalityDTO nationalityDTO = peopleNationalitiesBusinessService.getNationality(idNationality, errTracking);
        return buildResponseEntity(nationalityDTO, errTracking);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////// Nationalities end ...

    ////////////////////////////////////////////////////////////////////////////////////////////// Regime Fiscal...

    @Override
    @RequestMapping(value = COUNT_FISCAL_REGIME, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> countFiscalRegime(@RequestParam(PERSON_DETAIL_ID_PARAM) Long personDetailId) {
        return new ResponseEntity<>(peopleManagementService.countFiscalRegime(personDetailId), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = DELETE_FISCAL_REGIME, method = RequestMethod.DELETE)
    public ResponseEntity deleteFiscalRegime(@RequestParam(FISCAL_REGIME_ID_PARAM) Long idFicalRegime) {
        BindingResult errTracking = createErrorTracker();
        peopleManagementService.deleteFiscalRegime(idFicalRegime,errTracking);
        return buildResponseEntity(errTracking);
    }

    @Override
    @RequestMapping(value = SEARCH_FISCAL_REGIME, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DetPersonFiscalRegimeDTO>> getPeopleFiscalRegime(@RequestParam(PERSON_DETAIL_ID_PARAM) Long personDetailId, FilterDetailPersonFiscalRegimePaginationOrder filter) {
        List<DetPersonFiscalRegimeDTO> detPersonFiscalRegimeDTOS = null;
        if (filter!= null) {
            detPersonFiscalRegimeDTOS = peopleManagementService.getPeopleFiscalRegime(personDetailId, filter.getPagination(),filter.getOrder());
        }
        else {
            detPersonFiscalRegimeDTOS = peopleManagementService.getPeopleFiscalRegime(personDetailId);
        }
        return new ResponseEntity<>(detPersonFiscalRegimeDTOS, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = SAVE_UPDATE_FISCAL_REGIME, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetPersonFiscalRegimeDTO> saveOrUpdateFiscalRegime(@RequestBody DetPersonFiscalRegimeDTO detPersonFiscalRegime) {
        BindingResult errTracking = createErrorTracker();
        DetPersonFiscalRegimeDTO detPersonFiscalRegimeDTOSaved = peopleManagementService.saveOrUpdateDetPeopleFiscalRegime(detPersonFiscalRegime,errTracking);
        return buildResponseEntity(detPersonFiscalRegimeDTOSaved, errTracking);
    }

    @Override
    @RequestMapping(value = GET_FISCAL_REGIME, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetPersonFiscalRegimeDTO> getFiscalRegime(@RequestParam(FISCAL_REGIME_ID_PARAM) Long idFicalRegime) {
        BindingResult errTracking = createErrorTracker();
        DetPersonFiscalRegimeDTO detPersonFiscalRegimeDTO = peopleManagementService.getFiscalRegime(idFicalRegime, errTracking);
        return buildResponseEntity(detPersonFiscalRegimeDTO, errTracking);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////// Regime Fiscal end ...


    ////////////////////////////////////////////////////////////////////////////////////////////// Account ...

    @Override
    @RequestMapping(value = COUNT_BANK_CARD, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> countBankCards(@RequestParam(PeopleManagementServiceProxy.PERSON_DETAIL_ID_PARAM) Long idPersonDetail) {
        return new ResponseEntity<>(peopleManagementService.countBankCards(idPersonDetail), HttpStatus.OK);
    }

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

    ////////////////////////////////////////////////////////////////////////////////////////////// Account end ...


}
