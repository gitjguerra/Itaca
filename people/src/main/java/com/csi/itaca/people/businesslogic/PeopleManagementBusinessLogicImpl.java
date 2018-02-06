package com.csi.itaca.people.businesslogic;

import com.csi.itaca.people.model.Identification;
import com.csi.itaca.people.model.dto.IdentificationDTO;
import com.csi.itaca.people.service.PeopleIdentificationService;
import com.csi.itaca.tools.utils.beaner.Beaner;
import com.csi.itaca.config.model.Configurator;
import com.csi.itaca.people.api.PeopleModuleConfiguration;
import com.csi.itaca.people.api.PeopleModuleConfiguration.IndividualLogicalPrimaryKey;
import com.csi.itaca.people.api.PeopleModuleConfiguration.CompanyLogicalPrimaryKey;
import com.csi.itaca.people.api.ErrorConstants;
import com.csi.itaca.people.model.PersonType;
import com.csi.itaca.people.model.dto.PersonTypeDTO;
import com.csi.itaca.people.model.filters.IndividualSearchFilter;
import com.csi.itaca.people.model.filters.PeopleSearchFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.List;

@Service
public class PeopleManagementBusinessLogicImpl implements PeopleManagementBusinessLogic {

    @Autowired
    private Configurator configurator;

    @Autowired
    private Beaner beaner;

    @Autowired
    private PeopleIdentificationService peopleIdentificationService;


    /**
     * @see PeopleManagementBusinessLogic#isLogicalPrimaryKeyCorrect(PeopleSearchFilter, Errors)
     */
    public boolean isLogicalPrimaryKeyCorrect(PeopleSearchFilter filter, Errors errTracking) {

        int initialErrorCount = errTracking.getErrorCount();

        if (filter!=null) {
            // We require a person type to continue.
            if (filter.getPersonType() == null || StringUtils.isEmpty(filter.getPersonType().getId())) {
                errTracking.rejectValue(PeopleSearchFilter.PERSON_TYPE_FIELD + "." + PersonType.ID_FIELD, ErrorConstants.VALIDATION_REQUIRED_FIELD);
            }

            // Individual person type.
            else if (filter.getPersonType().getId().equals(PersonType.INDIVIDUAL_PERSON_CODE)) {
                IndividualLogicalPrimaryKey individualLogicalPrimaryKeyConfig = configurator
                        .getConfig(PeopleModuleConfiguration.class).getIndividualLogicalPrimaryKey();

                if (individualLogicalPrimaryKeyConfig.getIdentificationCodeInLogicalKey() &&  StringUtils.isEmpty(filter.getIdCode())) {
                    errTracking.rejectValue(PeopleSearchFilter.ID_CODE_FIELD, ErrorConstants.VALIDATION_REQUIRED_FIELD);
                }

                if (individualLogicalPrimaryKeyConfig.getIdentificationTypeInLogicalKey() && filter.getIdType() == null) {
                    errTracking.rejectValue(PeopleSearchFilter.ID_TYPE_FIELD, ErrorConstants.VALIDATION_REQUIRED_FIELD);
                }

                if (individualLogicalPrimaryKeyConfig.getExternalReferenceCodeInLogicalKey()
                        && StringUtils.isEmpty(filter.getExternalReference())) {
                    errTracking.rejectValue(PeopleSearchFilter.EXTERNAL_REFERENCE_FIELD, ErrorConstants.VALIDATION_REQUIRED_FIELD);
                }

                if (individualLogicalPrimaryKeyConfig.getDateOfBirthInLogicalKey()
                        && (!(filter instanceof IndividualSearchFilter)
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
        }
        else {
            errTracking.reject(ErrorConstants.VALIDATION_NO_FILTER_PROVIDED);
        }

        return initialErrorCount != errTracking.getErrorCount();
    }

    /**
     * @see PeopleManagementBusinessLogic#isDuplicatePeopleAllowed()
     */

    public boolean isDuplicatePeopleAllowed() {
        return configurator.getConfig(PeopleModuleConfiguration.class).getDuplicatePeopleAllowed();
    }

    /**
     * @see PeopleManagementBusinessLogic#isDuplicateIdentification(Identification, Errors)
     */
    public boolean isDuplicateIdentification(Identification identification, Errors errTracking) {

        List<IdentificationDTO> identifications =
                peopleIdentificationService.listIdentifications(identification.getPersonDetailId());

        for (IdentificationDTO entity : identifications) {

            if (entity.getIdType().getId().equals(identification.getIdType().getId())
                && entity.getIdentificationCode().equals(identification.getIdentificationCode())
                && entity.getCountry().getId().equals(identification.getCountry().getId())
                && entity.getPersonDetailId().equals(identification.getPersonDetailId())
                && (!(entity.getId().equals(identification.getId()))) ) {

                errTracking.reject(ErrorConstants.DB_DUPLICATE_IDENTIFICATION);
                return true;
            }
        }

        return false;
    }
}
