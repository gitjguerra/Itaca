package com.csi.itaca.common.service;

import com.csi.itaca.common.model.dao.CountryEntity;
import com.csi.itaca.common.model.dto.CountryDTO;
import com.csi.itaca.common.repository.CountryRepository;
import com.csi.itaca.tools.utils.beaner.Beaner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private Beaner beaner;

    @Override
    public List<CountryDTO> lookupCountries() {
        return beaner.transform((List<CountryEntity>)countryRepository.findAll(),CountryDTO.class);
    }
}
