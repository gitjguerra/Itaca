package com.csi.itaca.people.businesslogic;

import com.csi.itaca.common.utils.beaner.Beaner;
import com.csi.itaca.config.model.Configurator;
import com.csi.itaca.people.api.PeopleModuleConfiguration;
import com.csi.itaca.people.api.PeopleModuleConfiguration.IndividualLogicalPrimaryKey;
import com.csi.itaca.people.api.PeopleModuleConfiguration.CompanyLogicalPrimaryKey;
import com.csi.itaca.people.api.ErrorConstants;
import com.csi.itaca.people.model.PersonType;
import com.csi.itaca.people.model.dto.PersonTypeDTO;
import com.csi.itaca.people.model.filters.IndividualSearchFilter;
import com.csi.itaca.people.model.filters.PeopleSearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class PeopleManagementBusinessLogicImpl implements PeopleManagementBusinessLogic {

    @Autowired
    private Configurator configurator;

    @Autowired
    private Beaner beaner;

    /**
     * Checks if the supplied filter key is correct based on the configuration.
     * @param filter the filter parameters
     * @param errTracking any errors will be added here.
     * @return true if correct.
     */
    public boolean isLogicalPrimaryKeyCorrect(PeopleSearchFilter filter, Errors errTracking) {

        int initialErrorCount = errTracking.getErrorCount();

        // We require a person type to continue.
        if (filter.getPersonType() == null || filter.getPersonType().getId() == null || filter.getPersonType().getId().isEmpty()) {
            errTracking.rejectValue(PeopleSearchFilter.PERSON_TYPE_FIELD+"."+PersonType.ID_FIELD, ErrorConstants.VALIDATION_REQUIRED_FIELD);
        }

        // Individual person type.
        else if (filter.getPersonType().getId().equals(PersonType.INDIVIDUAL_PERSON_CODE)) {
            IndividualLogicalPrimaryKey individualLogicalPrimaryKeyConfig = configurator
                    .getConfig(PeopleModuleConfiguration.class).getIndividualLogicalPrimaryKey();

            if (individualLogicalPrimaryKeyConfig.getIdentificationCodeInLogicalKey() && (filter.getIdCode() == null || filter.getIdCode().isEmpty())) {
                errTracking.rejectValue(PeopleSearchFilter.ID_CODE_FIELD, ErrorConstants.VALIDATION_REQUIRED_FIELD);
            }

            if (individualLogicalPrimaryKeyConfig.getIdentificationTypeInLogicalKey() && filter.getIdType() == null) {
                errTracking.rejectValue(PeopleSearchFilter.ID_TYPE_FIELD, ErrorConstants.VALIDATION_REQUIRED_FIELD);
            }

            if  (individualLogicalPrimaryKeyConfig.getExternalReferenceCodeInLogicalKey()
                 && (filter.getExternalReference() == null || filter.getExternalReference().isEmpty())) {
                errTracking.rejectValue(PeopleSearchFilter.EXTERNAL_REFERENCE_FIELD, ErrorConstants.VALIDATION_REQUIRED_FIELD);
            }

            if (individualLogicalPrimaryKeyConfig.getDateOfBirthInLogicalKey()
                    && ( !(filter instanceof IndividualSearchFilter)
                    || ((IndividualSearchFilter) filter).getDateOfBirth() == null)) {
                errTracking.reject(ErrorConstants.VALIDATION_DATE_OF_BIRTH_REQUIRED);
            }
        }

        // Company person type.
        else if (filter.getPersonType().getId().equals(PersonType.COMPANY_PERSON_CODE)) {
            CompanyLogicalPrimaryKey companyLogicalPrimaryKeyConfig = configurator
                    .getConfig(PeopleModuleConfiguration.class).getCompanyLogicalPrimaryKey();

            if (companyLogicalPrimaryKeyConfig.getIdentificationCodeInLogicalKey() && (filter.getIdCode() == null || filter.getIdCode().isEmpty())) {
                errTracking.rejectValue(PeopleSearchFilter.ID_CODE_FIELD, ErrorConstants.VALIDATION_REQUIRED_FIELD);

            }

            if (companyLogicalPrimaryKeyConfig.getIdentificationTypeInLogicalKey() && filter.getIdType() == null) {
                errTracking.rejectValue(PeopleSearchFilter.ID_TYPE_FIELD, ErrorConstants.VALIDATION_REQUIRED_FIELD);
            }
        }

        // Person type not valid.
        else {
            errTracking.rejectValue(PersonTypeDTO.ID_FIELD, ErrorConstants.VALIDATION_PERSON_TYPE_INVALID);
        }

        return initialErrorCount != errTracking.getErrorCount();
    }

    public boolean isDuplicatePeopleAllowed() {
        return configurator.getConfig(PeopleModuleConfiguration.class).getDuplicatePeopleAllowed();
    }
}
