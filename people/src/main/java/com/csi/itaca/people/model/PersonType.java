package com.csi.itaca.people.model;

public interface PersonType {

	String INDIVIDUAL_PERSON_CODE 		= "individual";
	String I18N_INDIVIDUAL_PERSON_KEY 	= "personType.individual";
	String COMPANY_PERSON_CODE 			= "company";
	String I18N_COMPANY_PERSON_KEY 		= "personType.company";

	String ID_FIELD 					= "id";
	String NAME_FIELD 					= "name";

	String getId();
	String getName();
}