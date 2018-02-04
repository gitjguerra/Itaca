package com.csi.itaca.people.model;

import java.time.LocalDate;

public interface BankCard {
	
	Long getIdBankCard();
	Long getIdPersonDetail();
	CardType getIdCardType();
	String getCard();
	Boolean getPrincipal();	
	Boolean getAvailable();
	Bank getIdBank();
	LocalDate getExpirationDate();
	Long getSecurityCode();

}
