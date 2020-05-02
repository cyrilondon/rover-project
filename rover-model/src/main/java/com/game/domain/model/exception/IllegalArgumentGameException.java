package com.game.domain.model.exception;


public class IllegalArgumentGameException extends GameException {

	private static final long serialVersionUID = 1728437016696356759L;

	public IllegalArgumentGameException(String message) {
		super(message, GameExceptionLabels.ILLEGAL_ARGUMENT_CODE);
	}

}
