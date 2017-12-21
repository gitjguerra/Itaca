package com.csi.itaca.people.model.dao;

import com.csi.itaca.tools.utils.beaner.BeanerConfig;
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
	
    @OneToMany(fetch=FetchType.LAZY, mappedBy = PersonDetailEntity.PERSON,
			   targetEntity = PersonDetailEntity.class, cascade=CascadeType.REMOVE)
    @BeanerConfig(contentAs = CompanyDetailEntity.class)
    private List<CompanyDetailEntity> details;
}
