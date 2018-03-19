package com.csi.itaca.people.model;

import java.time.LocalDate;

	public interface FiscalRegimeAmounts {
	
	Long getId();

	DetPersonFiscalRegime getFiscalRegime();

	Long getTaxes();

	Long getRetention();

	LocalDate getStartDate();

	LocalDate getEndDate();

}
