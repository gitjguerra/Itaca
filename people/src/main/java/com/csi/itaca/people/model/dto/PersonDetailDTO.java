package com.csi.itaca.people.model.dto;

import com.csi.itaca.common.model.dto.CountryDTO;
import com.csi.itaca.people.model.PersonDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public abstract class PersonDetailDTO implements PersonDetail {

	private Long id;

	private LanguageDTO language;

	private CountryDTO country;

	private String name;

}
