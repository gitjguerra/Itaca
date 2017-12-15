package com.csi.itaca.people.endpoint;

import com.csi.itaca.common.rest.ItacaBaseRestController;
import com.csi.itaca.people.api.PeopleManagementServiceProxy;
import com.csi.itaca.people.model.dto.PersonDTO;
import com.csi.itaca.people.service.PeopleManagementService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTful interface for the people management service.
 * @author bboothe
 */
@RestController
public class PeopleManagementRestController extends ItacaBaseRestController implements PeopleManagementServiceProxy {

    /** Logger */
    private static Logger logger = Logger.getLogger(PeopleManagementRestController.class);

    /** The people service for this rest controller. */
    @Autowired
    private PeopleManagementService peopleManagementService;


    @Override
    @RequestMapping(value = GET_PERSON, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity getPerson(@RequestParam(PeopleManagementServiceProxy.ID_PARAM) Long id) {

        BindingResult errTracking = createErrorTracker();
        PersonDTO user = peopleManagementService.getPerson(id, errTracking);
        return buildResponseEnity(user, errTracking);
    }
}
