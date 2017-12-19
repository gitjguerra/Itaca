package com.csi.itaca.people.model.dto;

import com.csi.itaca.common.model.dto.CountryDTO;
import com.csi.itaca.people.model.PersonDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public abstract class PersonDetailDTO implements PersonDetail {

	private Long id;

	// We add this ignore to prevent recursion when generating the JSON.
	@JsonIgnore
	private PersonDTO person;

	private LanguageDTO language;

	private CountryDTO country;

	private String name;

}
