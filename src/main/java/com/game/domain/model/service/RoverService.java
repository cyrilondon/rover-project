package com.game.domain.model.service;

import java.util.UUID;

import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

public interface RoverService extends DomainService {
	
	public void initializeRover(UUID plateauUuid, String roverName, TwoDimensionalCoordinates coordinates, Orientation orientation);

	public void faceToOrientation(String roverName, Orientation orientation);

	void moveRoverNumberOfTimes(String roverName, int times);
	
	Rover getRover(String roverName);
	
	void updateRover(Rover rover);


}
