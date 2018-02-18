package com.csi.itaca.people.model;

import java.time.LocalDate;

public interface BankCard {
	
	Long getBankCardId();
	Long getPersonDetailId();
	Long getCardTypeId();
	String getCard();
	Boolean getPrincipal();	
	Boolean getAvailable();
	Long getBankId();
	LocalDate getExpirationDate();
	Long getSecurityCode();

}
