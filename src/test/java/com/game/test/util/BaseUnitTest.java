package com.game.test.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.entity.plateau.Plateau;
import com.game.domain.model.entity.rover.Orientation;
import com.game.domain.model.entity.rover.Rover;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.event.DomainEventPublisherSubscriber;
import com.game.domain.model.event.DomainEventSubscriber;
import com.game.domain.model.event.plateau.PlateauSwitchedLocationEvent;
import com.game.domain.model.event.rover.RoverInitializedEvent;
import com.game.domain.model.event.rover.RoverInitializedWithExceptionEvent;
import com.game.domain.model.event.rover.RoverMovedEvent;
import com.game.domain.model.event.rover.RoverMovedWithExceptionEvent;
import com.game.domain.model.exception.IllegalRoverMoveException;
import com.game.domain.model.exception.RoverInitializationException;
import com.game.domain.model.repository.RoverRepository;

import com.game.infrastructure.persistence.impl.InMemoryRoverRepositoryImpl;

/**
 * Utility class which provides mock implementations for various {@link DomainEventSubscriber}
 *
 */
public class BaseUnitTest {
	
	protected static final String ROVER_NAME = "ROVER_TEST";
	
	protected UUID plateauUUID = UUID.randomUUID();
	
	protected Rover initializeDefaultRover() {
		return new Rover(new RoverIdentifier(UUID.randomUUID(), ROVER_NAME), new TwoDimensionalCoordinates(3, 4),
				Orientation.SOUTH);
	}

	protected Rover initializeDefaultRover(UUID uuid) {
		return new Rover(new RoverIdentifier(uuid, ROVER_NAME), new TwoDimensionalCoordinates(3, 4), Orientation.SOUTH);
	}


	protected void addPlateau(UUID uuid, int width, int height) {
		GameContext.getInstance().addPlateau(new Plateau(uuid, new TwoDimensions(new TwoDimensionalCoordinates(width, height)))
				.initializeLocations());
	}
	
	protected Rover initializeRover(Orientation orientation) {
		return new Rover(new RoverIdentifier(plateauUUID, ROVER_NAME), new TwoDimensionalCoordinates(3, 4), orientation);
	}
	
	/**
	 * Simple MockClass for the RoverRepository We explicitly choose a different
	 * implementation than the {@link InMemoryRoverRepositoryImpl}
	 *
	 */
	public class MockRoverRepository implements RoverRepository {

		List<Rover> rovers = new ArrayList<Rover>();

		@Override
		public Rover load(RoverIdentifier id) {
			return rovers.get(Integer.valueOf(id.getName().substring(ROVER_NAME.length())) - 1);
		}

		@Override
		public void add(Rover rover) {
			rovers.add(rover);
		}

		@Override
		public void remove(RoverIdentifier id) {
		}

		@Override
		public int getNumberOfRovers() {
			return rovers.size();
		}

		@Override
		public void removeAllRovers() {
			rovers.clear();
		}

		@Override
		public void update(Rover entity) {
		}

		@Override
		public Collection<Rover> getAllRovers() {
			return rovers;
		}

	}

	protected List<RoverInitializedEvent> roverInitializedEvents = new ArrayList<RoverInitializedEvent>();

	protected List<RoverInitializedWithExceptionEvent> roverInitializedWithExceptionEvents = new ArrayList<RoverInitializedWithExceptionEvent>();

	protected List<RoverMovedEvent> roverMovedEvents = new ArrayList<RoverMovedEvent>();

	protected List<RoverMovedWithExceptionEvent> roverMovedWithExceptionEvents = new ArrayList<RoverMovedWithExceptionEvent>();

	protected List<PlateauSwitchedLocationEvent> plateauSwitchedLocationEvents = new ArrayList<PlateauSwitchedLocationEvent>();

	protected void clearAndSubscribe() {
		DomainEventPublisherSubscriber.instance().clear();
		DomainEventPublisherSubscriber.instance().subscribe(new MockRoverInitizialiedEventSubscriber());
		DomainEventPublisherSubscriber.instance().subscribe(new MockRoverInitizialiedWithExceptionEventSubscriber());
		DomainEventPublisherSubscriber.instance().subscribe(new MockRoverMovedEventSubscriber());
		DomainEventPublisherSubscriber.instance().subscribe(new MockRoverMovedEventWithExceptionSubscriber());
		DomainEventPublisherSubscriber.instance().subscribe(new MockPlateauSwitchedLocationEventSubscriber());
	}

	protected void clearAllExpectedEvents() {
		roverInitializedEvents.clear();
		roverInitializedWithExceptionEvents.clear();
		roverMovedEvents.clear();
		roverMovedWithExceptionEvents.clear();
		plateauSwitchedLocationEvents.clear();
	}
	
	protected void clearEventStore() {
		GameContext.getInstance().getEventStore().getAllEvents().clear();
	}

	public class MockRoverInitizialiedEventSubscriber implements DomainEventSubscriber<RoverInitializedEvent> {

		@Override
		public void handleEvent(RoverInitializedEvent event) {
			roverInitializedEvents.add(event);
		}

		@Override
		public Class<RoverInitializedEvent> subscribedToEventType() {
			return RoverInitializedEvent.class;
		}
	}

	public class MockRoverInitizialiedWithExceptionEventSubscriber
			implements DomainEventSubscriber<RoverInitializedWithExceptionEvent> {

		@Override
		public void handleEvent(RoverInitializedWithExceptionEvent event) {
			roverInitializedWithExceptionEvents.add(event);
			throw new RoverInitializationException("Error message");
		}

		@Override
		public Class<RoverInitializedWithExceptionEvent> subscribedToEventType() {
			return RoverInitializedWithExceptionEvent.class;
		}

	}

	public class MockRoverMovedEventSubscriber implements DomainEventSubscriber<RoverMovedEvent> {

		@Override
		public void handleEvent(RoverMovedEvent event) {
			roverMovedEvents.add(event);
		}

		@Override
		public Class<RoverMovedEvent> subscribedToEventType() {
			return RoverMovedEvent.class;
		}
	}

	public class MockRoverMovedEventWithExceptionSubscriber
			implements DomainEventSubscriber<RoverMovedWithExceptionEvent> {

		@Override
		public void handleEvent(RoverMovedWithExceptionEvent event) {
			roverMovedWithExceptionEvents.add(event);
			throw new IllegalRoverMoveException(event.getException().getMessage());
		}

		@Override
		public Class<RoverMovedWithExceptionEvent> subscribedToEventType() {
			return RoverMovedWithExceptionEvent.class;
		}
	}

	public class MockPlateauSwitchedLocationEventSubscriber
			implements DomainEventSubscriber<PlateauSwitchedLocationEvent> {

		@Override
		public void handleEvent(PlateauSwitchedLocationEvent event) {
			plateauSwitchedLocationEvents.add(event);

		}

		@Override
		public Class<PlateauSwitchedLocationEvent> subscribedToEventType() {
			return PlateauSwitchedLocationEvent.class;
		}

	}

}
