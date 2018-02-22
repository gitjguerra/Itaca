package com.csi.itaca.people.service;

import com.csi.itaca.tools.utils.beaner.Beaner;
import com.csi.itaca.people.model.dao.*;
import com.csi.itaca.people.model.dto.*;
import com.csi.itaca.people.repository.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PeopleLookupServiceImpl implements PeopleLookupService {

    private static final Logger logger = LogManager.getLogger(PeopleLookupServiceImpl.class);

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
    private ContactTypeRepository contactTypeRepository;

    @Autowired
    private RelationTypeRepository relationTypeRepository;

    @Autowired
    private CompanyPersonTypeRepository companyPersonTypeRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private AccountClasificationRepository accountClasificationRepository;

    @Autowired
    private CardTypeRepository cardTypeRepository;

    @Autowired
    private Beaner beaner;

    @Autowired
    private AddressFormat1Repository addressFormat1Repository;

    @Autowired
    private PublicPersonRepository publicPersonRepository;

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

    @Override
    @Transactional(readOnly = true)
    public List<CompanyPersonTypeDTO> lookupCompanyPersonTypes() {
        return beaner.transform((List<CompanyPersonTypeEntity>) companyPersonTypeRepository.findAll(), CompanyPersonTypeDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactTypeDTO> lookupContactTypes() {
        return beaner.transform((List<ContactTypeEntity>) contactTypeRepository.findAll(), ContactTypeDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelationTypeDTO> lookupRelationTypes() {
        return beaner.transform((List<RelationTypeEntity>) relationTypeRepository.findAll(), RelationTypeDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankDTO> lookupBanks() {
        return  beaner.transform((List<BankEntity>) bankRepository.findAll(), BankDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountTypeDTO> lookupAccountTypes() {
        return  beaner.transform((List<AccountTypeEntity>) accountTypeRepository.findAll(), AccountTypeDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountClasificationDTO> lookupAccountClasifications() {
        return  beaner.transform((List<AccountClasificationEntity>) accountClasificationRepository.findAll(), AccountClasificationDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardTypeDTO> lookupCardTypes() {
        return  beaner.transform((List<CardTypeEntity>) cardTypeRepository.findAll(), CardTypeDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressFormat1DTO> lookupAddresFormat1() {
        return  beaner.transform((List<AddressFormat1Entity>) addressFormat1Repository.findAll(), AddressFormat1DTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicPersonDTO> lookupPublicPerson() {
        return  beaner.transform((List<PublicPersonEntity>) publicPersonRepository.findAll(), PublicPersonDTO.class);
    }

}
