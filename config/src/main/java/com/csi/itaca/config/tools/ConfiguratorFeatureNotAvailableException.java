package com.csi.itaca.config.tools;

public class ConfiguratorFeatureNotAvailableException extends RuntimeException {

	private static final long serialVersionUID = 6226954819772186736L;

	public ConfiguratorFeatureNotAvailableException(String message) {
		super(message);
	}

	public ConfiguratorFeatureNotAvailableException(String message, Throwable t) {
		super(message, t);
	}
}
