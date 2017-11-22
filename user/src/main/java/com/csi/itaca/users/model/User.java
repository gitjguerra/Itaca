package com.csi.itaca.users.model;

import java.time.LocalDate;

/**
 * Represents a company user than can that can log in to the system.
 * @author bboothe
 */
public interface User {

	/**
	 * Gets the user id.
	 * @return the user id
	 */
	Long getId();

	/**
	 * Gets the username.
	 * @return
	 */
	String getUsername();

	/**
	 * Gets the user's first name.
	 * @return the user's first name.
	 */
	String getFirstName();

	/**
	 * Gets the user's last name.
	 * @return the user's last name.
	 */
	String getLastName();

	/**
	 * Gets the description.
	 * @return the user's last name.
	 */
	String getDescription();

	/**
	 * Gets the user's email address.
	 * @return the user's email address.
	 */
	String getEmail();

	/**
	 * Gets the user's company code.
	 * @return the user's company code.
	 */
	String getCompanyCode();

	/**
	 * Gets the date when the user started in the company.
	 * @return the user start date.
	 */
	LocalDate getCompanyStartDate();

	/**
	 * Gets the date when the user left the company.
	 * @return the user start date
	 */
	LocalDate getCompanyEndDate();


	/**
	 * Gets the date when the user was blocked.
	 * @return the blocked date.
	 */
	LocalDate getBlockedDate();


	/**
	 * Indicates if the user is blocked.
	 * @return true if the user is blocked.
	 */
	boolean isBlocked();
}
