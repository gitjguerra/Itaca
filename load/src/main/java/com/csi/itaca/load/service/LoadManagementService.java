package com.csi.itaca.load.service;

/**
 * Load management service.
 * @author jguerra
 */
public interface LoadManagementService {

    /**
     * Finds people or a single person based on the supplied filter. Where there will be a single person or a list of
     * people will be based on the configuration.
     * @param filter the search filter.
     * @param errTracking error tracker. Please @see {@link com.csi.itaca.people.api.ErrorConstants}
     * @return a list containing 1 more people depend upon the configuration. Will return an empty list if
     * no people were found.
     */
    //List<? extends PersonDTO> findPeople(PeopleSearchFilter filter, Errors errTracking);

}
