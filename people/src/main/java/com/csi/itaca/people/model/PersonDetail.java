package com.csi.itaca.people.model;

import com.csi.itaca.common.model.dao.Country;
import com.csi.itaca.people.model.dao.PersonEntity;

public interface PersonDetail {

    Long getId();

    PersonEntity getPerson();

    Language getLanguage();

    Country getCountry();

    String getName();
}
