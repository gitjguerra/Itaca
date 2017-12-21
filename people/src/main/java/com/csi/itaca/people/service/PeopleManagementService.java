package com.csi.itaca.people.service;

import com.csi.itaca.people.model.dto.PersonDTO;
import com.csi.itaca.people.model.filters.PeopleSearchFilter;
import org.springframework.validation.Errors;

import java.util.List;

/**
 * People management service.
 * @author bboothe
 */
public interface PeopleManagementService {

    /**
     * Retrieves a specific person.
     * @param id the person id.
     * @param errTracking error tracker. Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     * @return the person if found otherwise null.
     */
    PersonDTO getPerson(Long id, Errors errTracking);

    /**
     * Finds people or a single person based on the supplied filter. Where there will be a single person or a list of
     * people will be based on the configuration.
     * @param filter the search filter.
     * @param errTracking error tracker. Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     * @return a list containing 1 more people depend upon the configuration. Will return an empty list if
     * no people were found.
     */
    List<? extends PersonDTO> findPeople(PeopleSearchFilter filter, Errors errTracking);

    /**
     * Saves or updates the provided person.
     * @param personToSaveOrUpdate the person to save/update.
     * @param errTracking error tracker. Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     */
    PersonDTO saveOrUpdatePerson(PersonDTO personToSaveOrUpdate, Errors errTracking);

    /**
     * Delete the person associated to the provided ID
     * @param personId the id of the person to delete.
     * @param errTracking error tracker. Will advise if person not found.
     *                    Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     */
     void deletePerson(Long personId, Errors errTracking);

    /**
     * Checks if the provided external reference code already exists in the database.
     * @param externalReferenceCode the external reference code to check.
     * @return true if already exists otherwise false.
     */
    Boolean doseExternalReferenceAlreadyExist(String externalReferenceCode);
}
