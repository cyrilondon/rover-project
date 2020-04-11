package com.game.domain.model.exception;

public class EntityInitialisationException extends GameException {
	
	private static final long serialVersionUID = 1728437016696356759L;

	public EntityInitialisationException(String message) {
		super(message, GameExceptionLabels.ENTITY_VALIDATION_ERROR_CODE);
	}

}
