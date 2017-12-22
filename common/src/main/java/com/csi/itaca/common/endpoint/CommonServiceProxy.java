package com.csi.itaca.common.endpoint;

import com.csi.itaca.common.model.dto.CountryDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommonServiceProxy {

    // End point URLs...
    String RESOURCE                 = "/common";
    String LOOKUP                    = "/lookup";
    String LOOKUP_COUNTRIES         = RESOURCE + LOOKUP + "/countries";

    /**
     * @return all countries.
     */
    @ApiOperation(value = "Countries lookup.")
    ResponseEntity<List<CountryDTO>> lookupCountries();
}
