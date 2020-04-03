package com.game.domain.model.service;

import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

public interface GameService extends ApplicationService {

	public void initializeBoard(TwoDimensionalCoordinates coordinates);

	/**
	 * Arguments to be refactored later in a Command object
	 * 
	 * @param coordinates
	 * @param orientation
	 */
	public Rover initializeRover(TwoDimensionalCoordinates coordinates, Orientation orientation);

	public void moveRoverwithOrientation(Rover rover, Orientation orientation);

}
