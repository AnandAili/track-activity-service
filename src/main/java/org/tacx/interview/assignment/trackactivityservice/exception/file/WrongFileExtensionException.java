package org.tacx.interview.assignment.trackactivityservice.exception.file;

public class WrongFileExtensionException extends RuntimeException {

	public WrongFileExtensionException(String message) {
		super(message);
	}

	public WrongFileExtensionException(String message, Throwable cause) {
		super(message, cause);
	}

}
