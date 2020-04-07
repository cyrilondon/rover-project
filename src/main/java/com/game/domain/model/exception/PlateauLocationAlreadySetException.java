package com.game.domain.model.exception;

public class PlateauLocationAlreadySetException extends GameException {


	private static final long serialVersionUID = 1998077853323471491L;

	public PlateauLocationAlreadySetException(String message) {
		super(message, GameExceptionLabels.PLATEAU_LOCATION_ERROR_CODE);
	}

}
