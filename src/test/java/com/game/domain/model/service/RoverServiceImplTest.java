package com.game.domain.model.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.game.domain.model.entity.Plateau;
import com.game.domain.application.GameContext;
import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.exception.EntityValidationException;
import com.game.domain.model.exception.GameExceptionLabels;
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


	/**
	 * Inject our own Mock Rover repository implementation here
	 */
	private RoverServiceImpl roverService = new RoverServiceImpl(mockRoverRepository);

	@BeforeMethod
	public void reset() {
		gameContext.reset();
		mockRoverRepository.removeAllRovers();
	}

	@Test
	public void testInitializeRover() {
		addPlateau(WIDTH, HEIGHT);
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);
		roverService.initializeRover(UUID.randomUUID(), ROVER_PREFIX + (mockRoverRepository.getNumberOfRovers() + 1), coordinates,
				Orientation.SOUTH);
		Rover rover = mockRoverRepository.load(ROVER_PREFIX + 1);
		assertThat(rover.getCoordinates()).isEqualTo(new TwoDimensionalCoordinates(3, 4));
		assertThat(rover.getOrientation()).isEqualTo(Orientation.SOUTH);
	}

	@Test
	public void testInitializeRoverOutOfPlateau() {
		int width = 2, height = 2;
		addPlateau(width, height);
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);

		Throwable thrown = catchThrowable(() -> roverService.initializeRover(UUID.randomUUID(),
				ROVER_PREFIX + (mockRoverRepository.getNumberOfRovers() + 1), coordinates, Orientation.SOUTH));
		
		assertThat(thrown).isInstanceOf(EntityValidationException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ENTITY_VALIDATION_ERROR_CODE,
						String.format(GameExceptionLabels.ROVER_X_OUT_OF_PLATEAU, X, width)
								+ ", " + String.format(GameExceptionLabels.ROVER_Y_OUT_OF_PLATEAU, Y,
										height)));
	}

	@Test
	public void testFaceToOrientation() {
		mockRoverRepository.add(getRover());
		roverService.faceToOrientation(ROVER_PREFIX + 1, Orientation.EAST);
		Rover rover = mockRoverRepository.load(ROVER_PREFIX + 1);
		assertThat(rover.getOrientation()).isEqualTo(Orientation.EAST);
	}

	@Test
	public void testMoveRoverNumberOfTimes() {
		addPlateau(WIDTH, HEIGHT);
		mockRoverRepository.add(getRover());
		roverService.moveRoverNumberOfTimes(UUID.randomUUID(), ROVER_PREFIX + 1, 3);
		Rover rover = mockRoverRepository.load(ROVER_PREFIX + 1);
		assertThat(rover.getCoordinates()).isEqualTo(new TwoDimensionalCoordinates(X, Y-3));
		assertThat(rover.getOrientation()).isEqualTo(Orientation.SOUTH);
	}

	@Test
	public void testAddRovers() {
		mockRoverRepository.add(getRover());
		assertThat(mockRoverRepository.load(ROVER_PREFIX + 1)).isNotNull();
		mockRoverRepository.add(getRover());
		assertThat(mockRoverRepository.load(ROVER_PREFIX + 2)).isNotNull();
		assertThat(mockRoverRepository.getNumberOfRovers()).isEqualTo(2);
	}
//
//	@Test
//	public void testRemoveRover() {
//		gameContext.addBoard(getBoard());
//		gameContext.addRover(getRover());
//		assertThat(gameContext.getRover(GameContext.ROVER_NAME_PREFIX + 1)).isNotNull();
//		gameContext.removeRover(GameContext.ROVER_NAME_PREFIX + 1);
//		assertThat(gameContext.getNumberOfRovers()).isEqualTo(0);
//	}

	private Rover getRover() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);
		return new Rover(UUID.randomUUID(), ROVER_PREFIX + (mockRoverRepository.getNumberOfRovers() + 1), coordinates, Orientation.SOUTH);
	}

	/**
	 * Simple MockClass for the RoverRepository We explicitly choose a different
	 * implementation than the {@link InMemoryRoverRepositoryImpl}
	 *
	 */
	private class MockRoverRepository implements RoverRepository {

		List<Rover> rovers = new ArrayList<Rover>();

		@Override
		public Rover load(String name) {
			return rovers.get(Integer.valueOf(name.substring(ROVER_PREFIX.length())) - 1);
		}

		@Override
		public void add(Rover rover) {
			rovers.add(rover);
		}

		@Override
		public void remove(String roverName) {
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

	}

	
	private void addPlateau(int width, int height) {
		gameContext.addPlateau(
				new Plateau(UUID.randomUUID(), new TwoDimensions(new TwoDimensionalCoordinates(width, height))).initializeLocations());
	}

}
