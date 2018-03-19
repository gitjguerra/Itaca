package com.csi.itaca.people.model.dto;

import com.csi.itaca.common.model.dto.CountryDTO;
import com.csi.itaca.people.model.Nationality;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class NationalityDTO implements Nationality {

	private Long id;

	private Long personDetailId;

	@NotNull
	private CountryDTO country;

	private Boolean Bydefault;

}
