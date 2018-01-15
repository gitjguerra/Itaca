package com.csi.itaca.people.model;

import java.time.LocalDate;

public interface Individual extends Person {

	Gender getGender();

	LocalDate getDateOfBirth();
	
}
