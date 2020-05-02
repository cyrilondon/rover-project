package com.game.domain.model.validation;

import com.game.domain.model.exception.IllegalRoverMoveException;

public class RoverMovedPositionValidationNotificationHandler extends EntityDefaultValidationNotificationHandler {

	@Override
	public void handleError(String errorMessage) {
		super.handleError(errorMessage);

	}

	@Override
	public void checkValidationResult() {
		if (validationResult.isInError()) {
			throw new IllegalRoverMoveException(validationResult.getAllErrorMessages());
		}
	}

}
