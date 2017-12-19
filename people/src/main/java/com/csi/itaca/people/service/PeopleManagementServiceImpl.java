package com.csi.itaca.people.service;

import com.csi.itaca.common.utils.beaner.Beaner;
import com.csi.itaca.config.model.Configurator;
import com.csi.itaca.people.api.ConfiguracionModuloPersonas;
import com.csi.itaca.people.api.ErrorConstants;

import com.csi.itaca.people.model.dao.*;
import com.csi.itaca.people.model.dto.CompanyDTO;
import com.csi.itaca.people.model.dto.IndividualDTO;
import com.csi.itaca.people.model.dto.PersonDTO;
import com.csi.itaca.people.model.dto.PersonType;
import com.csi.itaca.people.model.filters.IndividualPeopleSearchFilter;
import com.csi.itaca.people.model.filters.CompanyPeopleSearchFilter;
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
    private Configurator configurator;

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
            errTracking.reject(ErrorConstants.VALIDATION_PERSON_NOT_FOUND);
        }
        return person;
    }

    @Override
    @Transactional(readOnly = true)
    public List<? extends PersonDTO> findPeople(PeopleSearchFilter filter) {

        List<? extends PersonEntity> personasEncontradas = new ArrayList<>();

        if (filter.getPersonType() != null) {


            if (!clavePrimariaLogicaCorrecta(filter)) {
                //throw new FaltanParametrosException();
            }

            personasEncontradas = listPersonas(filter);

            // if (personasEncontradas.size() != 1) {
            // throw new PersonaDuplicadaException();
            // }

            if (personasEncontradas.get(0).getDetails() == null || personasEncontradas.get(0).getDetails().isEmpty()) {
                // throw new PersonaSinDetallesException();
            }

            // TODO _REVIEW Pendiente validacion cuando se implemente nivel de
            // visualización (no habrán cabeceras duplicadas por cada detalle)
            if (personasEncontradas.get(0).getDetails().size() != 1) {
                //throw new PersonaDuplicadaException();
            }
        }

        return null;

    }



    @Transactional(readOnly = true)
    private List<? extends PersonEntity> listPersonas(PeopleSearchFilter parametros) {

        if (parametros.getPersonType().getId().equals(PersonType.INDIVIDUAL_PERSON_CODE)) {

            Specification<IndividualEntity> spec = (root, query, cb) -> {
                Predicate p = cb.and(cb.equal(root.type(), IndividualEntity.class));
                return applyCommonFilters(root, p, cb, parametros, "");
            };

            return individualPeopleRepository.findAll(spec);

        } else if (parametros.getPersonType().getId().equals(PersonType.COMPANY_PERSON_CODE)) {

            Specification<CompanyEntity> spec = (root, query, cb) -> {
                Predicate p = cb.and(cb.equal(root.type(), CompanyEntity.class));
                return applyCommonFilters(root, p, cb, parametros, "");
            };

            return legalPeopleRepository.findAll(spec);
        }
        return new ArrayList<>();
    }


    private boolean clavePrimariaLogicaCorrecta(PeopleSearchFilter parametros) {

        if (parametros.getPersonType().getId().equals(PersonType.INDIVIDUAL_PERSON_CODE)) {
            ConfiguracionModuloPersonas.ClavePrimariaLogicaPersonaFisica configuracionClavePrimariaLogicaPersonaFisica = configurator
                    .getConfig(ConfiguracionModuloPersonas.class).getClavePrimariaLogicaPersonaFisica();

            return !((configuracionClavePrimariaLogicaPersonaFisica.getCodigoIdentificacionEsClaveLogica()
                    && (parametros.getIdCode() == null || parametros.getIdCode().isEmpty()))
                    || (configuracionClavePrimariaLogicaPersonaFisica.getTipoIdentificacionEsClaveLogica()
                    && parametros.getIdCode() == null)
                    || (configuracionClavePrimariaLogicaPersonaFisica.getReferenciaExternaEsClaveLogica()
                    && (((IndividualPeopleSearchFilter) parametros).getExternalReference() == null
                    || beaner.transform(parametros, IndividualPeopleSearchFilter.class)
                    .getExternalReference().isEmpty()))
                    || (configuracionClavePrimariaLogicaPersonaFisica.getFechaNacimientoEsClaveLogica()
                    && ((IndividualPeopleSearchFilter) parametros).getDateOfBirth() == null));

        } else if (parametros.getPersonType().getId().equals(PersonType.COMPANY_PERSON_CODE)) {
            ConfiguracionModuloPersonas.ClavePrimariaLogicaPersonaJuridica configuracionClavePrimariaLogicaPersonaJuridica = configurator
                    .getConfig(ConfiguracionModuloPersonas.class).getClavePrimariaLogicaPersonaJuridica();

            return !((configuracionClavePrimariaLogicaPersonaJuridica.getCodigoIdentificacionEsClaveLogica()
                    && (parametros.getIdCode() == null || parametros.getIdCode().isEmpty()))
                    || (configuracionClavePrimariaLogicaPersonaJuridica.getTipoIdentificacionEsClaveLogica()
                    && parametros.getIdCode() == null));
        }

        return false;
    }


    private Predicate applyCommonFilters(Root<?> root, Predicate p, CriteriaBuilder cb,
                                         PeopleSearchFilter parametros, String path) {
        if (parametros.getIdCode() != null && !parametros.getIdCode().isEmpty()) {
            if (path.isEmpty())
                p = cb.and(p,
                        cb.equal(root.get(PersonEntity.ID_CODE), parametros.getIdCode()));
            else
                p = cb.and(p, cb.equal(root.get(path).get(PersonEntity.ID_CODE),
                        parametros.getIdCode()));
        }
        if (parametros.getIdType() != null) {
            if (path.isEmpty())
                p = cb.and(p, cb.equal(root.get(PersonEntity.ID_CODE).get(IDTypeEntity.ID),
                        parametros.getIdType().getId()));
            else
                p = cb.and(p,
                        cb.equal(root.get(path).get(PersonEntity.ID_CODE).get(IDTypeEntity.ID),
                                parametros.getIdType().getId()));
        }
        if (parametros.getPersonType().getId().equals(PersonType.INDIVIDUAL_PERSON_CODE)) {


            IndividualPeopleSearchFilter individualPeopleSearchFilter = (IndividualPeopleSearchFilter) parametros;
            if (individualPeopleSearchFilter.getExternalReference() != null
                    && !individualPeopleSearchFilter.getExternalReference().isEmpty()) {
                if (path.isEmpty())
                    p = cb.and(p, cb.equal(root.get(PersonEntity.EXTERNAL_REFERENCE_CODE),
                            individualPeopleSearchFilter.getExternalReference()));
                else
                    p = cb.and(p, cb.equal(root.get(path).get(PersonEntity.EXTERNAL_REFERENCE_CODE),
                            individualPeopleSearchFilter.getExternalReference()));
            }
            if (individualPeopleSearchFilter.getDateOfBirth() != null) {
                if (path.isEmpty()) {
                    p = cb.and(p, cb.equal(root.get(IndividualEntity.DATE_OF_BIRTH),
                            individualPeopleSearchFilter.getDateOfBirth()));
                }
            }
        } else if (parametros.getPersonType().getId().equals(PersonType.COMPANY_PERSON_CODE)) {
            CompanyPeopleSearchFilter companyPeopleSearchFilter = (CompanyPeopleSearchFilter) parametros;
            if (companyPeopleSearchFilter.getStartDate() != null) {
                if (path.isEmpty()) {
                    p = cb.and(p, cb.equal(root.get(CompanyEntity.START_DATE),
                            companyPeopleSearchFilter.getStartDate()));
                }
            }
        }

        return p;
    }
}
