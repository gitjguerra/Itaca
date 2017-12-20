package com.csi.itaca.people.service;

import com.csi.itaca.people.model.dto.GenderDTO;
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
}
