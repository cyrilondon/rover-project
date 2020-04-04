package com.game.domain.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.game.domain.model.GameContext;
import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.repository.RoverRepository;
import com.game.infrastructure.persistence.impl.InMemoryRoverRepositoryImpl;

public class RoverServiceImplTest {

	private static final int X = 3;

	private static final int Y = 4;

	private static final String ROVER_PREFIX = "ROVER_TEST_";

	RoverRepository mockRoverRepository = new MockRoverRepository();

	/**
	 * Inject our own Mock Rover repository implementation here
	 */
	private RoverServiceImpl roverService = new RoverServiceImpl(mockRoverRepository);

	private GameContext gameContext = GameContext.getInstance();

	private static final int WIDTH = 3;

	private static final int HEIGHT = 3;

	@BeforeMethod
	public void reset() {
		mockRoverRepository.removeAllRovers();
	}

	@Test
	public void testInitializeRover() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);
		roverService.initializeRover(ROVER_PREFIX + (mockRoverRepository.getNumberOfRovers() + 1), coordinates,
				Orientation.SOUTH);
		Rover rover = mockRoverRepository.getRover(ROVER_PREFIX + 1);
		assertThat(rover.getCoordinates()).isEqualTo(new TwoDimensionalCoordinates(3, 4));
		assertThat(rover.getOrientation()).isEqualTo(Orientation.SOUTH);
	}

	@Test
	public void testFaceToOrientation() {
		mockRoverRepository.addRover(getRover());
		roverService.faceToOrientation(ROVER_PREFIX + 1, Orientation.EAST);
		Rover rover = mockRoverRepository.getRover(ROVER_PREFIX + 1);
		assertThat(rover.getOrientation()).isEqualTo(Orientation.EAST);
	}

	@Test
	public void testMoveRoverwithOrientation() {
		mockRoverRepository.addRover(getRover());
		roverService.moveRoverwithOrientation(ROVER_PREFIX + 1, Orientation.EAST);
		Rover rover = mockRoverRepository.getRover(ROVER_PREFIX + 1);
		assertThat(rover.getCoordinates()).isEqualTo(new TwoDimensionalCoordinates(X + 1, Y));
		assertThat(rover.getOrientation()).isEqualTo(Orientation.EAST);
	}

	@Test
	public void testAddRovers() {
		mockRoverRepository.addRover(getRover());
		assertThat(mockRoverRepository.getRover(ROVER_PREFIX + 1)).isNotNull();
		mockRoverRepository.addRover(getRover());
		assertThat(mockRoverRepository.getRover(ROVER_PREFIX + 2)).isNotNull();
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
		return new Rover(ROVER_PREFIX + (mockRoverRepository.getNumberOfRovers() + 1), coordinates, Orientation.SOUTH);
	}

	/**
	 * Simple MockClass for the RoverRepository We explicitly choose a different
	 * implementation than the {@link InMemoryRoverRepositoryImpl}
	 *
	 */
	private class MockRoverRepository implements RoverRepository {

		List<Rover> rovers = new ArrayList<Rover>();

		@Override
		public Rover getRover(String name) {
			return rovers.get(Integer.valueOf(name.substring(ROVER_PREFIX.length())) - 1);
		}

		@Override
		public void addRover(Rover rover) {
			rovers.add(rover);
		}

		@Override
		public void removeRover(String roverName) {
		}

		@Override
		public int getNumberOfRovers() {
			return rovers.size();
		}

		@Override
		public void removeAllRovers() {
			rovers.clear();
		}

	}

}
