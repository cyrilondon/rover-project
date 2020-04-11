package com.game.domain.model.exception;

public class IllegalRoverPositionException extends GameException {
	
	private static final long serialVersionUID = 1728437016696356759L;

	public IllegalRoverPositionException(String message) {
		super(message, GameExceptionLabels.ROVER_ILLEGAL_POSITION_ERROR_CODE);
	}

}
