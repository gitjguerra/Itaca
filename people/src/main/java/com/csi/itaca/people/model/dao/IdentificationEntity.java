package com.csi.itaca.people.model.dao;


import com.csi.itaca.common.model.dao.CountryEntity;
import com.csi.itaca.people.model.Identification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "per_identification")
public class IdentificationEntity implements Identification {

	public static final String ID 						= "id";
	public static final String DETAIL_PERSON_ID 		= "personDetailId";
	public static final String ID_TYPE 					= "idType";
	public static final String COUNTRY 					= "country";
	public static final String IDENTIFICATION_CODE 		= "identificationCode";
	public static final String ISSUE_DATE 				= "issueDate";
	
	@Id
	@Column(name="identification_id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "per_identification_seq")
	@SequenceGenerator(name = "per_identification_seq", sequenceName = "per_identification_seq", allocationSize = 1)
	private Long id;

	@Column(name = "person_detail_id")
	private Long personDetailId;
	
	@ManyToOne
	@JoinColumn(name="id_type_id")
	private IDTypeEntity idType;
	
	@ManyToOne
	@JoinColumn(name = "country_id")
	private CountryEntity country;
	
	@Column(name="identification_code", nullable=false)
	private String identificationCode;
	
	@Column(name="issue_date")
	private LocalDate issueDate;

}
