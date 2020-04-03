package com.game.domain.model.service;

import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

public class RoverServiceImpl implements DomainService {
	
	
	public Rover initializeRover(TwoDimensionalCoordinates coordinates, Orientation orientation) {
		return new Rover(coordinates, orientation);
	}
	
	/**
	 * To be refactored later in Command object
	 * @param coordinates
	 * @param orientation
	 */
	public void moveRoverwithOrientation(Rover rover, Orientation orientation) {
		rover.setOrientation(orientation);
		rover.move();
	}

}
