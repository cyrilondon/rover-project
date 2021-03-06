package com.game.domain.model.exception;

public class RoverInitializationException extends EntityValidationException {

	private static final long serialVersionUID = 1L;

	public RoverInitializationException(String message) {
		super(message);
	}
	
	public RoverInitializationException(String message, Exception exception) {
		super(message, exception);
	}

}
