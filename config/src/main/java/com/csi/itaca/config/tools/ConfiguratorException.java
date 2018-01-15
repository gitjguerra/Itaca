package com.csi.itaca.config.tools;

public class ConfiguratorException extends RuntimeException {

	private static final long serialVersionUID = -432215682799610230L;

	public ConfiguratorException(String message) {
		super(message);
	}

	public ConfiguratorException(String message, Throwable t) {
		super(message, t);
	}
}
