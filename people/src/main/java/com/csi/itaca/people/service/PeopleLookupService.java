package com.csi.itaca.people.service;

import com.csi.itaca.people.model.dto.*;

import java.util.List;

public interface PeopleLookupService {

    List<CivilStatusDTO> lookupCivilStatus();

    List<PersonStatusDTO> lookupPersonStatus();

    List<GenderDTO> lookupGender();

    List<LanguageDTO> lookupLanguages();

    List<IDTypeDTO> lookupIdTypes();

    List<CompanyTypeDTO> lookupCompanyTypes();

    List<CompanyPersonTypeDTO> lookupCompanyPersonTypes();

    List<ContactTypeDTO> lookupContactTypes();

    List<RelationTypeDTO> lookupRelationTypes();

    List<BankDTO> lookupBanks();

    List<AccountTypeDTO> lookupAccountTypes();

    List<AccountClasificationDTO> lookupAccountClasifications();

}
