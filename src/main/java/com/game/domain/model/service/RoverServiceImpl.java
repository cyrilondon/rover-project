package com.game.domain.model.service;

import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.repository.RoverRepository;
import com.game.domain.model.validation.EntityDefaultValidationNotificationHandler;

/**
 * Pure domain service which handles {@link Rover} entity
 *
 */
public class RoverServiceImpl implements RoverService {

	private RoverRepository roverRepository;

	public RoverServiceImpl(RoverRepository roverRepository) {
		this.roverRepository = roverRepository;
	}

	@Override
	public void initializeRover(String roverName, TwoDimensionalCoordinates coordinates, Orientation orientation) {
		Rover rover = new Rover(coordinates, orientation);
		roverRepository.addRover(rover.validate(new EntityDefaultValidationNotificationHandler()));
	}

	@Override
	public void faceToOrientation(String roverName, Orientation orientation) {
		Rover rover = roverRepository.getRover(roverName);
		rover.setOrientation(orientation);
	}

	@Override
	public void moveRoverWithOrientation(String roverName, Orientation orientation) {
		Rover rover = roverRepository.getRover(roverName);
		rover.setOrientation(orientation);
		rover.move();
	}

}
