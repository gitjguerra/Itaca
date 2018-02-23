package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.AddressFormat1;
import com.csi.itaca.people.model.PublicPerson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PER_PUBLIC_PERSON")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="person_type", discriminatorType=DiscriminatorType.STRING)
public class PublicPersonEntity implements PublicPerson {


	public static final String id_per_public_person 	= "IdPerPublicPerson";
	public static final String id_type_public_person 	= "IdTypePublicPerson";
	public static final String id_person 			    = "IdPerson";

	@Id
	@Column(name="id_per_public_person")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_PER_PUBLIC_PERSON")
	@SequenceGenerator(name = "SEQ_PER_PUBLIC_PERSON", sequenceName = "SEQ_PER_PUBLIC_PERSON", allocationSize = 1)
	private Long IdPerPublicPerson;

	@Column(name="id_type_public_person")
	private String IdTypePublicPerson;

	@Column(name="id_person")
	private String IdPerson;


}
