package com.csi.itaca.config.tools;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConfigurationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4162436725763156620L;

	private String message;
	private Throwable cause;

	public ConfigurationNotFoundException(String message) {
		this.message = message;
	}

}
