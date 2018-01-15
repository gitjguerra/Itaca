package com.csi.itaca.people.model.dto;


import com.csi.itaca.people.model.CompanyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyTypeDTO implements CompanyType {

	private Long id;

	private String name;
}
