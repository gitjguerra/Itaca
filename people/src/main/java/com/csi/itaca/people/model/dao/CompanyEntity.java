package com.csi.itaca.people.model.dao;

import com.csi.itaca.common.utils.beaner.BeanerConfig;
import com.csi.itaca.people.model.Company;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("company")
public class CompanyEntity extends PersonEntity implements Company {
	
	public static final String COMPANY_TYPE 	= "companyType";
	public static final String START_DATE 		= "startDate";
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="company_type_id")
	private CompanyTypeEntity companyType;
	
	@Column(name="start_date")
	private LocalDate startDate;
	
	@MapKey(name = "id")
	@OneToMany(fetch=FetchType.LAZY, mappedBy="person")
	@BeanerConfig(contentAs = CompanyDetailEntity.class)
	private List<CompanyDetailEntity> details;
}
