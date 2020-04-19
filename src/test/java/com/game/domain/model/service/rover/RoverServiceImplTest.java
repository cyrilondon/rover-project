package com.game.domain.model.service.rover;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.entity.plateau.Plateau;
import com.game.domain.model.entity.rover.Orientation;
import com.game.domain.model.entity.rover.Rover;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.event.DomainEventPublisherSubscriber;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.RoverInitializationException;
import com.game.domain.model.repository.RoverRepository;
import com.game.infrastructure.persistence.impl.InMemoryRoverRepositoryImpl;

public class RoverServiceImplTest {

	private static final int X = 3;

	private static final int Y = 4;

	private static final int WIDTH = 10;

	private static final int HEIGHT = 10;

	private static final String ROVER_PREFIX = "ROVER_TEST_";

	RoverRepository mockRoverRepository = new MockRoverRepository();
	
	private GameContext gameContext = GameContext.getInstance();
	
	private final UUID mockUuuid = UUID.fromString("53567a5d-a21c-495e-80a3-d12adaf8585c");


	/**
	 * Inject our own Mock Rover repository implementation here
	 */
	private RoverServiceImpl roverService = new RoverServiceImpl(mockRoverRepository);

	@BeforeMethod
	public void reset() {
		gameContext.reset();
		DomainEventPublisherSubscriber.instance().clear();
		mockRoverRepository.removeAllRovers();
	}

	@Test
	public void testInitializeRover() {
		UUID uuid = UUID.randomUUID();
		addPlateau(uuid, WIDTH, HEIGHT);
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);
		RoverIdentifier id = new RoverIdentifier(uuid, ROVER_PREFIX + (mockRoverRepository.getNumberOfRovers() + 1));
		roverService.initializeRover(id, coordinates,
				Orientation.SOUTH);
		Rover rover = mockRoverRepository.load(id);
		assertThat(rover.getCoordinates()).isEqualTo(new TwoDimensionalCoordinates(3, 4));
		assertThat(rover.getOrientation()).isEqualTo(Orientation.SOUTH);
	}

	@Test
	public void testInitializeRoverOutOfPlateau() {
		int width = 2, height = 2;
		UUID uuid = UUID.randomUUID();
		addPlateau(uuid, width, height);
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);
		RoverIdentifier id = new RoverIdentifier(uuid, ROVER_PREFIX + (mockRoverRepository.getNumberOfRovers() + 1));
		Throwable thrown = catchThrowable(() -> roverService.initializeRover(id, coordinates, Orientation.SOUTH));
		
		assertThat(thrown).isInstanceOf(RoverInitializationException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ENTITY_VALIDATION_ERROR_CODE,
						String.format(GameExceptionLabels.ROVER_X_OUT_OF_PLATEAU, X, width)
								+ ", " + String.format(GameExceptionLabels.ROVER_Y_OUT_OF_PLATEAU, Y,
										height)));
	}

	@Test
	public void testMoveRoverNumberOfTimes() {
		UUID uuid = UUID.randomUUID();
		addPlateau(uuid, WIDTH, HEIGHT);
		mockRoverRepository.add(getRover());
		RoverIdentifier id = new RoverIdentifier(uuid, ROVER_PREFIX + 1);
		roverService.moveRoverNumberOfTimes(id, 3);
		Rover rover = mockRoverRepository.load(id);
		assertThat(rover.getCoordinates()).isEqualTo(new TwoDimensionalCoordinates(X, Y-3));
		assertThat(rover.getOrientation()).isEqualTo(Orientation.SOUTH);
	}

	@Test
	public void testAddRovers() {
		mockRoverRepository.add(getRover());
		assertThat(mockRoverRepository.load(new RoverIdentifier(mockUuuid, ROVER_PREFIX + 1))).isNotNull();
		mockRoverRepository.add(getRover());
		assertThat(mockRoverRepository.load(new RoverIdentifier(mockUuuid, ROVER_PREFIX + 1))).isNotNull();
		assertThat(mockRoverRepository.getNumberOfRovers()).isEqualTo(2);
	}

	private Rover getRover() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);
		return new Rover(new RoverIdentifier(mockUuuid, ROVER_PREFIX + (mockRoverRepository.getNumberOfRovers() + 1)), coordinates, Orientation.SOUTH);
	}

	/**
	 * Simple MockClass for the RoverRepository We explicitly choose a different
	 * implementation than the {@link InMemoryRoverRepositoryImpl}
	 *
	 */
	private class MockRoverRepository implements RoverRepository {

		List<Rover> rovers = new ArrayList<Rover>();

		@Override
		public Rover load(RoverIdentifier id) {
			return rovers.get(Integer.valueOf(id.getName().substring(ROVER_PREFIX.length())) - 1);
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

	
	private void addPlateau(UUID uuid, int width, int height) {
		gameContext.addPlateau(
				new Plateau(uuid, new TwoDimensions(new TwoDimensionalCoordinates(width, height))).initializeLocations());
	}

}
