package com.csi.itaca.people.model;

public interface Account {
	
	Long getId();
	Long getPersonDetail();
	AccountClasification getAccountClasification();
	Long getTypeAccount();
	String getAccount();
	Boolean getPrincipal();
	Boolean getAvailable();
	Long getIdBank();

}
