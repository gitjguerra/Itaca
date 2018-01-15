package com.csi.itaca.people.model.dao;


import com.csi.itaca.people.model.IndividualDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("individual")
public class IndividualDetailEntity extends PersonDetailEntity implements IndividualDetail {
	
	public static final String NAME1 			= "name1";
	public static final String NAME2 			= "name2";
	public static final String SURNAME1 		= "surname1";
	public static final String SURNAME2 		= "surname2";
	public static final String CIVIL_STATUS 	= "civilStatus";
	public static final String PERSON_STATUS 	= "personStatus";
	
	@Column(nullable=false)
	private String name1;
	
	@Column
	private String name2;
	
	@Column(nullable=false)
	private String surname1;
	
	@Column
	private String surname2;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="civil_status_id")
	private CivilStatusEntity civilStatus;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="person_status_id")
	private PersonStatusEntity personStatus;
	
}
