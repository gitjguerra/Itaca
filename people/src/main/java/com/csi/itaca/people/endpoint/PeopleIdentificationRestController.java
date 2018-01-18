package com.csi.itaca.people.endpoint;

import com.csi.itaca.common.endpoint.ItacaBaseRestController;
import com.csi.itaca.people.api.PeopleIdentificationServiceProxy;
import com.csi.itaca.people.model.dto.*;
import com.csi.itaca.people.model.filters.IdentificationSearchFilter;

import com.csi.itaca.people.service.PeopleIdentificationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Identification Rest interface.
 * @author bboothe
 */
@RestController
public class PeopleIdentificationRestController extends ItacaBaseRestController implements PeopleIdentificationServiceProxy {

    /** Logger */
    private static Logger logger = Logger.getLogger(PeopleIdentificationRestController.class);

    /** Identification service. */
    @Autowired
    private PeopleIdentificationService peopleIdentificationService;


    @Override
    @RequestMapping(value = SEARCH_IDENTIFICATIONS, method = RequestMethod.GET,  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IdentificationDTO>> listIdentifications(@RequestParam(PERSON_DETAIL_ID_PARAM) Long personDetailId
            , @RequestBody(required=false) IdentificationSearchFilter filter) {
        List<IdentificationDTO> identifications = null;
        if (filter!= null) {
            identifications = peopleIdentificationService.listIdentifications(filter.getPersonDetailsId(),
                    filter.getPagination(),
                    filter.getOrder());
        }
        else {
            identifications = peopleIdentificationService.listIdentifications(personDetailId);
        }
        return new ResponseEntity<>(identifications, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = COUNT_IDENTIFICATIONS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> countIdentifications(@RequestParam(PERSON_DETAIL_ID_PARAM) Long personDetailId) {
        return new ResponseEntity<>(peopleIdentificationService.countIdentification(personDetailId), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = DELETE_IDENTIFICATIONS, method = RequestMethod.DELETE)
    public ResponseEntity deleteIdentifications(@RequestParam(IDENTIFICATION_ID_PARAM) Long identificationId) {
        BindingResult errTracking = createErrorTracker();
        peopleIdentificationService.deleteIdentification(identificationId,errTracking);
        return buildResponseEntity(errTracking);
    }

    @Override
    @RequestMapping(value = SAVE_UPDATE_IDENTIFICATIONS, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
                                                                                     produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IdentificationDTO> saveOrUpdateIdentifications(@RequestBody IdentificationDTO identification) {
        BindingResult errTracking = createErrorTracker();
        IdentificationDTO identificationSaved = peopleIdentificationService.saveOrUpdateIdentification(identification,errTracking);
        return buildResponseEntity(identificationSaved, errTracking);
    }

    @Override
    @RequestMapping(value = GET_IDENTIFICATIONS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IdentificationDTO> getIdentifications(@RequestParam(IDENTIFICATION_ID_PARAM) Long identificationId) {
        BindingResult errTracking = createErrorTracker();
        IdentificationDTO identification = peopleIdentificationService.getIdentification(identificationId, errTracking);
        return buildResponseEntity(identification, errTracking);
    }

}
