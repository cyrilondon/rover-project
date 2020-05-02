package com.game.domain.model.exception;

public class EntityNotFoundException extends GameException {

	private static final long serialVersionUID = 1L;
	
	private static String ERROR_MSG = GameExceptionLabels.ENTITY_NOT_FOUND;
	
	public EntityNotFoundException(String entity, Object key) {
		super(String.format(ERROR_MSG, entity, key), GameExceptionLabels.ENTITY_NOT_FOUND_ERROR_CODE);
	}

}
