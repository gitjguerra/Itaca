package com.csi.itaca.common.endpoint;

import com.csi.itaca.common.model.dto.CountryDTO;
import com.csi.itaca.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RESTful interface for the common service.
 * @author bboothe
 */
@RestController
public class CommonRestController extends ItacaBaseRestController implements CommonServiceProxy {

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = LOOKUP_COUNTRIES, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CountryDTO>> lookupCountries() {
        return new ResponseEntity(commonService.lookupCountries(), HttpStatus.OK);
    }
}
