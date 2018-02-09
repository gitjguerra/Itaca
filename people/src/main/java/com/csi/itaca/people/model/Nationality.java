package com.csi.itaca.people.model;

import com.csi.itaca.common.model.Country;

public interface Nationality {
	
	Long getId();

	Long getPersonDetailId();

	Country getCountry();

	Boolean getBydefault();


}
