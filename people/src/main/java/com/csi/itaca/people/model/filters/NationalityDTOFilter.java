package com.csi.itaca.people.model.filters;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class NationalityDTOFilter {

	private Long id;
	
	public NationalityDTOFilter(Long id) {
	
		this.id = id;
	}
	
}
