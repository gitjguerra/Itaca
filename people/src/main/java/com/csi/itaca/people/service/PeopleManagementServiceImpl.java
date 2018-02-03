package com.csi.itaca.people.service;

import com.csi.itaca.common.model.dao.CountryEntity;
import com.csi.itaca.people.endpoint.PeopleManagementRestController;
import com.csi.itaca.people.model.BankCard;
import com.csi.itaca.people.model.CardType;
import com.csi.itaca.people.model.PersonDetail;
import com.csi.itaca.people.model.PersonType;
import com.csi.itaca.people.model.filters.*;
import com.csi.itaca.people.repository.*;
import com.csi.itaca.tools.utils.beaner.Beaner;
import com.csi.itaca.people.api.ErrorConstants;

import com.csi.itaca.people.businesslogic.PeopleManagementBusinessLogic;
import com.csi.itaca.people.model.dao.*;
import com.csi.itaca.people.model.dto.*;
import com.csi.itaca.tools.utils.jpa.JpaUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
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

import static com.csi.itaca.people.repository.AccountRepository.*;

@Service
public class PeopleManagementServiceImpl implements PeopleManagementService {

    /** Logger */
    private static Logger logger = Logger.getLogger(PeopleManagementRestController.class);

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
    private PersonDetailRepository personDetailRepository;

    @Autowired
    private CardTypeRepository cardTypeRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BankCardRepository bankCardRepository;

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

