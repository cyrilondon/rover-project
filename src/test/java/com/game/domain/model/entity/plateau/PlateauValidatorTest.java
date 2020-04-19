package com.game.domain.model.entity.plateau;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.UUID;

import org.testng.annotations.Test;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.entity.plateau.Plateau;
import com.game.domain.model.exception.EntityInitialisationException;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.validation.EntityDefaultValidationNotificationHandler;
import com.game.domain.model.validation.ValidationNotificationHandler;

public class PlateauValidatorTest {

	private final static int WIDTH = 3;

	private final static int NEGATIVE_WIDTH = -5;

	private final static int HEIGHT = 2;

	private final static int NEGATIVE_HEIGHT = -1;

	
	/**
	 * Expected errorMessage: "[ERR-001] Plateau width [-5] should be strictly positive"
	 */
	@Test
	public void testValidatorNegativeWidth() {

		ValidationNotificationHandler errorHandler = new EntityDefaultValidationNotificationHandler();

		TwoDimensions dimensions = new TwoDimensions(new TwoDimensionalCoordinates(NEGATIVE_WIDTH, HEIGHT));
		Plateau plateau = new Plateau(UUID.randomUUID(), dimensions);

		Throwable thrown = catchThrowable(() -> plateau.validate(errorHandler));
		assertThat(thrown).isInstanceOf(EntityInitialisationException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ENTITY_VALIDATION_ERROR_CODE,
						String.format(GameExceptionLabels.PLATEAU_NEGATIVE_WIDTH, NEGATIVE_WIDTH)));
	}

	/**
	 * Expected errorMessage: "[ERR-001] Plateau height [-5] should be strictly positive"
	 */
	@Test
	public void testValidatorNegativeHeight() {

		ValidationNotificationHandler errorHandler = new EntityDefaultValidationNotificationHandler();

		TwoDimensions dimensions = new TwoDimensions(new TwoDimensionalCoordinates(WIDTH, NEGATIVE_HEIGHT));
		Plateau plateau = new Plateau(UUID.randomUUID(), dimensions);

		Throwable thrown = catchThrowable(() -> plateau.validate(errorHandler));
		assertThat(thrown).isInstanceOf(EntityInitialisationException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ENTITY_VALIDATION_ERROR_CODE,
						String.format(GameExceptionLabels.PLATEAU_NEGATIVE_HEIGHT, NEGATIVE_HEIGHT)));
	}

	
	/**
	 * Expected errorMessage: "[ERR-001] Plateau width [-5] should be strictly positive, Plateau height [-1] should strictly positive"
	 */
	@Test
	public void testValidatorWithNegativeWidthAndHeight() {

		ValidationNotificationHandler errorHandler = new EntityDefaultValidationNotificationHandler();

		TwoDimensions dimensions = new TwoDimensions(new TwoDimensionalCoordinates(NEGATIVE_WIDTH, NEGATIVE_HEIGHT));
		Plateau plateau = new Plateau(UUID.randomUUID(), dimensions);

		Throwable thrown = catchThrowable(() -> plateau.validate(errorHandler));
		assertThat(thrown).isInstanceOf(EntityInitialisationException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ENTITY_VALIDATION_ERROR_CODE,
						String.format(GameExceptionLabels.PLATEAU_NEGATIVE_WIDTH, NEGATIVE_WIDTH) + ", "
								+ String.format(GameExceptionLabels.PLATEAU_NEGATIVE_HEIGHT, NEGATIVE_HEIGHT)));
	}
}
