package com.csi.itaca.people.model;

public interface Account {
	
	Long getId();
	Long getPersonDetailId();
	Long getAccountClasificationId();
	Long getTypeAccountId();
	String getAccount();
	Boolean getPrincipal();
	Boolean getAvailable();
	Long getBankId();

}
