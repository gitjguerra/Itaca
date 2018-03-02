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
	public static final String ID_DET_PERSON = "personDetailId";
	public static final String ID_COUNTRY = "country";

	@Id
	@Column(name="ID_NATIONALITY")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_NATIONALITY")
	@SequenceGenerator(name = "SEQ_NATIONALITY", sequenceName = "SEQ_NATIONALITY", allocationSize = 1)
	private Long id;

	@Column(name = "id_det_person")
	private Long personDetailId;


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_COUNTRY")
	private CountryEntity country;

	
	@Column(name = "BY_DEFAULT", nullable = false)
	private Boolean bydefault;


}
