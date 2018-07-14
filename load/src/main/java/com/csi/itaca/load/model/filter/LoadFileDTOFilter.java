package com.csi.itaca.load.model.filter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class LoadFileDTOFilter {

	private Long id;
	
	public LoadFileDTOFilter(Long id) {
	
		this.id = id;
	}
	
}
