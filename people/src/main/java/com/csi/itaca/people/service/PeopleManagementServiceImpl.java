package com.csi.itaca.people.service;

import com.csi.itaca.common.utils.beaner.Beaner;
import com.csi.itaca.people.api.ErrorConstants;

import com.csi.itaca.people.businesslogic.PeopleManagementBusinessLogic;
import com.csi.itaca.people.model.dao.*;
import com.csi.itaca.people.model.dto.CompanyDTO;
import com.csi.itaca.people.model.dto.IndividualDTO;
import com.csi.itaca.people.model.dto.PersonDTO;
import com.csi.itaca.people.model.dto.PersonTypeDTO;
import com.csi.itaca.people.model.filters.IndividualSearchFilter;
import com.csi.itaca.people.model.filters.CompanySearchFilter;
import com.csi.itaca.people.model.filters.PeopleSearchFilter;
import com.csi.itaca.people.repository.IndividualPeopleRepository;
import com.csi.itaca.people.repository.LegalPeopleRepository;
import com.csi.itaca.people.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PeopleManagementServiceImpl implements PeopleManagementService {

    @Autowired
    private PeopleRepository repository;

    @Autowired
    private IndividualPeopleRepository individualPeopleRepository;

    @Autowired
    private LegalPeopleRepository legalPeopleRepository;

    @Autowired
    private Beaner beaner;

    @Autowired
    private PeopleManagementBusinessLogic peopleBusinessLogic;


    @Override
    public PersonDTO getPerson(Long id, Errors errTracking) {
        PersonEntity person = getPersonEntity(id, errTracking);

        if (person instanceof IndividualEntity) {
            return beaner.transform(person, IndividualDTO.class);
        }
        if (person instanceof CompanyEntity) {
            return beaner.transform(person, CompanyDTO.class);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<? extends PersonDTO> findPeople(PeopleSearchFilter filter, Errors errTracking) {

        // We require a person type to continue.
        if (!peopleBusinessLogic.isLogicalPrimaryKeyCorrect(filter, errTracking)) {

            List<? extends PersonEntity> peopleFound = listPeople(filter);

            // Do we have any people?
            if (peopleFound.isEmpty()) {
                errTracking.reject(ErrorConstants.DB_PERSON_NOT_FOUND);
            }
            else {
                // Check if duplicates are allowed
                if (!peopleBusinessLogic.isDuplicatePeopleAllowed() && peopleFound.size() > 1) {
                    errTracking.reject(ErrorConstants.DB_DUPLICATE_PERSON_NOT_ALLOWED);
                }

                // Details person details should not be empty.
                if (peopleFound.get(0).getDetails() == null || peopleFound.get(0).getDetails().isEmpty()) {
                    errTracking.reject(ErrorConstants.DB_PERSON_WITH_NO_DETAILS);
                }

                // Convert to DTOs and return.
                if (filter.getPersonType().getId().equals(PersonTypeDTO.INDIVIDUAL_PERSON_CODE)) {
                    return beaner.transform(peopleFound, IndividualDTO.class);
                } else {
                    return beaner.transform(peopleFound, CompanyDTO.class);
                }
            }
        }


        return Collections.emptyList();
    }

    /**
     * Gets a person entity.
     * @param id the person ID.
     * @param errTracking error tracking object.
     * @return this person entity if found otherwise null.
     */
    @Transactional(readOnly = true)
    private PersonEntity getPersonEntity(Long id, Errors errTracking) {
        PersonEntity person = repository.findOne(id);
        if (person == null && errTracking != null) {
            errTracking.reject(ErrorConstants.DB_PERSON_NOT_FOUND);
        }
        return person;
    }

    @Transactional(readOnly = true)
    private List<? extends PersonEntity> listPeople(PeopleSearchFilter parameters) {

        if (parameters.getPersonType().getId().equals(PersonTypeDTO.INDIVIDUAL_PERSON_CODE)) {

            Specification<IndividualEntity> spec = (root, query, cb) -> {
                Predicate p = cb.and(cb.equal(root.type(), IndividualEntity.class));
                return applyCommonFilters(root, p, cb, parameters, "");
            };

            return individualPeopleRepository.findAll(spec);

        } else if (parameters.getPersonType().getId().equals(PersonTypeDTO.COMPANY_PERSON_CODE)) {

            Specification<CompanyEntity> spec = (root, query, cb) -> {
                Predicate p = cb.and(cb.equal(root.type(), CompanyEntity.class));
                return applyCommonFilters(root, p, cb, parameters, "");
            };

            return legalPeopleRepository.findAll(spec);
        }
        return new ArrayList<>();
    }

    private Predicate applyCommonFilters(Root<?> root, Predicate p, CriteriaBuilder cb,
                                         PeopleSearchFilter parameters, String path) {
        if (parameters.getIdCode() != null && !parameters.getIdCode().isEmpty()) {
            if (path.isEmpty())
                p = cb.and(p,
                        cb.equal(root.get(PersonEntity.ID_CODE), parameters.getIdCode()));
            else
                p = cb.and(p, cb.equal(root.get(path).get(PersonEntity.ID_CODE),
                        parameters.getIdCode()));
        }
        if (parameters.getIdType() != null) {
            if (path.isEmpty())
                p = cb.and(p, cb.equal(root.get(PersonEntity.ID_TYPE).get(IDTypeEntity.ID),
                        parameters.getIdType().getId()));
            else
                p = cb.and(p,
                        cb.equal(root.get(path).get(PersonEntity.ID_TYPE).get(IDTypeEntity.ID),
                                parameters.getIdType().getId()));
        }
        if (parameters.getPersonType().getId().equals(PersonTypeDTO.INDIVIDUAL_PERSON_CODE)) {



            if (parameters.getExternalReference() != null
                    && !parameters.getExternalReference().isEmpty()) {
                if (path.isEmpty())
                    p = cb.and(p, cb.equal(root.get(PersonEntity.EXTERNAL_REFERENCE_CODE),
                            parameters.getExternalReference()));
                else
                    p = cb.and(p, cb.equal(root.get(path).get(PersonEntity.EXTERNAL_REFERENCE_CODE),
                            parameters.getExternalReference()));
            }

            if (parameters instanceof IndividualSearchFilter) {
                IndividualSearchFilter individualSearchFilter = (IndividualSearchFilter) parameters;

                if (individualSearchFilter.getDateOfBirth() != null &&path.isEmpty()) {
                    p = cb.and(p, cb.equal(root.get(IndividualEntity.DATE_OF_BIRTH),
                            individualSearchFilter.getDateOfBirth()));
                }
            }
        } else if (parameters.getPersonType().getId().equals(PersonTypeDTO.COMPANY_PERSON_CODE)) {
            if (parameters instanceof CompanySearchFilter) {
                CompanySearchFilter companySearchFilter = (CompanySearchFilter) parameters;
                if (companySearchFilter.getStartDate() != null) {
                    if (path.isEmpty()) {
                        p = cb.and(p, cb.equal(root.get(CompanyEntity.START_DATE),
                                companySearchFilter.getStartDate()));
                    }
                }
            }
        }

        return p;
    }
}
