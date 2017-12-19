package com.csi.itaca.people.model.dto;

import com.csi.itaca.common.utils.beaner.BeanerConfig;
import com.csi.itaca.people.model.Company;
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
	private List<CompanyDetailDTO> details;
}
