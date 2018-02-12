package com.csi.itaca.people.model;

public interface RelatedPerson {

	Long getId();
	PersonDetail getPersonDetail();
	Long getPersonRelId();
	Long getRelationTypeId();

	/*
	PersonDetail getPersonDetail();
	PersonDetail getPersonRel();
	RelationType getRelationType();
	*/
}
