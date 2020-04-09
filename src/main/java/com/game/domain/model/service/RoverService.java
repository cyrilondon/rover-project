package com.game.domain.model.service;

import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.entity.RoverIdentifier;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

public interface RoverService extends DomainService {
		
	void updateRover(Rover rover);

	void initializeRover(RoverIdentifier id, TwoDimensionalCoordinates coordinates, Orientation orientation);

	void faceToOrientation(RoverIdentifier id, Orientation orientation);

	void moveRoverNumberOfTimes(RoverIdentifier id, int times);

	Rover getRover(RoverIdentifier id);


}
