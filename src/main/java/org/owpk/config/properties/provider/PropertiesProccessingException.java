package org.owpk.config.properties.provider;

public class PropertiesProccessingException extends RuntimeException {
	public PropertiesProccessingException(String message) {
		super(message);
	}

	public PropertiesProccessingException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropertiesProccessingException(Throwable cause) {
		super(cause);
	}

}
