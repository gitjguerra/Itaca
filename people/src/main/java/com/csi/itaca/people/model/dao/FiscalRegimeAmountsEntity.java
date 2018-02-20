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
@Table(name = "FIS_FISCAL_REGIME_AMOUNTS")
public class FiscalRegimeAmountsEntity implements FiscalRegimeAmounts{

	public static final String ID_FISCAL_REGIME_AMOUNTS = "id";

	public static final String ID_FISCAL_REGIME = "fiscalRegime";

	public static final String TAXES = "taxes";

	public static final String RETENTION = "retention";

	public static final String STARTDATE = "startDate";

	public static final String ENDDATE = "endDate";

	@Id
	@Column(name="ID_FISCAL_REGIME_AMOUNTS")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SFIS_FISCAL_REGIME_AMOUNTS")
	@SequenceGenerator(name = "SFIS_FISCAL_REGIME_AMOUNTS", sequenceName = "SFIS_FISCAL_REGIME_AMOUNTS", allocationSize = 1)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_FISCAL_REGIME")
	private FiscalRegimeEntity fiscalRegime;

	@Column(name="TAXES")
	private Long taxes;

	@Column(name="RETENTION")
	private Long retention;

	@Column(name="STARTDATE")
	private LocalDate startDate;

	@Column(name="ENDDATE")
	private LocalDate endDate;
	
}