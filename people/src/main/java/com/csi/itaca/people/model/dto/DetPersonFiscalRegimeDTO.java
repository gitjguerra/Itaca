package com.csi.itaca.people.model.dto;


import com.csi.itaca.people.model.DetPersonFiscalRegime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class DetPersonFiscalRegimeDTO implements DetPersonFiscalRegime {

	private Long id;

	private Long personDetailId;

	private Long fiscalRegimeAmounts;

	private Long annuity;

	private LocalDate EffectDate;

}
