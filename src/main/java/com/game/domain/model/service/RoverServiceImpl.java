package com.game.domain.model.service;

import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.repository.RoverRepository;

/**
 * Pure domain service 
 * No need of a specific interface other than the marker {@link DomainService} one
 *
 */
public class RoverServiceImpl implements DomainService {

	private RoverRepository roverRepository;

	public RoverServiceImpl(RoverRepository roverRepository) {
		this.roverRepository = roverRepository;
	}

	public void initializeRover(String roverName, TwoDimensionalCoordinates coordinates, Orientation orientation) {
		roverRepository.addRover(new Rover(coordinates, orientation));
	}

	public void faceToOrientation(String roverName, Orientation orientation) {
		Rover rover = roverRepository.getRover(roverName);
		rover.setOrientation(orientation);
	}

	public void moveRoverwithOrientation(String roverName, Orientation orientation) {
		Rover rover = roverRepository.getRover(roverName);
		rover.setOrientation(orientation);
		rover.move();
	}

}
