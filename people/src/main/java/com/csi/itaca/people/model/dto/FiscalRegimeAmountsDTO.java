package com.csi.itaca.people.model.dto;

import com.csi.itaca.people.model.FiscalRegimeAmounts;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode

public class FiscalRegimeAmountsDTO implements FiscalRegimeAmounts {
	
	private Long id;
	
	private DetPersonFiscalRegimeDTO fiscalRegime;

	private Long taxes;
	
	private Long retention;

	private LocalDate startDate;

	private LocalDate endDate;
}
