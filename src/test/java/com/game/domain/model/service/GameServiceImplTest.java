package com.game.domain.model.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.game.domain.model.GameContext;
import com.game.domain.model.entity.Board;
import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Rover;
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
	public void testInitializeBoard() {
		gameService.initializeBoard(new TwoDimensionalCoordinates(WIDTH, HEIGHT));
		Board board = gameContext.getBoard();
		assertThat(board.getWidth()).isEqualTo(WIDTH);
		assertThat(board.getHeight()).isEqualTo(HEIGHT);
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
	 * Expected error message: "[ERR-000] Missing Board configuration - It is not allowed to add a Rover. Please initialize the Board first."
	 */
	@Test
	public void testInitializeRoverWithoutBoard() {
		Throwable thrown = catchThrowable(
				() -> gameService.initializeRover(new TwoDimensionalCoordinates(X, Y), Orientation.SOUTH));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
						String.format(GameExceptionLabels.ERROR_MESSAGE_SEPARATION_PATTERN, GameExceptionLabels.MISSING_BOARD_CONFIGURATION,
								GameExceptionLabels.NOT_ALLOWED_ADDING_ROVER_ERROR)));
	}

	private Board getBoard() {
		TwoDimensions dimensions = new TwoDimensions(new TwoDimensionalCoordinates(WIDTH, HEIGHT));
		return new Board(dimensions);
	}

}
