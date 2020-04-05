package com.game.domain.model.service;

import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

public interface RoverService extends DomainService {
	
	public void initializeRover(String roverName, TwoDimensionalCoordinates coordinates, Orientation orientation);

	public void faceToOrientation(String roverName, Orientation orientation);

	public void moveRoverWithOrientation(String roverName, Orientation orientation);


}
