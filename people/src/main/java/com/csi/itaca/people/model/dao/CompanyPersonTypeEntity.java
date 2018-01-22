package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.CompanyPersonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "per_company_person_type")
public class CompanyPersonTypeEntity implements CompanyPersonType {
	
	public static final String ID = "id";
	public static final String LITERAL = "literal";
	
	@Id
	@Column(name="company_person_type_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "per_company_person_seq")
	@SequenceGenerator(name = "per_company_person_seq", sequenceName = "per_company_person_seq", allocationSize = 1)
	private Long id;

	@Column(name="value")
	private String literal;
	
}