        if (!peopleBusinessLogic.isLogicalPrimaryKeyCorrect(filter, errTracking)) {

            List<? extends PersonEntity> peopleFound = listPeople(filter);

            // Do we have any people?
            if (peopleFound.isEmpty()) {
                errTracking.reject(ErrorConstants.DB_ITEM_NOT_FOUND);
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

    @Override
    @Transactional(readOnly = true)
    public List<? extends PersonDetailDTO> findPersonDetails(PeopleSearchFilter criteria, Errors errTracking) {

        List<? extends PersonDetailDTO> personDetails = Collections.EMPTY_LIST;

        if (criteria!=null) {
            PageRequest pr = null;
            if (criteria.getPagination() != null) {
                pr = new PageRequest(criteria.getPagination().getPageNo() - 1, criteria.getPagination().getItemsPerPage());
            }

            if (criteria.getPersonType() == null || criteria.getPersonType().getId().isEmpty()) {
                Specification<PersonDetailEntity> spec = (root, query, cb) -> applyCommonDetailFilter(null, cb, root, criteria);

                spec = JpaUtils.applyOrder(PersonDetailEntity.class, criteria.getOrder(), spec);
                personDetails = beaner.transform(personDetailRepository.findAll(spec, pr).getContent(), PersonDetailDTO.class);

            } else if (PersonType.INDIVIDUAL_PERSON_CODE.equals(criteria.getPersonType().getId())) {
                Specification<IndividualDetailEntity> spec = (root, query, cb) -> applyIndividualDetailFilter(cb, root, criteria);

                spec = JpaUtils.applyOrder(IndividualDetailEntity.class, criteria.getOrder(), spec);
                personDetails = beaner.transform(individualDetailRepository.findAll(spec, pr).getContent(), IndividualDetailDTO.class);

            } else {
                Specification<CompanyDetailEntity> spec = (root, query, cb) -> applyCompanyDetailFilter(cb, root, criteria);

                spec = JpaUtils.applyOrder(CompanyDetailEntity.class, criteria.getOrder(), spec);
                personDetails = beaner.transform(companyDetailRepository.findAll(spec, pr).getContent(), CompanyDetailDTO.class);
            }
        }
        else {
            errTracking.reject(ErrorConstants.VALIDATION_NO_FILTER_PROVIDED);
        }
        return personDetails;
    }

    @Override
    @Transactional(readOnly = true)
    public Long countPersonDetails(PeopleSearchFilter filter) {

        if (filter.getPersonType() == null || filter.getPersonType().getId().isEmpty()) {
            Specification<PersonDetailEntity> spec = (root, query, cb) -> {
                return applyCommonDetailFilter(null, cb, root, filter);
            };
            return personDetailRepository.count(spec);

        } else if (PersonType.INDIVIDUAL_PERSON_CODE.equals(filter.getPersonType().getId())) {
            Specification<IndividualDetailEntity> spec = (root, query, cb) -> {
                return applyIndividualDetailFilter(cb, root, filter);
            };
            return individualDetailRepository.count(spec);

        } else {
            Specification<CompanyDetailEntity> spec = (root, query, cb) -> {
                return applyCompanyDetailFilter(cb, root, filter);
            };
            return companyDetailRepository.count(spec);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<? extends PersonDetailDTO> findDuplicatePersonDetails(PeopleSearchFilter criteria, Errors errTracking) {

        List<? extends PersonDetailDTO> personDetails = Collections.EMPTY_LIST;

        if (!peopleBusinessLogic.isLogicalPrimaryKeyCorrect(criteria, errTracking)) {

            PageRequest pr = null;
            if (criteria.getPagination() != null) {
                pr = new PageRequest(criteria.getPagination().getPageNo() - 1, criteria.getPagination().getItemsPerPage());
            }

            if (criteria.getPersonType().getId().equals(PersonType.INDIVIDUAL_PERSON_CODE)) {

                Specification<IndividualDetailEntity> spec = buildDuplicateDetailsSpecForIndividual(criteria);
                spec = JpaUtils.applyOrder(IndividualDetailEntity.class, criteria.getOrder(), spec);

                if (pr != null) {
                    personDetails = beaner.transform(individualDetailRepository.findAll(spec, pr).getContent(), IndividualDetailDTO.class);
                } else {
                    personDetails = beaner.transform(individualDetailRepository.findAll(spec), IndividualDetailDTO.class);
                }

            } else if (criteria.getPersonType().getId().equals(PersonType.COMPANY_PERSON_CODE)) {

                Specification<CompanyDetailEntity> spec = buildDuplicateDetailsSpecForCompany(criteria);
                spec = JpaUtils.applyOrder(CompanyDetailEntity.class, criteria.getOrder(), spec);

                if (pr != null) {
                    personDetails = beaner.transform(companyDetailRepository.findAll(spec, pr).getContent(), CompanyDetailDTO.class);
                } else {
                    personDetails = beaner.transform(companyDetailRepository.findAll(spec), CompanyDetailDTO.class);
                }

            }

        }

        return personDetails;
    }

    @Override
    @Transactional(readOnly = true)
    public Long countDuplicatePersonDetails(PeopleSearchFilter filter) {

        if (filter.getPersonType().getId().equals(PersonType.INDIVIDUAL_PERSON_CODE)) {
            Specification<IndividualDetailEntity> spec = buildDuplicateDetailsSpecForIndividual(filter);
            return individualDetailRepository.count(spec);

        } else if (filter.getPersonType().getId().equals(PersonType.COMPANY_PERSON_CODE)) {
            Specification<CompanyDetailEntity> spec = buildDuplicateDetailsSpecForCompany(filter);
            return companyDetailRepository.count(spec);
        }

        return 0L;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDetailDTO getPersonDetail(Long personDetailId, Errors errTracking) {
        PersonDetailEntity personDetailEntity = personDetailRepository.findOne(personDetailId);

        PersonDetailDTO retPersonDetail = null;
        if (personDetailEntity!=null) {
            if (personDetailEntity instanceof IndividualDetailEntity) {
                retPersonDetail = beaner.transform(personDetailEntity, IndividualDetailDTO.class);
            } else {
                retPersonDetail = beaner.transform(personDetailEntity, CompanyDetailDTO.class);
            }
        }
        else {
            errTracking.reject(ErrorConstants.DB_ITEM_NOT_FOUND);
        }

        return retPersonDetail;
    }

    /**
     * Gets a person entity.
     * @param id the person ID.
     * @param errTracking error tracking object.
     * @return this person entity if found otherwise null.
     */
    @Transactional(readOnly = true)
    PersonEntity getPersonEntity(Long id, Errors errTracking) {
        PersonEntity person = repository.findOne(id);
        if (person == null && errTracking != null) {
            errTracking.reject(ErrorConstants.DB_ITEM_NOT_FOUND);
        }
        return person;
    }

    @Transactional(readOnly = true)
    List<? extends PersonEntity> listPeople(PeopleSearchFilter parameters) {

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

    /**
     * Builds a specification that will produce a list of individual detail items.
     * @param filter the filter in which the specification will be based on.
     * @return the constructed specification.
     */
    private Specification<IndividualDetailEntity> buildDuplicateDetailsSpecForIndividual(PeopleSearchFilter filter) {

        Specification<IndividualDetailEntity> spec = (root, query, cb) -> {
            Predicate p = cb.and(cb.equal(root.type(), IndividualDetailEntity.class));
            return applyCommonFilters(root, p, cb, filter, IndividualDetailEntity.PERSON);
        };

        IndividualSearchFilter individualSearchFilter = (IndividualSearchFilter) filter;
        if (individualSearchFilter.getDateOfBirth() != null) {
            spec = Specifications.where(spec).and((root, query, cb) -> {
                return cb.equal(
                        cb.treat(root.join(IndividualDetailEntity.PERSON), IndividualEntity.class)
                                .get(IndividualEntity.DATE_OF_BIRTH),
                        individualSearchFilter.getDateOfBirth());
            });
        }
        return spec;
    }

    /**
     * Builds a specification that will produce a list of company detail items.
     * @param filter the filter in which the specification will be based on.
     * @return the constructed specification.
     */
    private Specification<CompanyDetailEntity> buildDuplicateDetailsSpecForCompany(PeopleSearchFilter filter) {
        Specification<CompanyDetailEntity> spec = (root, query, cb) -> {
            Predicate p = cb.and(cb.equal(root.type(), CompanyDetailEntity.class));
            return applyCommonFilters(root, p, cb, filter, CompanyDetailEntity.PERSON);
        };

        CompanySearchFilter companySearchFilter = (CompanySearchFilter) filter;
        if (companySearchFilter.getStartDate() != null) {
            spec = Specifications.where(spec).and((root, query, cb) -> {
                return cb.equal(
                        cb.treat(root.join(CompanyDetailEntity.PERSON), CountryEntity.class)
                                .get(CompanyEntity.START_DATE),
                        companySearchFilter.getStartDate());
            });
        }

        return spec;
    }

    private Predicate applyIndividualDetailFilter(CriteriaBuilder cb, Root<? extends PersonDetailEntity> r,
                                                  PeopleSearchFilter parameters) {

        Predicate p = cb.and(cb.equal(r.type(), IndividualDetailEntity.class));
        p = applyCommonDetailFilter(p, cb, r, parameters);

        IndividualSearchFilter individualSearchFilter = (IndividualSearchFilter) parameters;

        if (StringUtils.isNotEmpty(individualSearchFilter.getName1())) {
            p = applyLikeLowerFilter(cb, r, IndividualDetailEntity.NAME1, individualSearchFilter.getName1(), p);
        }

        if (StringUtils.isNotEmpty(individualSearchFilter.getName2())) {
            p = applyLikeLowerFilter(cb, r, IndividualDetailEntity.NAME2, individualSearchFilter.getName2(), p);
        }

        if (StringUtils.isNotEmpty(individualSearchFilter.getSurname1())) {
            p = applyLikeLowerFilter(cb, r, IndividualDetailEntity.SURNAME1, individualSearchFilter.getSurname1(), p);
        }

        if (StringUtils.isNotEmpty(individualSearchFilter.getSurname2())) {
            p = applyLikeLowerFilter(cb, r, IndividualDetailEntity.SURNAME2, individualSearchFilter.getSurname2(), p);
        }
        return p;
    }

    private Predicate applyCompanyDetailFilter(CriteriaBuilder cb, Root<? extends PersonDetailEntity> r,
                                               PeopleSearchFilter filter) {
        Predicate p = cb.and(cb.equal(r.type(), CompanyDetailEntity.class));
        p = applyCommonDetailFilter(p, cb, r, filter);

        return p;
    }

    private Predicate applyCommonDetailFilter(Predicate p, CriteriaBuilder cb,
                                              Root<? extends PersonDetailEntity> r,
                                              PeopleSearchFilter filter) {
        if (filter.getIdType() != null) {
            p = applyLikeLowerFilter(cb, r,PersonDetailEntity.PERSON+ "." + PersonEntity.ID_TYPE + "."
                            + IDTypeEntity.ID, String.valueOf(filter.getIdType().getId()), p, 3);
        }
        if (StringUtils.isNotEmpty(filter.getIdCode())) {
            p = applyLikeLowerFilter(cb, r, PersonDetailEntity.PERSON + "." + PersonEntity.ID_CODE,
                                    filter.getIdCode(), p, 2);
        }
        if (StringUtils.isNotEmpty(filter.getExternalReference())) {
            p = applyLikeLowerFilter(cb, r, PersonDetailEntity.PERSON + "." + PersonEntity.EXTERNAL_REFERENCE_CODE,
                    filter.getExternalReference(), p, 2);
        }

        if (StringUtils.isNotEmpty(filter.getName())) {
            p = applyLikeLowerFilter(cb, r, PersonDetailEntity.NAME, filter.getName(), p);
        }
        return p;
    }

    private Predicate applyLikeLowerFilter(CriteriaBuilder cb, Root<? extends PersonDetailEntity> r, String field,
                                           String value, Predicate p) {

        return applyLikeLowerFilter(cb, r, field, value, p,1);
    }

    private Predicate applyLikeLowerFilter(CriteriaBuilder cb, Root<? extends PersonDetailEntity> r, String field,
                                           String value, Predicate p, int fieldDepth) {

        value = value.trim().toLowerCase();

        if (fieldDepth == 1) {
            p = p == null ? p = cb.like(cb.lower(r.get(field)), "%" + value + "%")
                    : cb.and(p, cb.like(cb.lower(r.get(field)), "%" + value + "%"));
        } else if (fieldDepth > 1 && fieldDepth < 7) {
            String fields[] = field.split("[.]", -1);
            if (fieldDepth == 2) {
                p = p == null ? p = cb.like(cb.lower(r.get(fields[0]).get(fields[1])), "%" + value + "%")
                        : cb.and(p,cb.like(cb.lower(r.get(fields[0]).get(fields[1])), "%" + value + "%"));
            } else if (fieldDepth == 3) {
                p = p == null
                        ? p = cb.like(cb.lower(r.get(fields[0]).get(fields[1]).get(fields[2])),"%" + value + "%")
                        : cb.and(p, cb.like(cb.lower(r.get(fields[0]).get(fields[1]).get(fields[2])),"%" + value + "%"));
            } else if (fieldDepth == 4) {
                p = p == null
                        ? p = cb.like(cb.lower(r.get(fields[0]).get(fields[1]).get(fields[2]).get(fields[3])),"%" + value + "%")
                        : cb.and(p, cb.like(cb.lower(r.get(fields[0]).get(fields[1]).get(fields[2]).get(fields[3])),"%" + value + "%"));
            } else if (fieldDepth == 5) {
                p = p == null
                        ? p = cb.like(cb.lower(r.get(fields[0]).get(fields[1]).get(fields[2]).get(fields[3]).get(fields[4])),"%" + value + "%")
                        : cb.and(p,cb.like(cb.lower(r.get(fields[0]).get(fields[1]).get(fields[2]).get(fields[3]).get(fields[4])),"%" + value + "%"));
            } else if (fieldDepth == 6) {
                p = p == null
                        ? p = cb.like(cb.lower(r.get(fields[0]).get(fields[1]).get(fields[2]).get(fields[3]).get(fields[4]).get(fields[5])), "%" + value + "%")
                        : cb.and(p, cb.like(cb.lower(r.get(fields[0]).get(fields[1]).get(fields[2]).get(fields[3]).get(fields[4]).get(fields[5])), "%" + value + "%"));
            }
        }
        return p;
    }

    private Predicate applyCommonFilters(Root<?> root, Predicate p, CriteriaBuilder cb,
                                         PeopleSearchFilter filter, String path) {

        if (filter.getIdCode() != null && !filter.getIdCode().isEmpty()) {
            if (path.isEmpty())
                p = cb.and(p,
                        cb.equal(root.get(PersonEntity.ID_CODE), filter.getIdCode()));
            else
                p = cb.and(p, cb.equal(root.get(path).get(PersonEntity.ID_CODE),
                        filter.getIdCode()));
        }

        if (filter.getIdType() != null) {
            if (path.isEmpty())
                p = cb.and(p, cb.equal(root.get(PersonEntity.ID_TYPE).get(IDTypeEntity.ID),
                        filter.getIdType().getId()));
            else
                p = cb.and(p,
                        cb.equal(root.get(path).get(PersonEntity.ID_TYPE).get(IDTypeEntity.ID),
                                filter.getIdType().getId()));
        }

        if (filter.getPersonType().getId().equals(PersonTypeDTO.INDIVIDUAL_PERSON_CODE)) {

            if (filter.getExternalReference() != null
                    && !filter.getExternalReference().isEmpty()) {
                if (path.isEmpty())
                    p = cb.and(p, cb.equal(root.get(PersonEntity.EXTERNAL_REFERENCE_CODE),
                            filter.getExternalReference()));
                else
                    p = cb.and(p, cb.equal(root.get(path).get(PersonEntity.EXTERNAL_REFERENCE_CODE),
                            filter.getExternalReference()));
            }

            if (filter instanceof IndividualSearchFilter) {
                IndividualSearchFilter individualSearchFilter = (IndividualSearchFilter) filter;

                if (individualSearchFilter.getDateOfBirth() != null &&path.isEmpty()) {
                    p = cb.and(p, cb.equal(root.get(IndividualEntity.DATE_OF_BIRTH),
                            individualSearchFilter.getDateOfBirth()));
                }
            }
        } else if (filter.getPersonType().getId().equals(PersonTypeDTO.COMPANY_PERSON_CODE)) {
            if (filter instanceof CompanySearchFilter) {
                CompanySearchFilter companySearchFilter = (CompanySearchFilter) filter;
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

    private Predicate applyLikeLowerAccountFilter(CriteriaBuilder cb, Root<? extends AccountEntity> r, String field,
                                                  String value, Predicate p, int fieldDepth) {

        value = value.trim().toLowerCase();

        if (fieldDepth == 1) {
            p = p == null ? p = cb.like(cb.lower(r.get(field)), "%" + value + "%")
                    : cb.and(p, cb.like(cb.lower(r.get(field)), "%" + value + "%"));
        } else if (fieldDepth > 1 && fieldDepth < 7) {
            String fields[] = field.split("[.]", -1);
            if (fieldDepth == 2) {
                p = p == null ? p = cb.like(cb.lower(r.get(fields[0]).get(fields[1])), "%" + value + "%")
                        : cb.and(p,cb.like(cb.lower(r.get(fields[0]).get(fields[1])), "%" + value + "%"));
            }
        }
        return p;
    }
    private Predicate applyAccountFilter(Predicate p, CriteriaBuilder cb,
                                         Root<? extends AccountEntity> r,
                                         AccountSearchFilter filter) {
        if (filter.getId() != null) {
            p = applyLikeLowerAccountFilter(cb, r,AccountEntity.ID+ ".", String.valueOf(filter.getId()), p, 1);
        }
        if (StringUtils.isNotEmpty(filter.getNumber())) {
            p = applyLikeLowerAccountFilter(cb, r, AccountEntity.ACCOUNT, filter.getNumber(), p,2);
        }
        return p;
    }

    private Predicate applyLikeLowerBankCardFilter(CriteriaBuilder cb, Root<? extends BankCardEntity> r, String field,
                                                  String value, Predicate p, int fieldDepth) {

        value = value.trim().toLowerCase();

        if (fieldDepth == 1) {
            p = p == null ? p = cb.like(cb.lower(r.get(field)), "%" + value + "%")
                    : cb.and(p, cb.like(cb.lower(r.get(field)), "%" + value + "%"));
        } else if (fieldDepth > 1 && fieldDepth < 7) {
            String fields[] = field.split("[.]", -1);
            if (fieldDepth == 2) {
                p = p == null ? p = cb.like(cb.lower(r.get(fields[0]).get(fields[1])), "%" + value + "%")
                        : cb.and(p,cb.like(cb.lower(r.get(fields[0]).get(fields[1])), "%" + value + "%"));
            }
        }
        return p;
    }
    private Predicate applyBankCardFilter(Predicate p, CriteriaBuilder cb,
                                         Root<? extends BankCardEntity> r,
                                          BankCardSearchFilter filter) {
        if (filter.getId() != null) {
            p = applyLikeLowerBankCardFilter(cb, r,BankCardEntity.ID_BANK_CARD  + ".", String.valueOf(filter.getId()), p, 1);
        }
        if (StringUtils.isNotEmpty(filter.getCardType().getLiteral())) {
            p = applyLikeLowerBankCardFilter(cb, r, BankCardEntity.CARD, filter.getLiteral(), p,2);
        }
        return p;
    }

    // TODO: (Jose Guerra) CountBankCards method
    @Override
    @Transactional(readOnly = true)
    public Long countBankCards(BankCardSearchFilter filter) {

        if (filter.getId() == null) {
            Specification<BankCardEntity> spec = (root, query, cb) -> {
                return applyBankCardFilter(null, cb, root, filter);
            };
            return bankCardRepository.count(spec);

        } else if (BankCardEntity.ID_BANK_CARD.equals(filter.getId())) {
            Specification<BankCardEntity> spec = (root, query, cb) -> {
                return applyBankCardFilter(null, cb, root, filter);
            };
            return bankCardRepository.count(spec);
        }
        return null;
    }

    //TODO: (Jose Guerra) Save Or Update Account
    @Override
    @Transactional
    public AccountDTO saveOrUpdateAccount(AccountDTO dto, Errors errTracking) {

        logger.info("******************** SAVEorUPDATE ACCOUNT PROCESS *************************");

        AccountEntity accountEntity = accountRepository.findOne(dto.getId());

        logger.info("count:"+accountRepository);
        accountEntity.setId(dto.getId());
        logger.info("********** hey ******************");
        logger.info("account:"+dto.getAccount());
        accountEntity.setAccount(dto.getAccount());
        logger.info("Persondetail:"+dto.getPersonDetail().getId());
        accountEntity.setPersondetail(beaner.transform(dto.getPersonDetail().getId(), PersonDetailEntity.class));
        logger.info("Accountclasification:"+dto.getAccountClasification().getId());
        accountEntity.setAccountclasification(beaner.transform(dto.getAccountClasification().getId(), AccountClasificationEntity.class));
        logger.info("Accounttype:"+dto.getAccountType().getId());
        accountEntity.setAccounttype(beaner.transform(dto.getAccountType().getId(), AccountTypeEntity.class));
        logger.info("Available:"+dto.getAvailable());
        accountEntity.setAvailable(dto.getAvailable());
        logger.info("Principal:"+dto.getPrincipal());
        accountEntity.setPrincipal(dto.getPrincipal());
        logger.info("Bank:"+dto.getBank().getId());
        accountEntity.setBank(beaner.transform(dto.getBank().getId(), BankEntity.class));

        accountEntity = accountRepository.save(accountEntity);

        entityManager.flush();
        entityManager.clear();

        // update id
        dto.setId(accountEntity.getId());

        logger.info("******************** Ready  ACCOUNT return DTO *************************");

        return dto;

    }

    // TODO: (Jose Guerra) getAccount method
    //    @Override
    public AccountDTO getAccount(Long id, Errors errTracking) {

        AccountEntity accountEntity = accountRepository.findOne(id);
        AccountDTO account = null;
        if (accountEntity!=null) {
            return beaner.transform(accountEntity, AccountDTO.class);
        }
        return account;
    }

    //TODO: (Jose Guerra) Save Or Update Bank Card
    @Override
    @Transactional
    public BankCardDTO saveOrUpdateBankCard(BankCardDTO dto, Errors errTracking) {

        BankCardEntity bankCardEntity = bankCardRepository.findOne(dto.getId());

        bankCardEntity.setId(dto.getId());
        bankCardEntity.setAvailable(dto.getAvailable());
        bankCardEntity.setBank(beaner.transform(dto.getBank().getId(), BankEntity.class));
        bankCardEntity.setCard(dto.getCard());
        bankCardEntity.setCardType(beaner.transform(dto.getCardType().getId(), CardTypeEntity.class));
        bankCardEntity.setExpirationDate(dto.getExpirationDate());
        bankCardEntity.setPersonDetail(beaner.transform(dto.getPersonDetail().getId(), PersonDetailEntity.class));
        bankCardEntity.setPrincipal(dto.getPrincipal());
        bankCardEntity.setSecurityCode(dto.getSecurityCode());
        bankCardEntity = bankCardRepository.save(bankCardEntity);

        entityManager.flush();
        entityManager.clear();

        // update id
        dto.setId(bankCardEntity.getId());

        return dto;

    }

    // TODO: (Jose Guerra) getBankCard method
    //    @Override
    public BankCardDTO getBankCard(Long id, Errors errTracking) {

        BankCardEntity bankCardEntity = bankCardRepository.findOne(id);
        BankCardDTO bankCard = null;
        if (bankCardEntity!=null) {
            return beaner.transform(bankCardEntity, BankCardDTO.class);
        }
        return bankCard;
    }

    // TODO: (Jose Guerra) countAccount method
    @Override
    @Transactional
    public Long countAccount(AccountSearchFilter filter) {

        if (filter.getId() == null) {
            Specification<AccountEntity> spec = (root, query, cb) -> {
                return applyAccountFilter(null, cb, root, filter);
            };
            return accountRepository.count(spec);

        } else if (AccountTypeEntity.ID.equals(filter.getId())) {
            Specification<AccountEntity> spec = (root, query, cb) -> {
                return applyAccountFilter(null, cb, root, filter);
            };
            return accountRepository.count(spec);
        }
        return null;

    }
}
