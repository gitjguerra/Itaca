package com.csi.itaca.people.model.dao;

import com.csi.itaca.common.model.dao.CountryEntity;
import com.csi.itaca.people.model.Nationality;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PER_NATIONALITY")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class NationalityEntity implements Nationality {
	/**
	 * 
	 */
	public static final String ID_NATIONALITY = "id";
	public static final String BYDEFAULT = "bydefault";
	public static final String PERSON_DETAIL_ID = "personDetailId";
	public static final String COUNTRY_ID = "country";

	@Id
	@Column(name="ID_NATIONALITY")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_NATIONALITY")
	@SequenceGenerator(name = "SEQ_NATIONALITY", sequenceName = "SEQ_NATIONALITY", allocationSize = 1)
	private Long id;

	@Column(name = "PERSON_DETAIL_ID")
	private Long personDetailId;


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COUNTRY_ID")
	private CountryEntity country;

	
	@Column(name = "BY_DEFAULT", nullable = false)
	private Boolean bydefault;


}
