package com.csi.itaca.people.model.filters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class IndividualSearchFilter extends PeopleSearchFilter {

	public static final String DATE_OF_BIRTH_FIELD 	    = "dateOfBirth";

	private LocalDate dateOfBirth;
}
