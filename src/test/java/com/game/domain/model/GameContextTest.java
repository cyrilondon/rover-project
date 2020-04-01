package com.game.domain.model;

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

public class GameContextTest {

	private GameContext gameContext = GameContext.getInstance();

	private static final int WIDTH = 3;

	private static final int HEIGHT = 3;

	@BeforeMethod
	public void resetGame() {
		gameContext.reset();
	}

	@Test
	public void testGetGameInstance() {
		assertThat(gameContext.getBoard()).isNull();
		assertThat(gameContext.getNumberOfRovers()).isEqualTo(0);
	}
	
	@Test
	public void testGetGameInstanceWithStep() {
		GameContext gameContextWithStep = GameContext.getInstance(5);
		assertThat(gameContextWithStep.getRover_step_length()).isEqualTo(5);
	}


	@Test
	public void testAddRovers() {
		gameContext.configureBoard(getBoard());
		assertThat(gameContext.getBoard().getWidth()).isEqualTo(WIDTH);
		assertThat(gameContext.getBoard().getHeight()).isEqualTo(HEIGHT);
		gameContext.addRover(getRover());
		assertThat(gameContext.getRover(GameContext.ROVER_NAME_PREFIX + 1)).isNotNull();
		gameContext.addRover(getRover());
		assertThat(gameContext.getRover(GameContext.ROVER_NAME_PREFIX + 2)).isNotNull();
		assertThat(gameContext.getNumberOfRovers()).isEqualTo(2);
	}

	@Test
	public void testRemoveRover() {
		gameContext.configureBoard(getBoard());
		gameContext.addRover(getRover());
		assertThat(gameContext.getRover(GameContext.ROVER_NAME_PREFIX + 1)).isNotNull();
		gameContext.removeRover(GameContext.ROVER_NAME_PREFIX + 1);
		assertThat(gameContext.getNumberOfRovers()).isEqualTo(0);
	}

	@Test
	public void testAddRoverNotAllowed() {
		Throwable thrown = catchThrowable(() -> gameContext.addRover(getRover()));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
						String.format(GameExceptionLabels.ERROR_MESSAGE_SEPARATION_PATTERN,
								GameExceptionLabels.MISSING_BOARD_CONFIGURATION,
								GameExceptionLabels.NOT_ALLOWED_ADDING_ROVER_ERROR)));
	}

	@Test
	public void testReset() {
		gameContext.configureBoard(getBoard());
		gameContext.addRover(getRover());
		gameContext.addRover(getRover());
		gameContext.reset();
		assertThat(gameContext.getNumberOfRovers()).isEqualTo(0);
		assertThat(gameContext.isInitialized()).isFalse();
		gameContext.configureBoard(getBoard());
		gameContext.addRover(getRover());
		assertThat(gameContext.getRover(GameContext.ROVER_NAME_PREFIX + 1)).isNotNull();
	}

	private Board getBoard() {
		TwoDimensions dimensions = new TwoDimensions(new TwoDimensionalCoordinates(3, 3));
		return new Board(dimensions);
	}

	private Rover getRover() {
		return new Rover(new TwoDimensionalCoordinates(WIDTH, HEIGHT), Orientation.SOUTH);
	}

}
