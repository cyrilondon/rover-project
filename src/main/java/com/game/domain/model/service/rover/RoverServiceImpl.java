package com.game.domain.model.service.rover;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.plateau.Plateau;
import com.game.domain.model.entity.rover.Orientation;
import com.game.domain.model.entity.rover.Rover;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.entity.rover.RoverTurnInstruction;
import com.game.domain.model.event.BaseDomainEventPublisher;
import com.game.domain.model.event.plateau.PlateauSwitchedLocationEvent;
import com.game.domain.model.event.rover.RoverInitializedEvent;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.RoverInitializationException;
import com.game.domain.model.repository.RoverRepository;
import com.game.domain.model.service.plateau.PlateauService;

/**
 * Pure domain service which handles {@link Rover} entity
 *
 */
public class RoverServiceImpl extends BaseDomainEventPublisher implements RoverService {
	
	private PlateauService plateauService;

	private RoverRepository roverRepository;
	
	public final Function<RoverIdentifier, Void> addPlateauToContext = id -> {
		GameContext.getInstance().addPlateau(plateauService.loadPlateau(id.getPlateauId()));
		return null;
	};

	public RoverServiceImpl(PlateauService plateauService, RoverRepository roverRepository) {
		this.plateauService = plateauService;
		this.roverRepository = roverRepository;
	}

	@Override
	public void initializeRover(RoverIdentifier id, TwoDimensionalCoordinates coordinates, Orientation orientation) {
		
		// load plateau first to check that it is present in the system.
		Plateau plateau;
		try {
			plateau = plateauService.loadPlateau(id.getPlateauId());
		} catch (Exception e) {
			throw new RoverInitializationException(GameExceptionLabels.INITIALIZE_ROVER_NOT_ALLOWED, e);
		}
		GameContext.getInstance().addPlateau(plateau);
		
		RoverInitializedEvent event = new RoverInitializedEvent.Builder().withRoverId(id).withPosition(coordinates).withOrientation(orientation).build();
		Rover rover = new Rover(id, coordinates, orientation);
		rover.applyAndPublishEvent(event, rover.initializeRover, rover.initializeRoverWithException);
		
		PlateauSwitchedLocationEvent plateauEvent = new PlateauSwitchedLocationEvent.Builder().withPlateauId(id.getPlateauId()).
				withCurrentPosition(coordinates).build();
		
		publishEvent(plateauEvent);
	}


	@Override
	public void turnRover(RoverIdentifier id, RoverTurnInstruction turn) {
		addPlateauToContext.apply(id);
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
		addPlateauToContext.apply(id);
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
		return roverRepository.getAllRovers().stream().filter(rover -> rover.getId().getPlateauId().equals(uuid))
				.collect(Collectors.toList());
	}
	
	@Override
	public RoverRepository getRoverRepository() {
		return roverRepository;
	}

}
