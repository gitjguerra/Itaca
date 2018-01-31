package com.csi.itaca.people.model;

public interface Account {
	
	Long getId();
	PersonDetail getPersonDetail();
	AccountClasification getAccountClasification();
	AccountType getAccountType();
	String getAccount();
	Boolean getPrincipal();
	Boolean getAvailable();
	Bank getBank();

}
