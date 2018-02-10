package com.csi.itaca.people.model;

public interface RelatedPerson {

	Long getId();
	PersonDetail getPersonDetail();
	PersonDetail getPersonRel();
	RelationType getRelationType();

}
