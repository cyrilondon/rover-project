package com.game.domain.model.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.entity.RoverIdentifier;
import com.game.domain.model.entity.RoverTurnInstruction;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.repository.RoverRepository;

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
		roverRepository.add(rover.validate());
	}

	@Override
	public void turnRover(RoverIdentifier id, RoverTurnInstruction turn) {
		switch (turn) {
		case LEFT:
			roverRepository.load(id).turnLeft();
			break;

		case RIGHT:
			roverRepository.load(id).turnRight();
			break;

		default:
			// do nothing
		}
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

	@Override
	public void updateRoverWithPosition(RoverIdentifier id, TwoDimensionalCoordinates position) {
		Rover rover = this.getRover(id);
		rover.setPosition(position);
		this.updateRover(rover);
	}

	@Override
	public void updateRoverWithOrientation(RoverIdentifier id, Orientation orientation) {
		Rover rover = this.getRover(id);
		rover.setOrientation(orientation);
		this.updateRover(rover);
	}

	@Override
	public void removeRover(RoverIdentifier id) {
		roverRepository.remove(id);
	}

	@Override
	public List<Rover> getAllRoversOnPlateau(UUID uuid) {
		return roverRepository.getAllRovers().stream().filter(rover -> rover.getId().getPlateauUuid().equals(uuid))
				.collect(Collectors.toList());
	}

}
