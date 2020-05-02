package com.game.test.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.entity.plateau.Plateau;
import com.game.domain.model.entity.rover.Orientation;
import com.game.domain.model.entity.rover.Rover;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.entity.rover.RoverIdentifierDto;
import com.game.domain.model.entity.rover.RoverTurnInstruction;
import com.game.domain.model.event.AbstractDomainEventSubscriber;
import com.game.domain.model.event.DomainEventPublisherSubscriber;
import com.game.domain.model.event.DomainEventSubscriber;
import com.game.domain.model.event.plateau.PlateauSwitchedLocationEvent;
import com.game.domain.model.event.rover.RoverInitializedEvent;
import com.game.domain.model.event.rover.RoverInitializedWithExceptionEvent;
import com.game.domain.model.event.rover.RoverMovedEvent;
import com.game.domain.model.event.rover.RoverMovedWithExceptionEvent;
import com.game.domain.model.event.subscriber.plateau.PlateauInitializedEventSubscriber;
import com.game.domain.model.event.subscriber.plateau.PlateauInitializedWithExceptionEventSubscriber;
import com.game.domain.model.exception.IllegalRoverMoveException;
import com.game.domain.model.exception.PlateauLocationAlreadySetException;
import com.game.domain.model.exception.PlateauNotFoundException;
import com.game.domain.model.exception.RoverInitializationException;
import com.game.domain.model.repository.RoverRepository;
import com.game.domain.model.service.plateau.PlateauService;
import com.game.domain.model.service.rover.RoverService;
import com.game.infrastructure.persistence.impl.InMemoryRoverRepositoryImpl;

/**
 * Utility class which provides mock implementations for various {@link DomainEventSubscriber}
 *
 */
public class BaseUnitTest {
	
	protected static final String ROVER_NAME = "ROVER_TEST";
	
	UUID relativisticUUID = UUID.fromString("53567a5d-a21c-495e-80a3-d12adaf8585c");
	
	protected UUID plateauUUID = UUID.randomUUID();
	
	public Plateau plateau;
	
	public List<Rover> roversList = new ArrayList<Rover>();
	
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
			return rovers.get(0);
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
		DomainEventPublisherSubscriber.instance().subscribe(new PlateauInitializedEventSubscriber());
		DomainEventPublisherSubscriber.instance().subscribe(new PlateauInitializedWithExceptionEventSubscriber());
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

	public class MockRoverInitizialiedEventSubscriber extends AbstractDomainEventSubscriber<RoverInitializedEvent> {

		@Override
		public void handleEvent(RoverInitializedEvent event) {
			roverInitializedEvents.add(event);
		}

		@Override
		public Class<RoverInitializedEvent> subscribedToEventType() {
			return RoverInitializedEvent.class;
		}

		@Override
		public String getId() {
			return RoverInitializedEvent.class.getSimpleName();
		}
	}

	public class MockRoverInitizialiedWithExceptionEventSubscriber
	extends AbstractDomainEventSubscriber<RoverInitializedWithExceptionEvent> {

		@Override
		public void handleEvent(RoverInitializedWithExceptionEvent event) {
			roverInitializedWithExceptionEvents.add(event);
			throw new RoverInitializationException("Error message");
		}

		@Override
		public Class<RoverInitializedWithExceptionEvent> subscribedToEventType() {
			return RoverInitializedWithExceptionEvent.class;
		}

		@Override
		public String getId() {
			return RoverInitializedWithExceptionEvent.class.getSimpleName();
		}

	}

	public class MockRoverMovedEventSubscriber extends AbstractDomainEventSubscriber<RoverMovedEvent> {

		@Override
		public void handleEvent(RoverMovedEvent event) {
			roverMovedEvents.add(event);
		}

		@Override
		public Class<RoverMovedEvent> subscribedToEventType() {
			return RoverMovedEvent.class;
		}

		@Override
		public String getId() {
			return RoverMovedEvent.class.getSimpleName();
		}
	}

	public class MockRoverMovedEventWithExceptionSubscriber
	extends AbstractDomainEventSubscriber<RoverMovedWithExceptionEvent> {

		@Override
		public void handleEvent(RoverMovedWithExceptionEvent event) {
			roverMovedWithExceptionEvents.add(event);
			throw new IllegalRoverMoveException(event.getException().getMessage());
		}

		@Override
		public Class<RoverMovedWithExceptionEvent> subscribedToEventType() {
			return RoverMovedWithExceptionEvent.class;
		}

		@Override
		public String getId() {
			return RoverMovedWithExceptionEvent.class.getSimpleName();
		}
	}

