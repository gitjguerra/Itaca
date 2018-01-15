package com.csi.itaca.people.model;

public interface PersonStatus {
	
	Long getId();

	/**
	 * Name of this status.
	 * @return
	 */
	String getName();

	/**
	 * @return Indicates if this type is an individual.
	 */
	Boolean getIndividual();

	/**
	 * @return Indicates if this type is a company.
	 */
	Boolean getCompany();

}
