package com.csi.itaca.people.model;

import com.csi.itaca.people.model.dao.CivilStatusEntity;
import com.csi.itaca.people.model.dao.PersonStatusEntity;

public interface IndividualDetail {

    String getName1();

    String getName2();

    String getSurname1();

    String getSurname2();

    CivilStatusEntity getCivilStatus();

    PersonStatusEntity getPersonStatus();

}
