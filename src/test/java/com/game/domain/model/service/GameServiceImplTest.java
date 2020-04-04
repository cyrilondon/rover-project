package com.game.domain.model.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.game.domain.model.GameContext;
import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Plateau;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.IllegalArgumentGameException;

public class GameServiceImplTest {

	private static final int WIDTH = 5;

	private static final int HEIGHT = 5;

	private static final int X = 3;

	private static final int Y = 4;

	private GameContext gameContext = GameContext.getInstance();

	private GameService gameService = new GameServiceImpl();

	@BeforeMethod
	public void resetGame() {
		gameContext.reset();
	}

	@Test
	public void testInitializePlateau() {
		gameService.initializePlateau(new TwoDimensionalCoordinates(WIDTH, HEIGHT));
		Plateau plateau = gameContext.getPlateau();
		assertThat(plateau.getWidth()).isEqualTo(WIDTH);
		assertThat(plateau.getHeight()).isEqualTo(HEIGHT);
	}

//	@Test
//	public void testInitializeRover() {
//		gameContext.addBoard(getBoard());
//		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);
//		gameService.initializeRover(coordinates, Orientation.SOUTH);
//		Rover rover = gameContext.getRover(GameContext.ROVER_NAME_PREFIX + 1);
//		assertThat(rover.getCoordinates()).isEqualTo(new TwoDimensionalCoordinates(X, Y));
//		assertThat(rover.getOrientation()).isEqualTo(Orientation.SOUTH);
//		gameService.initializeRover(coordinates, Orientation.EAST);
//		Rover otherRover = gameContext.getRover(GameContext.ROVER_NAME_PREFIX + 2);
//		assertThat(otherRover.getName()).isNotNull();
//	}

	/**
	 * Expected error message: "[ERR-000] Missing Plateau configuration - It is not allowed to add a Rover. Please initialize the Plateau first."
	 */
	@Test
	public void testInitializeRoverWithoutPlateau() {
		Throwable thrown = catchThrowable(
				() -> gameService.initializeRover(new TwoDimensionalCoordinates(X, Y), Orientation.SOUTH));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
						String.format(GameExceptionLabels.ERROR_MESSAGE_SEPARATION_PATTERN, GameExceptionLabels.MISSING_PLATEAU_CONFIGURATION,
								GameExceptionLabels.NOT_ALLOWED_ADDING_ROVER_ERROR)));
	}

	private Plateau getPlateau() {
		TwoDimensions dimensions = new TwoDimensions(new TwoDimensionalCoordinates(WIDTH, HEIGHT));
		return new Plateau(dimensions);
	}

}
