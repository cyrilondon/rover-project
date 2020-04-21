package com.game.domain.model.exception;

public class EntityValidationException extends GameException {
	
	private static final long serialVersionUID = 1728437016696356759L;

	public EntityValidationException(String message) {
		super(message, GameExceptionLabels.ENTITY_VALIDATION_ERROR_CODE);
	}
	
	public EntityValidationException(String message, Exception exception) {
		super(message, GameExceptionLabels.ENTITY_VALIDATION_ERROR_CODE, exception);
	}

}
