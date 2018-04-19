package com.csi.itaca.people.model.dao;

import com.csi.itaca.common.model.dao.CountryEntity;
import com.csi.itaca.people.model.FiscalRegime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "PER_REGIME")
public class PerRegimeEntity implements FiscalRegime{
	
	public static final String ID_REGIME = "id";

	public static final String VALUE = "value";

	public static final String COUNTRY_ID = "country";
	
	@Id
	@Column(name="ID_REGIME")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SPER_REGIME")
	@SequenceGenerator(name = "SPER_REGIME", sequenceName = "SPER_REGIME", allocationSize = 1)
	private Long id;

	@Column(name="VALUE")
	private String value;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COUNTRY_ID")
	private CountryEntity country;

}