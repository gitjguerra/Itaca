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

    /**
     * List of Banks.
     * @return the Card Types.
     */
    List<BankDTO> lookupBanks();

    /**
     * List of Account Types.
     * @return the Card Types.
     */
    List<AccountTypeDTO> lookupAccountTypes();

    /**
     * List of Account Clasification.
     * @return the Card Types.
     */
    List<AccountClasificationDTO> lookupAccountClasifications();

    /**
     * List of Card Type.
     * @return the Card Types.
     */
    List<CardTypeDTO>  lookupCardTypes();
    //address
    List<AddressFormat1DTO>  lookupAddresFormat1();

    List<PublicPersonDTO>  lookupPublicPerson();
}
