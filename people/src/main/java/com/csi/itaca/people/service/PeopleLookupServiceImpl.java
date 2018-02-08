package com.csi.itaca.people.service;

import com.csi.itaca.tools.utils.beaner.Beaner;
import com.csi.itaca.people.model.dao.*;
import com.csi.itaca.people.model.dto.*;
import com.csi.itaca.people.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PeopleLookupServiceImpl implements PeopleLookupService {

    @Autowired
    private CivilStatusRepository civilStatusRepository;

    @Autowired
    private PersonStatusRepository personStatusRepository;

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private IDTypeRepository idTypeRepository;

    @Autowired
    private CompanyTypeRepository companyTypeRepository;


    @Autowired
    private Beaner beaner;


    @Override
    @Transactional(readOnly = true)
    public List<CivilStatusDTO> lookupCivilStatus () {
        return beaner.transform((List<CivilStatusEntity>) civilStatusRepository.findAll(), CivilStatusDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonStatusDTO> lookupPersonStatus() {
        return beaner.transform((List<PersonStatusEntity>) personStatusRepository.findAll(), PersonStatusDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenderDTO> lookupGender() {
        return beaner.transform((List<GenderEntity>) genderRepository.findAll(), GenderDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LanguageDTO> lookupLanguages() {
        return beaner.transform((List<LanguageEntity>) languageRepository.findAll(), LanguageDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IDTypeDTO> lookupIdTypes() {
        return  beaner.transform((List<IDTypeEntity>) idTypeRepository.findAll(), IDTypeDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyTypeDTO> lookupCompanyTypes() {
        return beaner.transform((List<CompanyTypeEntity>) companyTypeRepository.findAll(), CompanyTypeDTO.class);
    }

}
