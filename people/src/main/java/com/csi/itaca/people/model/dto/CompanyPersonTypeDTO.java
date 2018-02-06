package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.CompanyPersonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyPersonTypeDTO implements CompanyPersonType {
	
	private Long id;

	private String literal;
}
