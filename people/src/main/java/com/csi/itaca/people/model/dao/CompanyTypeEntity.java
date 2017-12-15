package com.csi.itaca.people.model.dao;


import com.csi.itaca.people.model.CompanyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "per_company_type")
public class CompanyTypeEntity implements CompanyType {
	
	public static final String ID 	 	= "id";
	public static final String NAME		= "name";
	
	@Id
	@Column(name="company_type_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "per_company_type_seq")
	@SequenceGenerator(name = "per_company_type_seq", sequenceName = "per_company_type_seq", allocationSize = 1)
	private Long id;

	@Column
	private String name;
}
