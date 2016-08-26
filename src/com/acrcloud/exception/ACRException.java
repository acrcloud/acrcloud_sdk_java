package com.acrcloud.exception;

public class ACRException extends Exception {

	public ACRException() {
		super();
	}

	public ACRException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ACRException(String message, Throwable cause) {
		super(message, cause);
	}

	public ACRException(String message) {
		super(message);
	}

	public ACRException(Throwable cause) {
		super(cause);
	}

}
