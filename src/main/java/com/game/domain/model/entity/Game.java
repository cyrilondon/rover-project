package com.game.domain.model.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.game.core.validation.ArgumentCheck;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.IllegalArgumentGameException;

public class Game {

	private Game() {
	}

	/**
	 * @Todo To add in Robot entity or leave here?
	 */
	public static final String ROBOT_NAME_PREFIX = "ROBOT_";

	/**
	 * Game is initialized if only the board has been initialized
	 */
	private boolean initialized;

	private AtomicInteger counter = new AtomicInteger(0);

	private static Game soleInstance = new Game();

	private Board board;

	private Map<String, Robot> robots = new ConcurrentHashMap<>();

	public static Game getInstance() {
		return soleInstance;
	}

	public void configureBoard(Board board) {
		this.board = ArgumentCheck.preNotNull(board, GameExceptionLabels.MISSING_BOARD_CONFIGURATION);
		setInitialized(true);
	}

	public Board getBoard() {
		return board;
	}

	public Robot getRobot(String name) {
		return robots.get(name);
	}

	public void addRobot(Robot robot) {
		if (!isInitialized())
			throw new IllegalArgumentGameException(String.format(GameExceptionLabels.ERROR_MESSAGE_SEPARATION_PATTERN,
					GameExceptionLabels.MISSING_BOARD_CONFIGURATION,
					GameExceptionLabels.NOT_ALLOWED_ADDING_ROBOT_ERROR));
		int robotNumber = counter.addAndGet(1);
		robots.putIfAbsent(ROBOT_NAME_PREFIX + robotNumber, robot);
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	/**
	 * In case for example of a robot moving out of the board, it will be removed
	 * from the game
	 * 
	 * @param robot
	 */
	public void removeRobot(String robotName) {
		robots.remove(robotName);
	}

	public int getNumberOfRobots() {
		return robots.size();
	}

	public void reset() {
		initialized = false;
		board = null;
		robots.clear();
		counter.set(0);
	}
}
