package com.csi.itaca.people.model.filters;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class IndividualPeopleSearchFilter extends PeopleSearchFilter {
	
	private LocalDate dateOfBirth;
}
