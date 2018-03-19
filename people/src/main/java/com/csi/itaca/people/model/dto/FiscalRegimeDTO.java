package com.csi.itaca.people.model.dto;

import com.csi.itaca.common.model.dto.CountryDTO;
import com.csi.itaca.people.model.FiscalRegime;
import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode

public class FiscalRegimeDTO implements FiscalRegime {

	private Long id;

	private String value;

	@NotNull
	private CountryDTO country;

}
