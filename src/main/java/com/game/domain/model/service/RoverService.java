package com.game.domain.model.service;

import java.util.List;
import java.util.UUID;

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

	List<Rover> getAllRoversOnPlateau(UUID uuid);

	void updateRoverWithPosition(RoverIdentifier id, TwoDimensionalCoordinates position);

	void updateRoverWithOrientation(RoverIdentifier id, Orientation orientation);

	void removeRover(RoverIdentifier id);


}
