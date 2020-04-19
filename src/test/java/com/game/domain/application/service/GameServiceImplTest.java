package com.game.domain.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.game.domain.application.command.plateau.PlateauInitializeCommand;
import com.game.domain.application.command.rover.RoverInitializeCommand;
import com.game.domain.application.command.rover.RoverMoveCommand;
import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.dimensions.RelativisticTwoDimensions;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.entity.plateau.Plateau;
import com.game.domain.model.entity.rover.Orientation;
import com.game.domain.model.entity.rover.Rover;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.entity.rover.RoverTurnInstruction;
import com.game.domain.model.event.DomainEventPublisherSubscriber;
import com.game.domain.model.event.store.EventStoreImpl;
import com.game.domain.model.event.subscriber.plateau.PlateauSwitchedLocationEventSubscriber;
import com.game.domain.model.event.subscriber.rover.RoverInitializedEventSubscriber;
import com.game.domain.model.event.subscriber.rover.RoverMovedEventSubscriber;
import com.game.domain.model.event.subscriber.rover.RoverMovedWithExceptionEventSubscriber;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.PlateauLocationAlreadySetException;
import com.game.domain.model.exception.PlateauNotFoundException;
import com.game.domain.model.repository.RoverRepository;
import com.game.domain.model.service.locator.ServiceLocator;
import com.game.domain.model.service.plateau.PlateauService;
import com.game.domain.model.service.rover.RoverService;

public class GameServiceImplTest {

	private static final int WIDTH = 5;

	private static final int HEIGHT = 5;

	private static final int X = 3;

	private static final int Y = 4;

	private GameContext gameContext = GameContext.getInstance();

	private GameServiceImpl gameService = new GameServiceImpl();

	public List<Rover> roversList = new ArrayList<Rover>();

	public Plateau plateau;

	UUID relativisticUUID = UUID.fromString("53567a5d-a21c-495e-80a3-d12adaf8585c");

	
	private void mockServiceLocator() {
		ServiceLocator mockServiceLocator = new ServiceLocator();
		mockServiceLocator.loadDomainService(ServiceLocator.ROVER_SERVICE, new MockRoverServiceImpl());
		mockServiceLocator.loadDomainService(ServiceLocator.PLATEAU_SERVICE, new MockPlateauServiceImpl());
		mockServiceLocator.loadEventStore(ServiceLocator.EVENT_STORE, new EventStoreImpl());
		ServiceLocator.load(mockServiceLocator);
	}

	@BeforeMethod
	public void resetGame() {
		mockServiceLocator();
		roversList.clear();
		plateau = null;
		DomainEventPublisherSubscriber.instance().clear();
	}

	@Test
	public void testInitializePlateau() {
		System.out.println(gameContext.getPlateauService());
		UUID uuid  = UUID.randomUUID();
		gameService.execute(new PlateauInitializeCommand.Builder().withId(uuid).withAbscissa(WIDTH)
				.withOrdinate(HEIGHT).build());
		assertThat(plateau.getWidth()).isEqualTo(WIDTH);
		assertThat(plateau.getHeight()).isEqualTo(HEIGHT);
		assertThat(gameContext.getPlateau(uuid)).isEqualTo(plateau);
	}

	@Test
	public void testInitializeRelativisticPlateau() {
		UUID uuid  = UUID.randomUUID();
		gameService.execute(new PlateauInitializeCommand.Builder().withId(uuid).withAbscissa(WIDTH)
				.withOrdinate(HEIGHT).withObserverSpeed(2 * GameContext.MINIMAL_RELATIVISTIC_SPEED).build());
		assertThat(plateau.getWidth()).isEqualTo(WIDTH - 2);
		assertThat(plateau.getHeight()).isEqualTo(HEIGHT - 2);
		assertThat(gameContext.getPlateau(relativisticUUID)).isNotNull();
	}

