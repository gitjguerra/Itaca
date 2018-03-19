package com.csi.itaca.people.model;

public interface RelatedPersonDetailAddress {
	
	Long getId();
	AddressType getAddressType();
	PersonDetail getPersonDetail();
	Address getAddress();

}
