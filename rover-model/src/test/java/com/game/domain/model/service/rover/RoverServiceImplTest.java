package com.game.domain.model.service.rover;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.List;
import java.util.UUID;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.rover.Orientation;
import com.game.domain.model.entity.rover.Rover;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.event.DomainEvent;
import com.game.domain.model.event.rover.RoverInitializedWithExceptionEvent;
import com.game.domain.model.exception.EntityValidationException;
import com.game.domain.model.exception.RoverInitializationException;
import com.game.domain.model.repository.RoverRepository;
import com.game.test.util.BaseUnitTest;

public class RoverServiceImplTest extends BaseUnitTest

{

	private static final int X = 3;

	private static final int Y = 4;

	private static final int WIDTH = 10;

	private static final int HEIGHT = 10;

	RoverRepository mockRoverRepository = new MockRoverRepository();

	private GameContext gameContext = GameContext.getInstance();

	private final UUID mockUuuid = UUID.fromString("53567a5d-a21c-495e-80a3-d12adaf8585c");

	/**
	 * Inject our own Mock Rover repository implementation here
	 */
	private RoverServiceImpl roverService = new RoverServiceImpl(new MockPlateauServiceImpl(), mockRoverRepository);

	@BeforeTest
	public void setup() {
		gameContext.reset();
		
	}

	@BeforeMethod
	public void reset() {
		mockRoverRepository.removeAllRovers();
		clearAllExpectedEvents();
		clearAndSubscribe();
		clearEventStore();
	}


	@Test
	public void testInitializeRover() {
		UUID uuid = UUID.randomUUID();
		new MockPlateauServiceImpl().initializePlateau(uuid, new TwoDimensionalCoordinates(WIDTH, HEIGHT),0);
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);
		RoverIdentifier id = new RoverIdentifier(uuid, ROVER_NAME + (mockRoverRepository.getNumberOfRovers() + 1));
		roverService.initializeRover(id, coordinates, Orientation.SOUTH);
		assertThat(roverInitializedEvents.size()).isEqualTo(1);
		assertThat(roverMovedEvents.size()).isEqualTo(0);
		assertThat(plateauSwitchedLocationEvents.size()).isEqualTo(1);
	}
	
	@Test
	public void testInitializeRoverWithoutPlateau() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);
		RoverIdentifier id = new RoverIdentifier(UUID.randomUUID(), ROVER_NAME + (mockRoverRepository.getNumberOfRovers() + 1));
		Throwable thrown = catchThrowable(() -> roverService.initializeRover(id, coordinates, Orientation.SOUTH));
		assertThat(thrown).isInstanceOf(RoverInitializationException.class);
		assertThat(roverInitializedEvents.size()).isEqualTo(0);
		assertThat(roverMovedEvents.size()).isEqualTo(0);
		assertThat(plateauSwitchedLocationEvents.size()).isEqualTo(0);
	}


	@Test
	public void testInitializeRoverOutOfPlateau() {
		int width = 2, height = 2;
		UUID uuid = UUID.randomUUID();
		new MockPlateauServiceImpl().initializePlateau(uuid, new TwoDimensionalCoordinates(width, height),0);
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);
		RoverIdentifier id = new RoverIdentifier(uuid, ROVER_NAME);
		Throwable thrown = catchThrowable(() -> roverService.initializeRover(id, coordinates, Orientation.SOUTH));

		assertThat(thrown).isInstanceOf(RoverInitializationException.class);
		assertThat(roverInitializedWithExceptionEvents.size()).isEqualTo(1);
		assertThat(roverInitializedEvents.size()).isEqualTo(0);
		List<DomainEvent> eventsList = GameContext.getInstance().getEventStore().getAllEvents();
		assertThat(eventsList.size()).isEqualTo(1);
		DomainEvent eventStored = eventsList.get(0);
		assertThat(eventStored).isInstanceOf(RoverInitializedWithExceptionEvent.class);
		assertThat(((RoverInitializedWithExceptionEvent) eventStored).getEvent().getRoverId()).isEqualTo(id);
		assertThat(((RoverInitializedWithExceptionEvent) eventStored).getException())
				.isInstanceOf(EntityValidationException.class);
	}

	@Test
	public void testMoveRoverNumberOfTimes() {
		new MockPlateauServiceImpl().initializePlateau(mockUuuid, new TwoDimensionalCoordinates(WIDTH, HEIGHT), 0);
		mockRoverRepository.add(getRover());
		roverService.moveRoverNumberOfTimes(getRover().getId(), 3);
		Rover rover = mockRoverRepository.load(getRover().getId());
		assertThat(rover.getCoordinates()).isEqualTo(new TwoDimensionalCoordinates(X, Y - 3));
		assertThat(rover.getOrientation()).isEqualTo(Orientation.SOUTH);
	}

	@Test
	public void testAddRovers() {
		mockRoverRepository.add(getRover());
		assertThat(mockRoverRepository.load(new RoverIdentifier(mockUuuid, ROVER_NAME + 1))).isNotNull();
		mockRoverRepository.add(getRover());
		assertThat(mockRoverRepository.load(new RoverIdentifier(mockUuuid, ROVER_NAME + 1))).isNotNull();
		assertThat(mockRoverRepository.getNumberOfRovers()).isEqualTo(2);
	}

	private Rover getRover() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);
		return new Rover(new RoverIdentifier(mockUuuid, ROVER_NAME + (mockRoverRepository.getNumberOfRovers() + 1)),
				coordinates, Orientation.SOUTH);
	}

}
