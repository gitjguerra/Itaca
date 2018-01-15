package com.csi.itaca.common.model.dto;

import com.csi.itaca.common.model.BaseModelImpl;
import com.csi.itaca.common.model.Country;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
public class CountryDTO extends BaseModelImpl implements Country {

	@JsonInclude(NON_NULL)
	private String isoCode;

	@JsonInclude(NON_NULL)
	private String name;
	
}
