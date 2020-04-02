package com.game.domain.model.entity;

import com.game.core.validation.ArgumentCheck;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensionalSpace;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.validation.ValidationNotificationHandler;

public class Board implements TwoDimensionalSpace, Entity {

	private TwoDimensionalSpace dimensions;

	public Board(TwoDimensionalSpace dimensions) {
		this.dimensions = ArgumentCheck.preNotNull(dimensions, GameExceptionLabels.MISSING_BOARD_DIMENSIONS);
	}

	public Board() {
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
	public void validate(ValidationNotificationHandler handler) {
		new BoardValidator(this, handler).validate();
	}

}
