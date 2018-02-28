package com.csi.itaca.people.model.filters;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class FilterSearchPersonFiscalRegimelDTO implements FilterSearchPersonFiscalRegime {

	private Long id;

	public FilterSearchPersonFiscalRegimelDTO(Long id) {

		this.id = id;
	}



}
