package com.game.domain.model.service.rover;

import java.util.List;
import java.util.UUID;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.rover.Orientation;
import com.game.domain.model.entity.rover.Rover;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.entity.rover.RoverTurnInstruction;
import com.game.domain.model.service.DomainService;

public interface RoverService extends DomainService {
		
	
	void initializeRover(RoverIdentifier id, TwoDimensionalCoordinates coordinates, Orientation orientation);

	void moveRoverNumberOfTimes(RoverIdentifier id, int times);
	
	void turnRover(RoverIdentifier roverId, RoverTurnInstruction turn);
	
	void updateRover(Rover rover);

	void updateRoverWithPosition(RoverIdentifier id, TwoDimensionalCoordinates position);

	void updateRoverWithOrientation(RoverIdentifier id, Orientation orientation);

	void removeRover(RoverIdentifier id);

	Rover getRover(RoverIdentifier id);

	List<Rover> getAllRoversOnPlateau(UUID uuid);

	
}
