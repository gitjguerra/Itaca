package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.Contact;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PER_CONTACT")
public class ContactEntity implements Contact {
	
	public static final String ID = "id";
	public static final String ID_CONTACT_TYPE = "ContactType";
	public static final String VALUE_CONTACT = "valueContact";
	public static final String ID_ADDRESS = "idAddress";
	public static final String ID_PERSON_DETAIL = "personDetailId";

	@Id
	@Column(name="ID_PER_CONTACT")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CONTACT")
	@SequenceGenerator(name = "SEQ_CONTACT", sequenceName = "SEQ_CONTACT", allocationSize = 1)
	private Long id;
	
	@Column(name="ID_PERSON_DETAIL")
	private Long personDetailId;
	
	@Column(name="ID_CONTACT_TYPE")
	private Long contactType;
	
	@Column(name="VALUE_CONTACT")
	private String valueContact;
	
	@Column(name="ID_ADDRESS")
	private Long idAddress;

}


