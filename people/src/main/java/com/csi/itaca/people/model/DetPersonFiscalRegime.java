package com.csi.itaca.people.model;

import java.time.LocalDate;

public interface DetPersonFiscalRegime {

	Long getId();

	Long getPersonDetailId();

	Long getFiscalRegimeAmounts();

	Long getAnnuity();

	LocalDate getEffectDate();

}
