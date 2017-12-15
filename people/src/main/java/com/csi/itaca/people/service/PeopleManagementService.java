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
     * @param errTracking error tracker.
     * @return the person if found otherwise null.
     */
    PersonDTO getPerson(Long id, Errors errTracking);

    /**
     * Finds people based on the supplied filter.
     * @param filter the search filter
     * @return a list of people.
     */
    List<? extends PersonDTO> findPeople(PeopleSearchFilter filter);
}
