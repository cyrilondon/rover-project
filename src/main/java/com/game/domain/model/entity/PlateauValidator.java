package com.game.domain.model.entity;

import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.validation.EntityValidator;
import com.game.domain.model.validation.ValidationNotificationHandler;

public class PlateauValidator extends EntityValidator<Plateau> {

	public PlateauValidator(Plateau plateau, ValidationNotificationHandler handler) {
		super(plateau, handler);
	}

	@Override
	public void doValidate() {
		if (entity().getWidth() <= 0)
			this.notificationHandler()
					.handleError(String.format(GameExceptionLabels.PLATEAU_NEGATIVE_WIDTH, entity().getWidth()));
		
		if (entity().getHeight() <= 0)
			this.notificationHandler()
					.handleError(String.format(GameExceptionLabels.PLATEAU_NEGATIVE_HEIGHT, entity().getHeight()));
	}

}
