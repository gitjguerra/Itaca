package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.ContactType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "per_contact_type")
public class ContactTypeEntity implements ContactType {
	
	public static final String ID = "id";
	public static final String VALOR = "value";
	
	@Id
	@Column(name="contact_type_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "per_contact_type_seq")
	@SequenceGenerator(name = "per_contact_type_seq", sequenceName = "per_contact_type_seq", allocationSize = 1)
	private Long id;

	@Column
	private String value;
}
