package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.FiscalRegimeAmounts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "PER_REGIME_AMOUNTS")
public class PerRegimeAmountsEntity implements FiscalRegimeAmounts{

	public static final String ID_REGIME_AMOUNTS = "id";

	public static final String ID_REGIME = "fiscalRegime";

	public static final String TAXES = "taxes";

	public static final String RETENTION = "retention";

	public static final String STARTDATE = "startDate";

	public static final String ENDDATE = "endDate";

	@Id
	@Column(name="ID_REGIME_AMOUNTS")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SPER_REGIME_AMOUNTS")
	@SequenceGenerator(name = "SPER_REGIME_AMOUNTS", sequenceName = "SPER_REGIME_AMOUNTS", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_REGIME")
	private PerFiscalRegimeEntity fiscalRegime;

	@Column(name="TAXES")
	private Long taxes;

	@Column(name="RETENTION")
	private Long retention;

	@Column(name="STARTDATE")
	private LocalDate startDate;

	@Column(name="ENDDATE")
	private LocalDate endDate;
	
}