package com.csi.itaca.people.businesslogic;

import com.csi.itaca.people.model.filters.PeopleSearchFilter;
import org.springframework.validation.Errors;

public interface PeopleManagementBusinessLogic {

    /**
     * Checks if the supplied filter is correct based on the configuration.
     * @param filter the filter parameters
     * @param errTracking any errors will be added here.
     * @return true if correct.
     */
    boolean isLogicalPrimaryKeyCorrect(PeopleSearchFilter filter, Errors errTracking);

    /**
     * Checks against the configuration if duplicate people are allowed
     * @return true if allowed
     */
    boolean isDuplicatePeopleAllowed();
}
