package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.PersonWithDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "per_person")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="person_type", discriminatorType=DiscriminatorType.STRING)
public abstract class PersonEntity implements PersonWithDetails {

	public static final String ID 						= "id";
	public static final String ID_TYPE 					= "idType";
	public static final String ID_CODE 					= "identificationCode";
	public static final String EXTERNAL_REFERENCE_CODE 	= "externalReferenceCode";
	public static final String DETAILS 					= "details";

	@Id
	@Column(name="person_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "per_person_seq")
	@SequenceGenerator(name = "per_person_seq", sequenceName = "per_person_seq", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_type_id")
	private IDTypeEntity idType;

	//@Column
	//private Long publica;

	@Column(name="identification_code", nullable=false)
	private String identificationCode;

	@Column(name="external_reference_code")
	private String externalReferenceCode;
}
