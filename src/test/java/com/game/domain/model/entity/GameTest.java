package com.game.domain.model.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.IllegalArgumentGameException;

public class GameTest {

	private Game game = Game.getInstance();

	private static final int WIDTH = 3;

	private static final int HEIGHT = 3;

	@BeforeMethod
	public void resetGame() {
		game.reset();
	}

	@Test
	public void testGetGameInstance() {
		assertThat(game.getBoard()).isNull();
		assertThat(game.getNumberOfRobots()).isEqualTo(0);
	}

	@Test
	public void testGetSameGameInstance() {
		Game otherGame = Game.getInstance();
		assertThat(game).isSameAs(otherGame);
	}

	@Test
	public void testAddRobots() {
		game.configureBoard(getBoard());
		assertThat(game.getBoard().getWidth()).isEqualTo(WIDTH);
		assertThat(game.getBoard().getHeight()).isEqualTo(HEIGHT);
		game.addRobot(getRobot());
		assertThat(game.getRobot(Game.ROBOT_NAME_PREFIX + 1)).isNotNull();
		game.addRobot(getRobot());
		assertThat(game.getRobot(Game.ROBOT_NAME_PREFIX + 2)).isNotNull();
		assertThat(Game.getInstance().getNumberOfRobots()).isEqualTo(2);
	}

	@Test
	public void testRemoveRobots() {
		game.configureBoard(getBoard());
		game.addRobot(getRobot());
		assertThat(game.getRobot(Game.ROBOT_NAME_PREFIX + 1)).isNotNull();
		game.removeRobot(Game.ROBOT_NAME_PREFIX + 1);
		assertThat(game.getNumberOfRobots()).isEqualTo(0);
	}

	@Test
	public void testAddRobotsNotAllowed() {
		Throwable thrown = catchThrowable(() -> game.addRobot(getRobot()));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
						String.format(GameExceptionLabels.ERROR_MESSAGE_SEPARATION_PATTERN,
								GameExceptionLabels.MISSING_BOARD_CONFIGURATION,
								GameExceptionLabels.NOT_ALLOWED_ADDING_ROBOT_ERROR)));
	}

	@Test
	public void testReset() {
		game.configureBoard(getBoard());
		game.addRobot(getRobot());
		game.addRobot(getRobot());
		game.reset();
		assertThat(game.getNumberOfRobots()).isEqualTo(0);
		assertThat(game.isInitialized()).isFalse();
		game.configureBoard(getBoard());
		game.addRobot(getRobot());
		assertThat(game.getRobot(Game.ROBOT_NAME_PREFIX + 1)).isNotNull();
	}

	private Board getBoard() {
		TwoDimensions dimensions = new TwoDimensions(new TwoDimensionalCoordinates(3, 3));
		return new Board(dimensions);
	}

	private Robot getRobot() {
		return new Robot(new TwoDimensionalCoordinates(WIDTH, HEIGHT), Orientation.SOUTH);
	}

}
