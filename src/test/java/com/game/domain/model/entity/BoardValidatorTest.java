package com.game.domain.model.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.testng.annotations.Test;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.exception.EntityValidationException;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.validation.GameDefaultValidationNotificationHandler;
import com.game.domain.model.validation.ValidationNotificationHandler;

public class BoardValidatorTest {

	private final static int WIDTH = 3;

	private final static int NEGATIVE_WIDTH = -5;

	private final static int HEIGHT = 2;

	private final static int NEGATIVE_HEIGHT = -1;

	
	/**
	 * Expected errorMessage: "[ERR-001] Board width [-5] should be strictly positive"
	 */
	@Test
	public void testValidatorNegativeWidth() {

		ValidationNotificationHandler errorHandler = new GameDefaultValidationNotificationHandler();

		TwoDimensions dimensions = new TwoDimensions(new TwoDimensionalCoordinates(NEGATIVE_WIDTH, HEIGHT));
		Board board = new Board(dimensions);

		Throwable thrown = catchThrowable(() -> board.validate(errorHandler));
		assertThat(thrown).isInstanceOf(EntityValidationException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ENTITY_VALIDATION_ERROR_CODE,
						String.format(GameExceptionLabels.BOARD_NEGATIVE_WIDTH, NEGATIVE_WIDTH)));
	}

	/**
	 * Expected errorMessage: "[ERR-001] Board height [-5] should be strictly positive"
	 */
	@Test
	public void testValidatorNegativeHeight() {

		ValidationNotificationHandler errorHandler = new GameDefaultValidationNotificationHandler();

		TwoDimensions dimensions = new TwoDimensions(new TwoDimensionalCoordinates(WIDTH, NEGATIVE_HEIGHT));
		Board board = new Board(dimensions);

		Throwable thrown = catchThrowable(() -> board.validate(errorHandler));
		assertThat(thrown).isInstanceOf(EntityValidationException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ENTITY_VALIDATION_ERROR_CODE,
						String.format(GameExceptionLabels.BOARD_NEGATIVE_HEIGHT, NEGATIVE_HEIGHT)));
	}

	
	/**
	 * Expected errorMessage: "[ERR-001] Board width [-5] should be strictly positive, Board height [-1] should strictly positive"
	 */
	@Test
	public void testValidatorWithNegativeWidthAndHeight() {

		ValidationNotificationHandler errorHandler = new GameDefaultValidationNotificationHandler();

		TwoDimensions dimensions = new TwoDimensions(new TwoDimensionalCoordinates(NEGATIVE_WIDTH, NEGATIVE_HEIGHT));
		Board board = new Board(dimensions);

		Throwable thrown = catchThrowable(() -> board.validate(errorHandler));
		assertThat(thrown).isInstanceOf(EntityValidationException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ENTITY_VALIDATION_ERROR_CODE,
						String.format(GameExceptionLabels.BOARD_NEGATIVE_WIDTH, NEGATIVE_WIDTH) + ", "
								+ String.format(GameExceptionLabels.BOARD_NEGATIVE_HEIGHT, NEGATIVE_HEIGHT)));
	}
}
