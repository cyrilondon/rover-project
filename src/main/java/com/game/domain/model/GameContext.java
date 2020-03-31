package com.game.domain.model;

import java.util.concurrent.atomic.AtomicInteger;

import com.game.core.validation.ArgumentCheck;
import com.game.domain.model.entity.Board;
import com.game.domain.model.entity.Robot;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.IllegalArgumentGameException;

/**
 * Application context whose responsibility is to keep track of the game state
 *
 */
public class GameContext {

	/**
	 * @Todo To add in Robot entity or leave here?
	 */
	public static final String ROBOT_NAME_PREFIX = "ROBOT_";

	private static final int STEP_LENGTH = 1;

	private GameContext() {

	}

	/**
	 * Game is initialized if only the board has been initialized
	 */
	private boolean initialized;

	private static GameContext GAME_CONTEXT = new GameContext();

	private AtomicInteger counter = new AtomicInteger(0);

	private Game GAME = new Game();

	public static GameContext getInstance() {
		return GAME_CONTEXT;
	}

	public void configureBoard(Board board) {
		GAME.board = ArgumentCheck.preNotNull(board, GameExceptionLabels.MISSING_BOARD_CONFIGURATION);
		initialized = true;
	}

	public void addRobot(Robot robot) {
		if (!isInitialized())
			throw new IllegalArgumentGameException(String.format(GameExceptionLabels.ERROR_MESSAGE_SEPARATION_PATTERN,
					GameExceptionLabels.MISSING_BOARD_CONFIGURATION,
					GameExceptionLabels.NOT_ALLOWED_ADDING_ROBOT_ERROR));
		int robotNumber = counter.addAndGet(1);
		robot.setName(ROBOT_NAME_PREFIX + robotNumber);
		GAME.addRobot(robot);
	}

	public boolean isInitialized() {
		return initialized;
	}

	public int getStep_length() {
		return STEP_LENGTH;
	}

	public void reset() {
		initialized = false;
		GAME.board = null;
		GAME.robots.clear();
		counter.set(0);
	}

	public Board getBoard() {
		return GAME.getBoard();
	}

	public Robot getRobot(String name) {
		return GAME.getRobot(name);
	}

	public int getNumberOfRobots() {
		return GAME.getNumberOfRobots();
	}

	public void removeRobot(String robotName) {
		GAME.removeRobot(robotName);
	}

}
