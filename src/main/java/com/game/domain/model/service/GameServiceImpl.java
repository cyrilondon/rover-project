package com.game.domain.model.service;

import com.game.domain.model.GameContext;
import com.game.domain.model.entity.Board;
import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.IllegalArgumentGameException;

/**
 * Application service which acts as a facade to the application and delegates
 * the execution of the process to the two Domain services
 * {@link RoverServiceImpl}, {@link BoardServiceImpl}  and the Application State
 * {@link GameContext}
 * All the write commands should have return type = void
 *
 */
public class GameServiceImpl implements GameService {
	
	GameContext gameContext = GameContext.getInstance();

	BoardServiceImpl boardService = gameContext.getBoardService();

	RoverServiceImpl roverService = gameContext.getRoverService();

	public void initializeBoard(TwoDimensionalCoordinates coordinates) {
		Board board = boardService.initializeBoard(coordinates);
		// once initialized, we want to keep track of the Board as in-memory singleton instance during the game lifetime
		// i.e no need to go back to the Board repository each time it is needed 
		// (this in contrary to what happens for the rover objects which are fetched each time from the Rover Repository)
		gameContext.addBoard(board);
	}

	public void initializeRover(TwoDimensionalCoordinates coordinates, Orientation orientation) {
		if (!gameContext.isInitialized())
			throw new IllegalArgumentGameException(String.format(GameExceptionLabels.ERROR_MESSAGE_SEPARATION_PATTERN,
					GameExceptionLabels.MISSING_BOARD_CONFIGURATION,
					GameExceptionLabels.NOT_ALLOWED_ADDING_ROVER_ERROR));
		int robotNumber = gameContext.getCounter().addAndGet(1);
		roverService.initializeRover(GameContext.ROVER_NAME_PREFIX + robotNumber, coordinates, orientation);
	}

	public void moveRoverwithOrientation(String roverName, Orientation orientation) {
		roverService.moveRoverwithOrientation(roverName, orientation);
	}

}
