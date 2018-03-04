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
	public static final String CONTACT_TYPE_ID = "ContactType";
	public static final String VALUE_CONTACT = "valueContact";
	public static final String ADDRESS_ID = "idAddress";
	public static final String PERSON_DETAIL_ID = "personDetailId";

	@Id
	@Column(name="PER_CONTACT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CONTACT")
	@SequenceGenerator(name = "SEQ_CONTACT", sequenceName = "SEQ_CONTACT", allocationSize = 1)
	private Long id;
	
	@Column(name="PERSON_DETAIL_ID")
	private Long personDetailId;
	
	@Column(name="CONTACT_TYPE_ID")
	private Long contactType;
	
	@Column(name="VALUE_CONTACT")
	private String valueContact;
	
	@Column(name="ADDRESS_ID")
	private Long idAddress;

}


