package com.game.domain.model.service;

import java.util.List;
import java.util.UUID;

import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.entity.RoverIdentifier;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.event.DomainEvent;

public interface RoverService extends DomainService {
		
	
	void initializeRover(RoverIdentifier id, TwoDimensionalCoordinates coordinates, Orientation orientation);

	void moveRoverNumberOfTimes(RoverIdentifier id, int times);

	void turnLeft(RoverIdentifier id);

	void turnRight(RoverIdentifier id);
	
	void updateRover(Rover rover);

	void updateRoverWithPosition(RoverIdentifier id, TwoDimensionalCoordinates position);

	void updateRoverWithOrientation(RoverIdentifier id, Orientation orientation);

	void removeRover(RoverIdentifier id);

	Rover getRover(RoverIdentifier id);

	List<Rover> getAllRoversOnPlateau(UUID uuid);

}
