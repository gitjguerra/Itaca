package com.csi.itaca.people.model;

import java.time.LocalDate;

public interface BankCard {
	
	Long getId();
	PersonDetail getPersonDetail();
	LocalDate getExpirationDate();
	CardType getCardType();
	String getNumber();
	Boolean getPrincipal();	
	Boolean getAvailable();
	Bank getBank();
	Long getSecurityCode();
	
}
