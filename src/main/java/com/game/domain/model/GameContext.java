package com.game.domain.model;

import java.util.concurrent.atomic.AtomicInteger;

import com.game.core.validation.ArgumentCheck;
import com.game.domain.model.entity.Board;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.service.BoardServiceImpl;
import com.game.domain.model.service.RoverServiceImpl;
import com.game.domain.model.service.ServiceLocator;

/**
 * Application context whose responsibility is to keep track of the game state.
 * Exposed as a singleton to the rest of the application via
 * {@link GameContext#getInstance()} or {@link GameContext#getInstance(int step)} if
 * we want the Rover to move with a step length different than the default one = 1
 * Equivalent to an Application Spring context
 *
 */
public class GameContext {

	public static final String ROVER_NAME_PREFIX = "ROVER_";
	
	public static final int ROVER_STEP_LENGTH = 1;

	/**
	 * By default, the rover moves one step forward
	 */
	private int roverStepLength = ROVER_STEP_LENGTH;

	/**
	 * Game is initialized if only the board has been initialized
	 */
	private boolean initialized;

	private static GameContext GAME_CONTEXT = new GameContext();
	
	AtomicInteger counter = new AtomicInteger(0);

	private Game GAME = new Game();
	
	private GameContext() {
	}

	public static GameContext getInstance() {
		return GAME_CONTEXT;
	}
	
	public static GameContext getInstance(int step) {
		 GAME_CONTEXT.roverStepLength = step;
		 return GAME_CONTEXT;
	}
	
	public RoverServiceImpl getRoverService() {
		return (RoverServiceImpl) ServiceLocator.getRoverService();
	}

	public BoardServiceImpl getBoardService() {
		return (BoardServiceImpl) ServiceLocator.getBoardService();
	}
	

	public int getRoverStepLength() {
		return roverStepLength;
	}

	
	/**
	 * Adding a board to the game will initialize the game
	 * Rovers are then allowed to be added/initialized as well
	 */
	public void addBoard(Board board) {
		reset();
		GAME.board = ArgumentCheck.preNotNull(board, GameExceptionLabels.MISSING_BOARD_CONFIGURATION);
		initialized = true;
	}

	public boolean isInitialized() {
		return initialized;
	}


	public void reset() {
		initialized = false;
		GAME.board = null;
		roverStepLength = 1;
		counter = new AtomicInteger(0);
	}

	public Board getBoard() {
		return GAME.getBoard();
	}
	
	public AtomicInteger getCounter() {
		return counter;
	}


}
