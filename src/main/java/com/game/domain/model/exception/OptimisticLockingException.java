package com.game.domain.model.exception;

public class OptimisticLockingException extends GameException {
	
	private static final long serialVersionUID = 1728437016696356759L;

	public OptimisticLockingException(String message) {
		super(message, GameExceptionLabels.CONCURRENT_MODIFICATION_ERROR_CODE);
	}
	
}
