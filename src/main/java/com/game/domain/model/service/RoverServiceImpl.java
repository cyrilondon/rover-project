package com.game.domain.model.service;

import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.entity.RoverIdentifier;
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
	public void initializeRover(RoverIdentifier id, TwoDimensionalCoordinates coordinates, Orientation orientation) {
		Rover rover = new Rover(id, coordinates, orientation);
		roverRepository.add(rover.validate(new EntityDefaultValidationNotificationHandler()));
	}

	@Override
	public void faceToOrientation(RoverIdentifier id, Orientation orientation) {
		Rover rover = roverRepository.load(id);
		rover.setOrientation(orientation);
	}

	@Override
	public void moveRoverNumberOfTimes(RoverIdentifier id, int times) {
		roverRepository.load(id).moveNumberOfTimes(times);
	}

	@Override
	public void updateRover(Rover rover) {
		roverRepository.update(rover);
	}

	@Override
	public Rover getRover(RoverIdentifier id) {
		return roverRepository.load(id);
	}

}
