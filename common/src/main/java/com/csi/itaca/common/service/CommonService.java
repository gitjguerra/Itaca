package com.csi.itaca.common.service;

import com.csi.itaca.common.model.dto.CountryDTO;

import java.util.List;

public interface CommonService {

    /** @return looks up all countries. */
    List<CountryDTO> lookupCountries();
}
