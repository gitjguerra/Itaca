package com.csi.itaca.people.model.dao;

import com.csi.itaca.tools.utils.beaner.BeanerConfig;

import com.csi.itaca.people.model.Individual;
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
@DiscriminatorValue("individual")
public class IndividualEntity extends PersonEntity implements Individual {
	
	public static final String GENDER        = "gender";
	public static final String DATE_OF_BIRTH = "dateOfBirth";
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="gender_id")
	private GenderEntity gender;

	@Column(name="date_of_birth")
	private LocalDate dateOfBirth;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy = PersonDetailEntity.PERSON, targetEntity = PersonDetailEntity.class)
	@BeanerConfig(contentAs = IndividualDetailEntity.class)
	private List<IndividualDetailEntity> details;

}
