package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.PersonStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "per_person_status")
public class PersonStatusEntity implements PersonStatus {
	
	public static final String ID 				= "id";
	public static final String NAME 			= "name";
	public static final String IS_INDIVIDUAL	= "individual";
	public static final String ID_COMPANY		= "company";
	
	@Id
	@Column(name="person_status_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "per_person_status_seq")
	@SequenceGenerator(name = "per_person_status_seq", sequenceName = "per_person_status_seq", allocationSize = 1)
	private Long id;

	@Column(name="name")
	private String name;
	
	@Column(name="is_individual")
	private Boolean individual;
	
	@Column(name="is_company")
	private Boolean company;

}
