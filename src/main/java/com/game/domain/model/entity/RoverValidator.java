package com.game.domain.model.entity;

import com.game.domain.application.GameContext;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.validation.EntityValidator;
import com.game.domain.model.validation.ValidationNotificationHandler;

public class RoverValidator extends EntityValidator<Rover> {

	public RoverValidator(Rover rover, ValidationNotificationHandler handler) {
		super(rover, handler);
	}

	@Override
	protected void doValidate() {

		if (isXPositionNegative())
			this.notificationHandler()
					.handleError(String.format(GameExceptionLabels.ROVER_NEGATIVE_X, entity().getXPosition()));

		if (isYPositionNegative())
			this.notificationHandler()
					.handleError(String.format(GameExceptionLabels.ROVER_NEGATIVE_Y, entity().getYPosition()));

		if (isXPositionOutOfBoard())
			this.notificationHandler().handleError(String.format(GameExceptionLabels.ROVER_X_OUT_OF_PLATEAU,
					entity().getXPosition(), GameContext.getInstance().getPlateau(entity().getId().getPlateauUuid()).getWidth()));

		if (isYPositionOutOfBoard())
			this.notificationHandler().handleError(String.format(GameExceptionLabels.ROVER_Y_OUT_OF_PLATEAU,
					entity().getYPosition(), GameContext.getInstance().getPlateau(entity().getId().getPlateauUuid()).getHeight()));

		if (areBothCoordinatesPositive() && areBothCoordinatesInsideTheBoard() && positionAlreadyBusy())
			this.notificationHandler().handleError(String.format(GameExceptionLabels.PLATEAU_LOCATION_ALREADY_SET,
					entity().getXPosition(), entity().getYPosition()));

	}


	private boolean isXPositionNegative() {
		return entity().getXPosition() < 0;
	}

	private boolean isYPositionNegative() {
		return entity().getYPosition() < 0;
	}

	private boolean areBothCoordinatesPositive() {
		return !(isXPositionNegative() || isYPositionNegative());
	}

	private boolean isXPositionOutOfBoard() {
		return entity().getXPosition() > GameContext.getInstance().getPlateau(entity().getId().getPlateauUuid()).getWidth();
	}

	private boolean isYPositionOutOfBoard() {
		return entity().getYPosition() > GameContext.getInstance().getPlateau(entity().getId().getPlateauUuid()).getHeight();
	}

	private boolean areBothCoordinatesInsideTheBoard() {
		return !(isXPositionOutOfBoard() || isYPositionOutOfBoard());
	}
	
	private boolean positionAlreadyBusy() {
		return GameContext.getInstance().getPlateau(entity().getId().getPlateauUuid())
				.isLocationBusy(new TwoDimensionalCoordinates(entity().getXPosition(), entity().getYPosition()));
	}

}
