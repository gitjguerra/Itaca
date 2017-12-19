package com.csi.itaca.people.model;

import com.csi.itaca.common.model.Country;

public interface PersonDetail {

    /** Gets the detail ID. */
    Long getId();

    /** Gets the person. */
    Person getPerson();

    /** Gets the the language. */
    Language getLanguage();

    /** Gets the the country. */
    Country getCountry();

    /** Gets the name. */
    String getName();
}
