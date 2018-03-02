package com.csi.itaca.people.model.dao;

import com.csi.itaca.common.model.dao.CountryEntity;
import com.csi.itaca.people.model.PersonDetail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "per_person_detail")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "detail_type", discriminatorType = DiscriminatorType.STRING)
public abstract class PersonDetailEntity implements PersonDetail {

	public static final String ID 		= "id";
	public static final String PERSON 	= "person";
	public static final String NAME 	= "name";
	public static final String LANGUAGE = "language";
	public static final String COUNTRY 	= "country";

	@Id
	@Column(name = "person_detail_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "per_person_detail_seq")
	@SequenceGenerator(name = "per_person_detail_seq", sequenceName = "per_person_detail_seq", allocationSize = 1)
	private Long id;

	@ManyToOne
	@JoinColumn(name="person_id", nullable=false)
	private PersonEntity person;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "language_id")
	private LanguageEntity language;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	private CountryEntity country;

	@Column(name = "name")
	private String name;

	/*
	@MapKey(name = "id")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "detallePersona")
	@BeanerConfig(contentAs = RelDetPersonaDireccionEntity.class)
	private List<RelDetPersonaDireccionEntity> direcciones;

	@MapKey(name = "id")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "detallePersona")
	@BeanerConfig(contentAs = IdentificadorEntity.class)
	private List<IdentificadorEntity> identificadores;

	@MapKey(name = "id")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "detallePersona")
	@BeanerConfig(contentAs = RelPersonaMetadataEntity.class)
	private List<RelPersonaMetadataEntity> metadata;
	*/

}