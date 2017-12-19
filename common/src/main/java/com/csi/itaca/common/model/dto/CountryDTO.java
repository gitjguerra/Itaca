package com.csi.itaca.common.model.dto;

import com.csi.itaca.common.model.Country;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CountryDTO implements Country {

	private Long id;
	
	private String isoCode;

	private String name;
	
}