	public class MockPlateauSwitchedLocationEventSubscriber
	extends AbstractDomainEventSubscriber<PlateauSwitchedLocationEvent> {

		@Override
		public void handleEvent(PlateauSwitchedLocationEvent event) {
			plateauSwitchedLocationEvents.add(event);

		}

		@Override
		public Class<PlateauSwitchedLocationEvent> subscribedToEventType() {
			return PlateauSwitchedLocationEvent.class;
		}

		@Override
		public String getId() {
			return PlateauSwitchedLocationEvent.class.getSimpleName();
		}

	}
	
	/**
	 * Simple MockClass for the PlateauServiceImpl
	 *
	 */
	public class MockPlateauServiceImpl implements PlateauService {

		Map<TwoDimensionalCoordinates, Boolean> mapLocations = new HashMap<>();

		@Override
		public Plateau initializePlateau(UUID uuid, TwoDimensionalCoordinates coordinates, int observerSpeed) {
			BaseUnitTest.this.plateau = new Plateau(uuid,
					new TwoDimensions(
							new TwoDimensionalCoordinates(coordinates.getAbscissa(), coordinates.getOrdinate())))
									.initializeLocations();
			return BaseUnitTest.this.plateau;
		}

		@Override
		public void updatePlateauWithOccupiedLocation(UUID uuid, TwoDimensionalCoordinates coordinates) {
			mapLocations.put(coordinates, Boolean.TRUE);
		}

		@Override
		public boolean isLocationBusy(UUID uuid, TwoDimensionalCoordinates coordinates) {
			return mapLocations.get(coordinates);
		}

		@Override
		public void updatePlateauWithFreeLocation(UUID uuid, TwoDimensionalCoordinates coordinates) {
			mapLocations.put(coordinates, Boolean.FALSE);
		}

		@Override
		public Plateau loadPlateau(UUID plateauUuid) {
			if (BaseUnitTest.this.plateau == null)
				throw new PlateauNotFoundException(plateauUuid);
			return BaseUnitTest.this.plateau;
		}

		@Override
		public void updatePlateau(Plateau plateau) {
		}

		@Override
		public void updatePlateauWithLocations(UUID plateauUUID, TwoDimensionalCoordinates freeLocation,
				TwoDimensionalCoordinates busyLocation) {
		}

		@Override
		public void addPlateau(Plateau plateau) {
		}

	}
	
	
	/**
//	 * Simple MockClass for the RoverServiceImpl
//	 *
//	 */
	public class MockRoverServiceImpl implements RoverService {

		@Override
		public void initializeRover(RoverIdentifier id, TwoDimensionalCoordinates coordinates,
				Orientation orientation) {
			BaseUnitTest.this.roversList
					.add(new Rover(new RoverIdentifier(id.getPlateauId(), id.getName()), coordinates, orientation));
		}

		@Override
		public void moveRoverNumberOfTimes(RoverIdentifier id, int numberOfTimes) {
			BaseUnitTest.this.roversList.add(new Rover(new RoverIdentifier(id.getPlateauId(), id.getName()),
					new TwoDimensionalCoordinates(2, 3), Orientation.WEST));
			if (id.getName().equals(GameContext.ROVER_NAME_PREFIX + 5))
				throw new PlateauLocationAlreadySetException("Error");
		}

		@Override
		public void updateRover(Rover rover) {
		}

		@Override
		public Rover getRover(RoverIdentifier id) {
			return null;
		}

		@Override
		public List<Rover> getAllRoversOnPlateau(UUID uuid) {
			return null;
		}

		@Override
		public void updateRoverWithPosition(RoverIdentifierDto id, TwoDimensionalCoordinates position) {
		}

		@Override
		public void updateRoverWithOrientation(RoverIdentifierDto id, Orientation orientation) {
		}

		@Override
		public void removeRover(RoverIdentifier id) {
		}

		@Override
		public void turnRover(RoverIdentifier roverId, RoverTurnInstruction turn) {
		}

		@Override
		public RoverRepository getRoverRepository() {
			return null;
		}

	}
	
}
