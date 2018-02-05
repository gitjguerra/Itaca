package com.csi.itaca.people.model;

import java.time.LocalDate;

public interface BankCard {
	
	Long getIdBankCard();
	Long getIdPersonDetail();
	Long getIdCardType();
	String getCard();
	Boolean getPrincipal();	
	Boolean getAvailable();
	Long getIdBank();
	LocalDate getExpirationDate();
	Long getSecurityCode();

}
