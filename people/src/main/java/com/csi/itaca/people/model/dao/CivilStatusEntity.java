package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.CivilStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "per_civil_status")
public class CivilStatusEntity implements CivilStatus {
	
	public static final String ID 		= "id";
	public static final String NAME 	= "name";
	
	@Id
	@Column(name="civil_status_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "per_civil_status_seq")
	@SequenceGenerator(name = "per_civil_status_seq", sequenceName = "per_civil_status_seq", allocationSize = 1)
	private Long id;

	@Column
	private String name;
	
}
