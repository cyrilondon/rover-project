package com.game.domain.model.validation;

import com.game.domain.model.exception.EntityValidationException;

public class GameDefaultValidationNotificationHandler implements ValidationNotificationHandler {

	private ValidationResult validationResult = new ValidationResult();

	@Override
	public void handleError(String errorMessage) {
		validationResult.addErrorMessage(errorMessage);
	}

	@Override
	public void checkValidationResult() {
		if (validationResult.isInError()) {
			throw new EntityValidationException(validationResult.getAllErrorMessages());
		}

	}

}
