package com.game.domain.model.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.UUID;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.game.domain.application.GameContext;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.exception.EntityInitialisationException;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.PlateauLocationAlreadySetException;
import com.game.domain.model.validation.EntityDefaultValidationNotificationHandler;
import com.game.domain.model.validation.ValidationNotificationHandler;

public class RoverValidatorTest {

	private final int POSITIVE_X_POSITION = 3;

	private final int NEGATIVE_X_POSITION = -POSITIVE_X_POSITION;

	private final int POSITIVE_Y_POSITION = 4;

	private final int NEGATIVE_Y_POSITION = -POSITIVE_Y_POSITION;

	private final int PLATEAU_WIDTH = 5;

	private final int PLATEAU_HEIGHT = PLATEAU_WIDTH;

	private final int PLATEAU_SMALL_X = 2;

	private final int PLATEAU_SMALL_Y = PLATEAU_SMALL_X;

	private GameContext gameContext = GameContext.getInstance();

	@BeforeMethod
	public void resetGame() {
		gameContext.reset();
	}

	/**
	 * Expected errorMessage: "[ERR-001] Rover X-position [-3] should be strictly
	 * positive"
	 */
	@Test
	public void testValidatorNegativeXPosition() {

		addPlateau(PLATEAU_WIDTH, PLATEAU_HEIGHT);

		ValidationNotificationHandler errorHandler = new EntityDefaultValidationNotificationHandler();

		Rover rover = getRover(NEGATIVE_X_POSITION, POSITIVE_Y_POSITION);

		Throwable thrown = catchThrowable(() -> rover.validate(errorHandler));
		assertThat(thrown).isInstanceOf(EntityInitialisationException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ENTITY_VALIDATION_ERROR_CODE,
						String.format(GameExceptionLabels.ROVER_NEGATIVE_X, rover.getXPosition())));
	}

	/**
	 * Expected errorMessage: "[ERR-001] Rover Y-position [-4] should be strictly
	 * positive"
	 */
	@Test
	public void testValidatorNegativeYPosition() {

		addPlateau(PLATEAU_WIDTH, PLATEAU_HEIGHT);

		ValidationNotificationHandler errorHandler = new EntityDefaultValidationNotificationHandler();

		Rover rover = getRover(POSITIVE_X_POSITION, NEGATIVE_Y_POSITION);

		Throwable thrown = catchThrowable(() -> rover.validate(errorHandler));
		assertThat(thrown).isInstanceOf(EntityInitialisationException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ENTITY_VALIDATION_ERROR_CODE,
						String.format(GameExceptionLabels.ROVER_NEGATIVE_Y, rover.getYPosition())));
	}

	/**
	 * [ERR-001] Rover X-position [-3] should be strictly positive, Rover Y-position
	 * [-4] should be strictly positive"
	 */
	@Test
	public void testValidatorNegativeXYPosition() {

		addPlateau(PLATEAU_WIDTH, PLATEAU_HEIGHT);

		ValidationNotificationHandler errorHandler = new EntityDefaultValidationNotificationHandler();

		Rover rover = getRover(NEGATIVE_X_POSITION, NEGATIVE_Y_POSITION);

		Throwable thrown = catchThrowable(() -> rover.validate(errorHandler));
		assertThat(thrown).isInstanceOf(EntityInitialisationException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ENTITY_VALIDATION_ERROR_CODE,
						String.format(GameExceptionLabels.ROVER_NEGATIVE_X, rover.getXPosition()) + ", "
								+ String.format(GameExceptionLabels.ROVER_NEGATIVE_Y, rover.getYPosition())));
	}

	/**
	 * [ERR-001] Rover with X-position [3] is out of the plateau with width [2],
	 * Rover with Y-position [4] is out of the plateau with height [2]"
	 */
	@Test
	public void testValidatorOutOfPlateauXYPosition() {

		addPlateau(PLATEAU_SMALL_X, PLATEAU_SMALL_X);

		ValidationNotificationHandler errorHandler = new EntityDefaultValidationNotificationHandler();

		Rover rover = getRover(POSITIVE_X_POSITION, POSITIVE_Y_POSITION);

		Throwable thrown = catchThrowable(() -> rover.validate(errorHandler));
		assertThat(thrown).isInstanceOf(EntityInitialisationException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ENTITY_VALIDATION_ERROR_CODE,
						String.format(GameExceptionLabels.ROVER_X_OUT_OF_PLATEAU, rover.getXPosition(), PLATEAU_SMALL_X)
								+ ", " + String.format(GameExceptionLabels.ROVER_Y_OUT_OF_PLATEAU, rover.getYPosition(),
										PLATEAU_SMALL_Y)));
	}
	
	/**
	 * com.game.domain.model.exception.PlateauLocationAlreadySetException: [ERR-003] There is already a Rover at position X = [3] and Y = [4]
	 */
	@Test
	public void testAlreadySetPosition() {

		addPlateau(PLATEAU_WIDTH, PLATEAU_HEIGHT);

		ValidationNotificationHandler errorHandler = new EntityDefaultValidationNotificationHandler();
		
		gameContext.getPlateau().setLocationBusy(new TwoDimensionalCoordinates(POSITIVE_X_POSITION, POSITIVE_Y_POSITION));

		Rover rover = getRover(POSITIVE_X_POSITION, POSITIVE_Y_POSITION);

		Throwable thrown = catchThrowable(() -> rover.validate(errorHandler));
		assertThat(thrown).isInstanceOf(PlateauLocationAlreadySetException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.PLATEAU_LOCATION_ERROR_CODE,
						String.format(GameExceptionLabels.PLATEAU_LOCATION_ALREADY_SET, rover.getXPosition(), rover.getYPosition())));
	}

	private void addPlateau(int width, int height) {
		gameContext.addPlateau(
				new Plateau(UUID.randomUUID(), new TwoDimensions(new TwoDimensionalCoordinates(width, height))).initializeLocations());
	}

	private Rover getRover(int X, int Y) {
		return new Rover(new RoverIdentifier(UUID.randomUUID(), GameContext.ROVER_NAME_PREFIX), new TwoDimensionalCoordinates(X, Y), Orientation.SOUTH);
	}

}
