package com.csi.itaca.people.model.dto;

import com.csi.itaca.tools.utils.beaner.BeanerConfig;
import com.csi.itaca.people.model.Individual;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class IndividualDTO extends PersonDTO implements Individual {

	private GenderDTO gender;

	private LocalDate dateOfBirth;

	@BeanerConfig(contentAs = IndividualDetailDTO.class)
	@JsonIgnoreProperties("person")    // Added prevent recursion when generating the JSON.
	private List<IndividualDetailDTO> details;

}
