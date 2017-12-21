package com.csi.itaca.people.service;

import com.csi.itaca.common.model.dao.CountryEntity;
import com.csi.itaca.people.model.PersonDetail;
import com.csi.itaca.people.repository.*;
import com.csi.itaca.tools.utils.beaner.Beaner;
import com.csi.itaca.people.api.ErrorConstants;

import com.csi.itaca.people.businesslogic.PeopleManagementBusinessLogic;
import com.csi.itaca.people.model.dao.*;
import com.csi.itaca.people.model.dto.*;
import com.csi.itaca.people.model.filters.IndividualSearchFilter;
import com.csi.itaca.people.model.filters.CompanySearchFilter;
import com.csi.itaca.people.model.filters.PeopleSearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PeopleManagementServiceImpl implements PeopleManagementService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PeopleRepository repository;

    @Autowired
    private IndividualRepository individualRepository;

    @Autowired
    private IndividualDetailRepository individualDetailRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyDetailRepository companyDetailRepository;

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

    @Override
    @Transactional
    public PersonDTO saveOrUpdatePerson(PersonDTO dto, Errors errTracking) {

        if (dto.getId() == null
            && !peopleBusinessLogic.isDuplicatePeopleAllowed()
            && doseExternalReferenceAlreadyExist(dto.getExternalReferenceCode())) {
            errTracking.reject(ErrorConstants.DB_DUPLICATE_PERSON_NOT_ALLOWED);
        }
        else {
            if (dto instanceof IndividualDTO) {

                IndividualEntity individualEntity = new IndividualEntity();
                individualEntity.setId(dto.getId());
                individualEntity.setIdentificationCode(dto.getIdentificationCode() != null ? dto.getIdentificationCode().trim().toUpperCase() : dto.getIdentificationCode());
                individualEntity.setExternalReferenceCode(dto.getExternalReferenceCode() != null ? dto.getExternalReferenceCode().trim().toUpperCase() : dto.getExternalReferenceCode());
                individualEntity.setIdType(beaner.transform(dto.getIdType(), IDTypeEntity.class));
                individualEntity.setGender(beaner.transform(((IndividualDTO) dto).getGender(), GenderEntity.class));
                individualEntity.setDateOfBirth(((IndividualDTO) dto).getDateOfBirth());
                individualEntity = individualRepository.save(individualEntity);

                List<IndividualDetailEntity> detailEntities = new ArrayList<>();

                for (PersonDetail detail : ((IndividualDTO) dto).getDetails()) {
                    IndividualDetailDTO individualDetailDTO = (IndividualDetailDTO) detail;
                    IndividualDetailEntity individualDetailEntity = new IndividualDetailEntity();

                    individualDetailEntity.setId(individualDetailDTO.getId());
                    individualDetailEntity.setSurname1(individualDetailDTO.getSurname1() != null ? individualDetailDTO.getSurname1().trim().toUpperCase() : "");
                    individualDetailEntity.setSurname2(individualDetailDTO.getSurname2() != null ? individualDetailDTO.getSurname2().trim().toUpperCase() : "");
                    individualDetailEntity.setName1(individualDetailDTO.getName1() != null ? individualDetailDTO.getName1().trim().toUpperCase() : "");
                    individualDetailEntity.setName2(individualDetailDTO.getName2() != null ? individualDetailDTO.getName2().trim().toUpperCase() : "");
                    individualDetailEntity.setName(individualDetailEntity.getName1() + " " + individualDetailEntity.getName2() + " "+ individualDetailEntity.getSurname1() + " " + individualDetailEntity.getSurname2());
                    individualDetailEntity.setCivilStatus(beaner.transform(individualDetailDTO.getCivilStatus(), CivilStatusEntity.class));
                    individualDetailEntity.setPersonStatus(beaner.transform(individualDetailDTO.getPersonStatus(), PersonStatusEntity.class));
                    individualDetailEntity.setLanguage(beaner.transform(individualDetailDTO.getLanguage(), LanguageEntity.class));
                    individualDetailEntity.setCountry(beaner.transform(individualDetailDTO.getCountry(), CountryEntity.class));

                    individualDetailEntity.setPerson(individualEntity);

                    individualDetailEntity = individualDetailRepository.save(individualDetailEntity);
                    detailEntities.add(individualDetailEntity);
                    individualEntity.setDetails(detailEntities);

                    /*
                    List<RelPersonaMetadataEntity> relacionesEntity = new ArrayList<>();
                    for (RelPersonaMetadata0DTO relacion : individualDetailDTO.getMetadata()) {
                        RelPersonaMetadataEntity relacionEntity;
                        relacionEntity = beaner.transform(relacion, RelPersonaMetadataEntity.class);
                        relacionesEntity.add(relacionEntity);
                    }

                    individualDetailEntity.setMetadata(relacionesEntity);

                    for (RelPersonaMetadataEntity rel : individualDetailEntity.getMetadata()) {
                        rel.setDetallePersona(individualDetailEntity);
                        repositoryValorMetadata.save(rel.getValor());
                        repositoryRelPersonaMetadata.save(rel);
                    }*/


                }

                entityManager.flush();
                entityManager.clear();

                // update id
                dto.setId(individualEntity.getId());

                return dto;

            } else if (dto instanceof CompanyDTO) {

                List<CompanyDetailEntity> detallesEntities = new ArrayList<>();

                CompanyEntity personaJuridicaEntity = new CompanyEntity();
                personaJuridicaEntity.setId(dto.getId());
                personaJuridicaEntity.setIdentificationCode(dto.getIdentificationCode() != null ? dto.getIdentificationCode().trim().toUpperCase() : "");
                personaJuridicaEntity.setExternalReferenceCode(dto.getExternalReferenceCode() != null ? dto.getExternalReferenceCode().trim().toUpperCase() : "");
                personaJuridicaEntity.setIdType(beaner.transform(dto.getIdType(), IDTypeEntity.class));
                personaJuridicaEntity.setStartDate(((CompanyDTO) dto).getStartDate());

                CompanyTypeEntity companyTypeEntity = new CompanyTypeEntity();
                companyTypeEntity.setId(1L);
                companyTypeEntity.setName("companyTypeEntity.personaJuridica");
                personaJuridicaEntity.setCompanyType(companyTypeEntity);

                personaJuridicaEntity = companyRepository.save(personaJuridicaEntity);

                for (PersonDetail detail : ((CompanyDTO) dto).getDetails()) {
                    PersonDetailDTO detalleJuridicaDTO = (PersonDetailDTO) detail;
                    CompanyDetailEntity detalleJuridica = new CompanyDetailEntity();
                    detalleJuridica.setId(detalleJuridicaDTO.getId());
                    detalleJuridica.setName(detalleJuridicaDTO.getName() != null ? detalleJuridicaDTO.getName().trim().toUpperCase() : "");
                    detalleJuridica.setLanguage(beaner.transform(detalleJuridicaDTO.getLanguage(), LanguageEntity.class));
                    detalleJuridica.setCountry(beaner.transform(detalleJuridicaDTO.getCountry(), CountryEntity.class));

                    detalleJuridica.setPerson(personaJuridicaEntity);

                    detalleJuridica = companyDetailRepository.save(detalleJuridica);

                    detallesEntities.add(detalleJuridica);
                    personaJuridicaEntity.setDetails(detallesEntities);

                    /*List<RelPersonaMetadataEntity> relacionesEntity = new ArrayList<>();
                    for (RelPersonaMetadata0DTO relacion : detalleJuridicaDTO.getMetadata()) {
                        RelPersonaMetadataEntity relacionEntity;
                        relacionEntity = beaner.transform(relacion, RelPersonaMetadataEntity.class);
                        relacionesEntity.add(relacionEntity);
                    }

                    detalleJuridica.setMetadata(relacionesEntity);

                    for (RelPersonaMetadataEntity rel : detalleJuridica.getMetadata()) {
                        rel.setDetallePersona(detalleJuridica);
                        repositoryValorMetadata.save(rel.getValor());
                        repositoryRelPersonaMetadata.save(rel);
                    }*/
                }

                entityManager.flush();
                entityManager.clear();

                // update id
                dto.setId(personaJuridicaEntity.getId());

                return dto;
            }
        }

        return null;
    }

    @Override
    @Transactional
    public void deletePerson(Long personId, Errors errTracking) {
        PersonEntity personToDelete = getPersonEntity(personId, errTracking);
        repository.delete(personToDelete);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean doseExternalReferenceAlreadyExist(String externalReferenceCode) {

        Specification<IndividualEntity> spec = (root, query, cb) -> {
            return cb.and(cb.equal(root.get(PersonEntity.EXTERNAL_REFERENCE_CODE), externalReferenceCode));
        };

        List<IndividualEntity> result = individualRepository.findAll(spec);
        return result != null && !result.isEmpty();
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

            return individualRepository.findAll(spec);

        } else if (parameters.getPersonType().getId().equals(PersonTypeDTO.COMPANY_PERSON_CODE)) {

            Specification<CompanyEntity> spec = (root, query, cb) -> {
                Predicate p = cb.and(cb.equal(root.type(), CompanyEntity.class));
                return applyCommonFilters(root, p, cb, parameters, "");
            };

            return companyRepository.findAll(spec);
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
