package com.game.domain.model.entity;

import com.game.core.validation.ArgumentCheck;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensionalSpace;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.validation.ValidationNotificationHandler;

public class Plateau implements Entity<Plateau>, TwoDimensionalSpace {

	private TwoDimensionalSpace dimensions;

	public Plateau(TwoDimensionalSpace dimensions) {
		this.dimensions = ArgumentCheck.preNotNull(dimensions, GameExceptionLabels.MISSING_PLATEAU_DIMENSIONS);
	}

	public Plateau() {
		this.dimensions = new TwoDimensions(
				new TwoDimensionalCoordinates(TwoDimensionalSpace.DEFAULT_WIDTH, TwoDimensionalSpace.DEFAULT_HEIGHT));
	}

	public int getWidth() {
		return dimensions.getWidth();
	}

	public int getHeight() {
		return dimensions.getHeight();
	}

	@Override
	public Plateau validate(ValidationNotificationHandler handler) {
		return new PlateauValidator(this, handler).validate();
	}

}