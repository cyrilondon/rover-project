package com.game.domain.application;

import com.game.domain.application.command.MoveRoverCommand;
import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

/**
 * 
 *
 */
public interface GameService extends ApplicationService {

	/**
	 * TODO replace the arguments by command object
	 * 
	 * @param coordinates
	 */
	public void initializePlateau(TwoDimensionalCoordinates coordinates);

	/**
	 * 
	 * @param speed
	 * @param coordinates
	 */
	public void initializeRelativisticPlateau(int speed, TwoDimensionalCoordinates coordinates);

	/**
	 * Arguments to be refactored later in a Command object
	 * 
	 * @param coordinates
	 * @param orientation
	 */
	public void initializeRover(TwoDimensionalCoordinates coordinates, Orientation orientation);

	/**
	 * 
	 * @param roverName
	 * @param orientation
	 */
	public void execute(MoveRoverCommand command);

}
