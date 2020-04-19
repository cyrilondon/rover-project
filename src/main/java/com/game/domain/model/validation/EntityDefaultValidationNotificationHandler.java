package com.game.domain.model.validation;

import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.PlateauLocationAlreadySetException;

public class EntityDefaultValidationNotificationHandler implements ValidationNotificationHandler {

	protected ValidationResult validationResult = new ValidationResult();

	@Override
	public void handleError(String errorMessage) {
		validationResult.addErrorMessage(errorMessage);
	}

	@Override
	public void checkValidationResult() {
		if (validationResult.isInError()) {
			String allErrorMessages = validationResult.getAllErrorMessages();
			// in case there is only an issue where the board is already set
			// we want to throw a more specific error with specific code
			if (allErrorMessages.startsWith(GameExceptionLabels.PLATEAU_LOCATION_ALREADY_SET_START)) {
				throw new PlateauLocationAlreadySetException(allErrorMessages);
			}
			throw new IllegalArgumentException(allErrorMessages);
		}

	}

}
