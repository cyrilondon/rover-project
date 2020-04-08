package com.game.domain.model.service;

import java.util.UUID;

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
	public void initializeRover(UUID plateauUuid, String roverName, TwoDimensionalCoordinates coordinates, Orientation orientation) {
		Rover rover = new Rover(plateauUuid, roverName, coordinates, orientation);
		roverRepository.add(rover.validate(new EntityDefaultValidationNotificationHandler()));
	}

	@Override
	public void faceToOrientation(String roverName, Orientation orientation) {
		Rover rover = roverRepository.load(roverName);
		rover.setOrientation(orientation);
	}

	@Override
	public void moveRoverNumberOfTimes(UUID plateauUuid, String roverName, int times) {
		Rover rover = roverRepository.load(roverName);
		rover.moveNumberOfTimes(times);
	}

	@Override
	public void updateRover(Rover rover) {
		roverRepository.update(rover);
		
	}

	@Override
	public Rover getRover(String roverName) {
		return roverRepository.load(roverName);
	}

}
