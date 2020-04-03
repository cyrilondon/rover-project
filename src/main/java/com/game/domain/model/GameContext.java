package com.game.domain.model;

import java.util.concurrent.atomic.AtomicInteger;

import com.game.core.validation.ArgumentCheck;
import com.game.domain.model.entity.Board;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.IllegalArgumentGameException;

/**
 * Application context whose responsibility is to keep track of the game state
 *
 */
public class GameContext {

	public static final String ROVER_NAME_PREFIX = "ROVER_";

	private int roverStepLength = 1;

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
	
	public static GameContext getInstance(int step) {
		 GAME_CONTEXT.roverStepLength = step;
		 return GAME_CONTEXT;
	}

	public int getRoverStepLength() {
		return roverStepLength;
	}

	public void addBoard(Board board) {
		GAME.board = ArgumentCheck.preNotNull(board, GameExceptionLabels.MISSING_BOARD_CONFIGURATION);
		initialized = true;
	}

	public void addRover(Rover robot) {
		if (!isInitialized())
			throw new IllegalArgumentGameException(String.format(GameExceptionLabels.ERROR_MESSAGE_SEPARATION_PATTERN,
					GameExceptionLabels.MISSING_BOARD_CONFIGURATION,
					GameExceptionLabels.NOT_ALLOWED_ADDING_ROVER_ERROR));
		int robotNumber = counter.addAndGet(1);
		robot.setName(ROVER_NAME_PREFIX + robotNumber);
		GAME.addRover(robot);
	}

	public boolean isInitialized() {
		return initialized;
	}


	public void reset() {
		initialized = false;
		GAME.board = null;
		GAME.rovers.clear();
		counter.set(0);
		roverStepLength = 1;
	}

	public Board getBoard() {
		return GAME.getBoard();
	}

	public Rover getRover(String name) {
		return GAME.getRover(name);
	}

	public int getNumberOfRovers() {
		return GAME.getNumberOfRovers();
	}

	public void removeRover(String roverName) {
		GAME.removeRover(roverName);
	}

}
