package com.csi.itaca.people.model;

import java.time.LocalDate;

public interface Company extends Person {

    /**
     * Gets the company type.
     * @return the company type.
     */
    CompanyType getCompanyType();

    /**
     * Gets the start date.
     * @return the start date.
     */
    LocalDate getStartDate();

}
