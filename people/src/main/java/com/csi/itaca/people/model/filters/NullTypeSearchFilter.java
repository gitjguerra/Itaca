package com.csi.itaca.people.model.filters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class NullTypeSearchFilter extends PeopleSearchFilter {

	private LocalDate startDate;
}
