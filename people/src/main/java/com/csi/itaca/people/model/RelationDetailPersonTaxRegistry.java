package com.csi.itaca.people.model;

import java.time.LocalDate;


public interface RelationDetailPersonTaxRegistry {
	PersonDetail getPersonDetail();
	RelationDetailPersonTaxRegistry getTaxRegimeAmounts();
	Long getId();
	Long getAnnuity();
	LocalDate getEffectDate();
}
