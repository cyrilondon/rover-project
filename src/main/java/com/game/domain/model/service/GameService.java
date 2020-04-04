package com.game.domain.model.service;

import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

/**
 * 
 *
 */
public interface GameService extends ApplicationService {

	
	/**
	 * TODO replace the arguments by command object
	 * @param  coordinates
	 */
	public void initializeBoard(TwoDimensionalCoordinates coordinates);

	/**
	 * Arguments to be refactored later in a Command object
	 * 
	 * @param coordinates
	 * @param orientation
	 */
	public void initializeRover(TwoDimensionalCoordinates coordinates, Orientation orientation);

	public void moveRoverwithOrientation(String roverName, Orientation orientation);

}
