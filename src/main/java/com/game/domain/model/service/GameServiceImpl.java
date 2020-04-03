package com.game.domain.model.service;

import com.game.domain.model.GameContext;
import com.game.domain.model.entity.Board;
import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

/**
 * Application service which orchestrates the application process among the two Domain services
 * {@link RoverServiceImpl} and {@link BoardServiceImpl}
 * and the {@link GameContext} application state.
 *
 */
public class GameServiceImpl implements GameService {

	RoverServiceImpl roverService = (RoverServiceImpl) ServiceLocator.getRoverService();

	BoardServiceImpl boardService = (BoardServiceImpl) ServiceLocator.getBoardService();

	GameContext gameContext = GameContext.getInstance();
	
	public void initializeBoard(TwoDimensionalCoordinates coordinates) {
		Board board = boardService.initializeBoard(coordinates);
		GameContext.getInstance().addBoard(board);
	}
	
	/**
	 *Arguments to be refactored later in a Command object
	 * 
	 * @param coordinates
	 * @param orientation
	 */
	public Rover initializeRover(TwoDimensionalCoordinates coordinates, Orientation orientation) {
		Rover rover = roverService.initializeRover(coordinates, orientation);
		GameContext.getInstance().addRover(rover);
		return rover;
	}

	public void moveRoverwithOrientation(Rover rover, Orientation orientation) {
		roverService.moveRoverwithOrientation(rover, orientation);
	}

}
