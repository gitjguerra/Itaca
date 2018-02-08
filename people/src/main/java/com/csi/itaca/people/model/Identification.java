package com.csi.itaca.people.model;

import com.csi.itaca.common.model.Country;
import java.time.LocalDate;

public interface Identification {
	
	Long getId();

	IDType getIdType();

	String getIdentificationCode();

	Country getCountry();

	Long getPersonDetailId();

	LocalDate getIssueDate();

}
