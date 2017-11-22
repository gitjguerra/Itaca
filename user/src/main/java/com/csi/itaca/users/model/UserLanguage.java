package com.csi.itaca.users.model;

/**
 * Represent a language the user speaks.
 */
public interface UserLanguage {

	/**
	 * Gets the user id.
	 * @return the user id.
	 */
	Long getId();

	/**
	 * Gets language code.
	 * @return the language code.
	 */
	String getCode();

	/**
	 * Gets the language description.
	 * @return the language description.
	 */
	String getValue();

}
