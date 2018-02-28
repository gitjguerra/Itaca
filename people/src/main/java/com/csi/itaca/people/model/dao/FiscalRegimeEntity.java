package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.DetPersonFiscalRegime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PER_FISCAL_REGIME")
public class FiscalRegimeEntity implements DetPersonFiscalRegime{
	
	public static final String ID_FISCAL_REGIME = "id";

	public static final String PERSON_DETAIL_ID = "personDetailId";

	public static final String ID_FISCAL_REGIME_AMOUNTS = "fiscalRegimeAmounts";

	public static final String ANNUITY = "annuity";

	public static final String EFFECT_DATE = "effectDate";

	@Id
	@Column(name="ID_FISCAL_REGIME")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_PER_FISCAL_REGIME")
	@SequenceGenerator(name = "SEQ_PER_FISCAL_REGIME", sequenceName = "SEQ_PER_FISCAL_REGIME", allocationSize = 1)
	private Long id;	

	@Column(name = "PERSON_DETAIL_ID")
	private Long personDetailId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_FISCAL_REGIME_AMOUNTS")
	private FiscalRegimeAmountsEntity fiscalRegimeAmounts;

	@Column(name="ANNUITY")
	private Long annuity;
	
	@Column(name="EFFECT_DATE")
	private LocalDate effectDate;
}