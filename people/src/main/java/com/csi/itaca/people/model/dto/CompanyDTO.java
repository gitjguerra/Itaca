package com.csi.itaca.people.model.dto;

import com.csi.itaca.tools.utils.beaner.BeanerConfig;
import com.csi.itaca.people.model.Company;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CompanyDTO extends PersonDTO implements Company {
	
	private CompanyTypeDTO companyType;
	
	private LocalDate startDate;

	@BeanerConfig(contentAs = CompanyDetailDTO.class)
	@JsonIgnoreProperties("person")    // Added prevent recursion when generating the JSON.
	private List<CompanyDetailDTO> details;
}
