package com.csi.itaca.users.model;

import com.csi.itaca.common.model.BaseModel;

/**
 * Represent a language the user speaks.
 */
public interface UserLanguage extends BaseModel {

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
