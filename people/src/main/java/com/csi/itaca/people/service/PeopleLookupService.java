package com.csi.itaca.people.service;

import com.csi.itaca.people.model.dto.*;

import java.util.List;

public interface PeopleLookupService {

    public List<CivilStatusDTO> lookupCivilStatus();

    public List<PersonStatusDTO> lookupPersonStatus();

    public List<GenderDTO> lookupGender();

    public List<LanguageDTO> lookupLanguages();

    public List<IDTypeDTO> lookupIdTypes();

    public List<CompanyTypeDTO> lookupCompanyTypes();

}
