package com.csi.itaca.common.model.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dir_country")
public class CountryEntity implements Country {

	public static final String ID 		= "id";
	public static final String ISO_CODE = "isoCode";
	public static final String NAME 	= "name";
	
	@Id
	@Column(name="country_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "country_id_seq")
	@SequenceGenerator(name = "country_id_seq", sequenceName = "country_id_seq", allocationSize = 1)
	private Long id;
	
	@Column(name="iso_code", nullable=false)
	private Long isoCode;

	@Column(name="name", nullable=false)
	private String name;
	
}
