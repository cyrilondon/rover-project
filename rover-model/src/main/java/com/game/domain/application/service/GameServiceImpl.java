package com.game.domain.application.service;

import java.util.Collections;
import java.util.List;

import com.game.domain.application.command.ApplicationCommand;
import com.game.domain.application.command.ReturnApplicationCommand;
import com.game.domain.application.command.VoidApplicationCommand;
import com.game.domain.application.command.plateau.PlateauGetCommand;
import com.game.domain.application.command.plateau.PlateauInitializeCommand;
import com.game.domain.application.command.rover.RoverInitializeCommand;
import com.game.domain.application.command.rover.RoverMoveCommand;
import com.game.domain.application.command.rover.RoverTurnCommand;
import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.plateau.Plateau;
import com.game.domain.model.entity.rover.Orientation;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.event.DomainEventPublisherSubscriber;
import com.game.domain.model.event.subscriber.plateau.PlateauInitializedEventSubscriber;
import com.game.domain.model.event.subscriber.plateau.PlateauInitializedWithExceptionEventSubscriber;
import com.game.domain.model.event.subscriber.plateau.PlateauSwitchedLocationEventSubscriber;
import com.game.domain.model.event.subscriber.rover.RoverInitializedEventSubscriber;
import com.game.domain.model.event.subscriber.rover.RoverInitializedWithExceptionEventSubscriber;
import com.game.domain.model.event.subscriber.rover.RoverMovedEventSubscriber;
import com.game.domain.model.event.subscriber.rover.RoverMovedWithExceptionEventSubscriber;
import com.game.domain.model.event.subscriber.rover.RoverTurnedEventSubscriber;
import com.game.domain.model.service.plateau.PlateauServiceImpl;
import com.game.domain.model.service.rover.RoverServiceImpl;

/**
 * Application service which acts as a facade to the application
 * <ol>
 * <li>Exposes only one public method to the rest of the world
 * {@link #execute(List)}</li>
 * <li>Delegates the execution of the process to the two Domain services
 * {@link RoverServiceImpl}, {@link PlateauServiceImpl} and the Application
 * State {@link GameContext}</li>
 * <li>Converts Command objects from outside world to Domain Services calls (All
 * the write commands should have return type = void)</li>
 * <li>Register Domain Events subscribers</li>
 * </ol>
 */
public class GameServiceImpl implements GameService {
	
	@Override
	public void execute(List<VoidApplicationCommand> commands) {
		GameServiceCommandVisitor commandVisitor = new GameServiceCommandVisitor(this);
        commands.forEach(command -> command.acceptVisitor(commandVisitor));		
	}

	@Override
	public void execute(VoidApplicationCommand command) {
		this.execute(Collections.singletonList(command));
	}

	@Override
	public <T> T execute(ReturnApplicationCommand<T> command) {
		GameServiceCommandVisitor commandVisitor = new GameServiceCommandVisitor(this);
		return command.acceptVisitor(commandVisitor);
	}

	void execute(PlateauInitializeCommand command) {

		// register the subscriber for the given type of event = PlateauInitializedEvent
		DomainEventPublisherSubscriber.instance().subscribe(new PlateauInitializedEventSubscriber());
		
		DomainEventPublisherSubscriber.instance().subscribe(new PlateauInitializedWithExceptionEventSubscriber());
		
		GameContext.getInstance().getPlateauService().initializePlateau(command.getPlateauUuid(),
				new TwoDimensionalCoordinates(command.getWidth(), command.getHeight()),
				command.getObserverSpeed());
	}

	void execute(RoverInitializeCommand command) {

		// register the subscriber for the given type of event = RoverInitializedEvent
		DomainEventPublisherSubscriber.instance().subscribe(new RoverInitializedEventSubscriber());

		// register the subscriber in case of something went wrong during Rover moves
		DomainEventPublisherSubscriber.instance().subscribe(new RoverInitializedWithExceptionEventSubscriber());

		// register the subscriber for the plateau to mark it as occupied for the rover
		// position
		DomainEventPublisherSubscriber.instance().subscribe(new PlateauSwitchedLocationEventSubscriber());

		GameContext.getInstance().getRoverService().initializeRover(
				new RoverIdentifier(command.getPlateauUuid(), command.getName()),
				new TwoDimensionalCoordinates(command.getAbscissa(), command.getOrdinate()),
				Orientation.get(String.valueOf(command.getOrientation())));
	}

	void execute(RoverMoveCommand command) {

		// register the subscriber for the given type of event = RoverMovedEvent
		DomainEventPublisherSubscriber.instance().subscribe(new RoverMovedEventSubscriber());

		// register the subscriber in case of something went wrong during Rover moves
		DomainEventPublisherSubscriber.instance().subscribe(new RoverMovedWithExceptionEventSubscriber());

		// register the subscriber for the plateau
		DomainEventPublisherSubscriber.instance().subscribe(new PlateauSwitchedLocationEventSubscriber());

		// delegates to the rover service
		GameContext.getInstance().getRoverService().moveRoverNumberOfTimes(command.getRoverId(),
				command.getNumberOfMoves());

	}

	void execute(RoverTurnCommand command) {

		// register the subscriber for the given type of event = RoverTurnedEvent
		DomainEventPublisherSubscriber.instance().subscribe(new RoverTurnedEventSubscriber());

		// delegates to rover service
		GameContext.getInstance().getRoverService().turnRover(command.getRoverId(), command.getTurn());

	}
	
	Plateau execute(PlateauGetCommand command) {

		// delegates to plateau service
		return GameContext.getInstance().getPlateauService().loadPlateau(command.getId());

	}	

}
