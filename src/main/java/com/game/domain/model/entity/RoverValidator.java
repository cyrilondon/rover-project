package com.game.domain.model.entity;

import com.game.domain.model.GameContext;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.validation.EntityValidator;
import com.game.domain.model.validation.ValidationNotificationHandler;


public class RoverValidator extends EntityValidator<Rover> {

	public RoverValidator(Rover rover, ValidationNotificationHandler handler) {
		super(rover, handler);
	}

	@Override
	protected void doValidate() {

		if (entity().getXPosition() < 0)
			this.notificationHandler()
					.handleError(String.format(GameExceptionLabels.ROVER_NEGATIVE_X, entity().getXPosition()));

		if (entity().getYPosition() < 0)
			this.notificationHandler()
					.handleError(String.format(GameExceptionLabels.ROVER_NEGATIVE_Y, entity().getYPosition()));

		if (entity().getXPosition() > GameContext.getInstance().getPlateau().getWidth())
			this.notificationHandler()
					.handleError(String.format(GameExceptionLabels.ROVER_X_OUT_OF_PLATEAU, entity().getXPosition(), GameContext.getInstance().getPlateau().getWidth()));
		
		if (entity().getYPosition() > GameContext.getInstance().getPlateau().getHeight())
			this.notificationHandler()
					.handleError(String.format(GameExceptionLabels.ROVER_Y_OUT_OF_PLATEAU, entity().getYPosition(), GameContext.getInstance().getPlateau().getHeight()));

	}

}
