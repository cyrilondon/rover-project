package com.game.domain.application;

import com.game.domain.application.command.MakeTurnRoverCommand;

import java.util.List;
import java.util.UUID;

import com.game.domain.application.command.InitializePlateauCommand;
import com.game.domain.application.command.InitializeRoverCommand;
import com.game.domain.application.command.MoveRoverCommand;
import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Plateau;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.entity.RoverIdentifier;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.event.DomainEventPublisher;
import com.game.domain.model.event.DomainEventSubscriber;
import com.game.domain.model.event.RoverTurnedEvent;
import com.game.domain.model.event.RoverMovedEvent;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.IllegalArgumentGameException;
import com.game.domain.model.service.PlateauServiceImpl;
import com.game.domain.model.service.RoverServiceImpl;

/**
 * Application service which acts as a facade to the application
 * <ol>
 * <li>Delegates the execution of the process to the two Domain services
 * {@link RoverServiceImpl}, {@link PlateauServiceImpl} and the Application
 * State {@link GameContext}</li>
 * <li>Converts Command objects from outside world to Domain Services calls (All
 * the write commands should have return type = void)</li>
 * <li>Register Domain Events subscribers</li>
 * </ol>
 */
public class GameServiceImpl implements GameService {

	public void execute(InitializePlateauCommand command) {
		GameContext gameContext = GameContext.getInstance();
		Plateau plateau = null;
		if (command.getObserverSpeed() < GameContext.MINIMAL_RELATIVISTIC_SPEED) {
			plateau = gameContext.getPlateauService().initializePlateau(command.getPlateauUuid(),
					new TwoDimensionalCoordinates(command.getAbscissa(), command.getOrdinate()));
		} else {
			plateau = gameContext.getPlateauService().initializeRelativisticPlateau(command.getPlateauUuid(),
					command.getObserverSpeed(),
					new TwoDimensionalCoordinates(command.getAbscissa(), command.getOrdinate()));
		}
		addPlateauToContext(plateau);
	}

	public void execute(InitializeRoverCommand command) {
		GameContext gameContext = GameContext.getInstance();
		if (gameContext.getPlateauService().loadPlateau(command.getPlateauUuid()) == null)
			throw new IllegalArgumentGameException(String.format(GameExceptionLabels.ERROR_MESSAGE_SEPARATION_PATTERN,
					GameExceptionLabels.MISSING_PLATEAU_CONFIGURATION, GameExceptionLabels.ADDING_ROVER_NOT_ALLOWED));
		gameContext.getRoverService().initializeRover(new RoverIdentifier(command.getPlateauUuid(), command.getName()),
				new TwoDimensionalCoordinates(command.getAbscissa(), command.getOrdinate()),
				Orientation.get(String.valueOf(command.getOrientation())));
		gameContext.getPlateauService().setLocationBusy(command.getPlateauUuid(),
				new TwoDimensionalCoordinates(command.getAbscissa(), command.getOrdinate()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(MakeTurnRoverCommand command) {
		
		GameContext gameContext = GameContext.getInstance();
		
		// defines the subscriber for the FaceToOrientationRoverCommand
		@SuppressWarnings("rawtypes")
		DomainEventSubscriber subscriber = new DomainEventSubscriber<RoverTurnedEvent>() {

			@Override
			public void handleEvent(RoverTurnedEvent event) {
				// 1. update persistent Rover with last orientation
				updateRoverWithLastOrientation(event);
				// 2. store the event
				// TODO with Kafka Producer?
			}

			@Override
			public Class<RoverTurnedEvent> subscribedToEventType() {
				return RoverTurnedEvent.class;
			}
			
			private void updateRoverWithLastOrientation(RoverTurnedEvent event) {
				Rover rover = gameContext.getRoverService().getRover(event.getRoverId());
				rover.setOrientation(event.getCurrentOrientation());
				gameContext.getRoverService().updateRover(rover);
			}
		
		};
		
		// register the subscriber for the given type of event = RoverMovedEvent
		DomainEventPublisher.instance().subscribe(subscriber);
				
		gameContext.getRoverService().faceToOrientation(
				new RoverIdentifier(command.getPlateauUuid(), command.getName()),
				Orientation.get(String.valueOf(command.getTurn())));
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(MoveRoverCommand command) {
		
		GameContext gameContext = GameContext.getInstance();

		// defines the subscriber for the RoverMovedEvent
		@SuppressWarnings("rawtypes")
		DomainEventSubscriber subscriber = new DomainEventSubscriber<RoverMovedEvent>() {

			@Override
			public void handleEvent(RoverMovedEvent event) {
				// 1. update persistent Rover with last position
				updateRoverWithLastPosition(event);
				// 2. mark old rover position as free
				gameContext.getPlateauService().setLocationFree(event.getRoverId().getPlateauUuid(),
						event.getPreviousPosition());
				// 3. mark new rover position as set/busy
				gameContext.getPlateauService().setLocationBusy(event.getRoverId().getPlateauUuid(),
						event.getCurrentPosition());
				// 4s. store the event
				// TODO with Kafka Producer?
			}

			@Override
			public Class<RoverMovedEvent> subscribedToEventType() {
				return RoverMovedEvent.class;
			}

			private void updateRoverWithLastPosition(RoverMovedEvent event) {
				Rover rover = gameContext.getRoverService().getRover(event.getRoverId());
				rover.setPosition(event.getCurrentPosition());
				gameContext.getRoverService().updateRover(rover);
			}
		};

		// register the subscriber for the given type of event = RoverMovedEvent
		DomainEventPublisher.instance().subscribe(subscriber);

		// delegates to the rover service
		gameContext.getRoverService().moveRoverNumberOfTimes(
				new RoverIdentifier(command.getPlateauUuid(), command.getRoverName()), command.getNumberOfMoves());
	}

	/**
	 * Once initialized, we want to keep track of the Plateau as in-memory singleton
	 * instance during the game lifetime i.e no need to go back to the Plateau
	 * repository each time it is needed (this in contrary to what happens for the
	 * rover objects which are fetched each time from the Rover Repository)
	 * 
	 * @param plateau
	 */
	private void addPlateauToContext(Plateau plateau) {
		GameContext.getInstance().addPlateau(plateau);
	}
	
	@Override
	public List<Rover> getAllRoversByPlateau(UUID uuid){
		return GameContext.getInstance().getRoverService().getAllRoversOnPlateau(uuid);
	}

}
