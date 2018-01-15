package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.IDType;
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
@Table(name = "per_id_type")
public class IDTypeEntity implements IDType {

	public static final String ID 				= "id";
	public static final String NAME 			= "name";
	public static final String IS_INDIVIDUAL	= "individual";
	public static final String ID_COMPANY		= "company";

	@Id
	@Column(name = "id_type_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "per_id_type_seq")
	@SequenceGenerator(name = "per_id_type_seq", sequenceName = "per_id_type_seq", allocationSize = 1)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "is_individual")
	private Boolean individual;

	@Column(name = "is_company")
	private Boolean company;

}
