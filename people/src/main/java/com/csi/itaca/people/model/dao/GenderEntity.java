package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "per_gender")
public class GenderEntity implements Gender {

	public static final String ID 			= "id";
	public static final String NAME 		= "name";
	public static final String IS_MALE 		= "male";
	public static final String IS_FEMALE 	= "female";
	public static final String IS_OTHER 	= "other";

	@Id
	@Column(name = "gender_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "per_gender_seq")
	@SequenceGenerator(name = "per_gender_seq", sequenceName = "per_gender_seq", allocationSize = 1)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "is_male")
	private Boolean male;

	@Column(name = "is_female")
	private Boolean female;

	@Column(name = "is_other")
	private Boolean other;
}