	@Test
	public void testInitializeRover() {
		UUID uuid = UUID.randomUUID();
		gameService.execute(
				new PlateauInitializeCommand.Builder().withId(uuid).withAbscissa(WIDTH).withOrdinate(HEIGHT).build());
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);
		RoverInitializeCommand initializeCommand = new RoverInitializeCommand.Builder().withPlateauUuid(uuid)
				.withName(GameContext.ROVER_NAME_PREFIX + 1).withAbscissa(coordinates.getAbscissa())
				.withOrdinate(coordinates.getOrdinate()).withOrientation('S').build();
		gameService.execute(initializeCommand);
		assertThat(roversList.contains(new Rover(new RoverIdentifier(uuid, GameContext.ROVER_NAME_PREFIX + 1),
				coordinates, Orientation.SOUTH))).isTrue();
		assertThat(DomainEventPublisherSubscriber.getSubscribers().get().size()).isEqualTo(2);
		assertThat(DomainEventPublisherSubscriber.getSubscribers().get().get(0)).isInstanceOf(RoverInitializedEventSubscriber.class);
		assertThat(DomainEventPublisherSubscriber.getSubscribers().get().get(1)).isInstanceOf(PlateauSwitchedLocationEventSubscriber.class);
	}

	@Test
	public void testMoveRoverWithOrientation() {
		UUID uuid = UUID.randomUUID();
		String roverName = GameContext.ROVER_NAME_PREFIX + 3;
		gameService.execute(new RoverMoveCommand(new RoverIdentifier(uuid, roverName), 1));
		assertThat(roversList).contains(
				new Rover(new RoverIdentifier(uuid, roverName), new TwoDimensionalCoordinates(2, 3), Orientation.WEST));
		assertThat(DomainEventPublisherSubscriber.getSubscribers().get().size()).isEqualTo(3);
		assertThat(DomainEventPublisherSubscriber.getSubscribers().get().get(0)).isInstanceOf(RoverMovedEventSubscriber.class);
		assertThat(DomainEventPublisherSubscriber.getSubscribers().get().get(1)).isInstanceOf(RoverMovedWithExceptionEventSubscriber.class);
		assertThat(DomainEventPublisherSubscriber.getSubscribers().get().get(2)).isInstanceOf(PlateauSwitchedLocationEventSubscriber.class);
	}

	/**
	 * Here we test that no exception is caught in GameServiceImpl method
	 */
	@Test
	public void testMoveRoverWithOrientationOutOfTheBoard() {
		String roverName = GameContext.ROVER_NAME_PREFIX + 5;
		Throwable thrown = catchThrowable(
				() -> gameService.execute(new RoverMoveCommand(new RoverIdentifier(UUID.randomUUID(), roverName), 1)));
		assertThat(thrown).isInstanceOf(PlateauLocationAlreadySetException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.PLATEAU_LOCATION_ERROR_CODE, "Error"));

	}

	/**
	 * Simple MockClass for the RoverServiceImpl
	 *
	 */
	private class MockRoverServiceImpl implements RoverService {

		@Override
		public void initializeRover(RoverIdentifier id, TwoDimensionalCoordinates coordinates,
				Orientation orientation) {
			GameServiceImplTest.this.roversList
					.add(new Rover(new RoverIdentifier(id.getPlateauId(), id.getName()), coordinates, orientation));
		}

		@Override
		public void moveRoverNumberOfTimes(RoverIdentifier id, int numberOfTimes) {
			GameServiceImplTest.this.roversList.add(new Rover(new RoverIdentifier(id.getPlateauId(), id.getName()),
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
		public void updateRoverWithPosition(RoverIdentifier id, TwoDimensionalCoordinates position) {
		}

		@Override
		public void updateRoverWithOrientation(RoverIdentifier id, Orientation orientation) {
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

	/**
	 * Simple MockClass for the PlateauServiceImpl
	 *
	 */
	private class MockPlateauServiceImpl implements PlateauService {

		Map<TwoDimensionalCoordinates, Boolean> mapLocations = new HashMap<>();

		@Override
		public Plateau initializePlateau(UUID uuid, TwoDimensionalCoordinates coordinates) {
			GameServiceImplTest.this.plateau = new Plateau(uuid,
					new TwoDimensions(
							new TwoDimensionalCoordinates(coordinates.getAbscissa(), coordinates.getOrdinate())))
									.initializeLocations();
			return GameServiceImplTest.this.plateau;
		}

		/**
		 * What ever UUID we pass as argument, if we go through this method we return
		 * the relativistic UUID back
		 */
		@Override
		public Plateau initializeRelativisticPlateau(UUID uuid, int speed, TwoDimensionalCoordinates coordinates) {
			GameServiceImplTest.this.plateau = new Plateau(relativisticUUID,
					new RelativisticTwoDimensions(speed, new TwoDimensions(
							new TwoDimensionalCoordinates(coordinates.getAbscissa(), coordinates.getOrdinate()))))
									.initializeLocations();
			return GameServiceImplTest.this.plateau;
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
			if (GameServiceImplTest.this.plateau == null)
				throw new PlateauNotFoundException(plateauUuid);
			return GameServiceImplTest.this.plateau;
		}

		@Override
		public void updatePlateau(Plateau plateau) {
		}

		@Override
		public void updatePlateauWithLocations(UUID plateauUUID, TwoDimensionalCoordinates freeLocation,
				TwoDimensionalCoordinates busyLocation) {
		}

	}

}
