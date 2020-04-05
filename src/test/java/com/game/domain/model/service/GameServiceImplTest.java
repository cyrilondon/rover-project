package com.game.domain.model.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.game.domain.model.GameContext;
import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Plateau;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.entity.dimensions.RelativisticTwoDimensions;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.IllegalArgumentGameException;

public class GameServiceImplTest {

	private static final int WIDTH = 5;

	private static final int HEIGHT = 5;

	private static final int X = 3;

	private static final int Y = 4;

	private GameContext gameContext = GameContext.getInstance();

	private GameService gameService = new GameServiceImpl();

	public List<Rover> roversList = new ArrayList<Rover>();

	public Plateau plateau;

	@BeforeTest
	public void setup() {
		ServiceLocator mockServiceLocator = new ServiceLocator();
		mockServiceLocator.loadService(ServiceLocator.ROVER_SERVICE, new MockRoverServiceImpl());
		mockServiceLocator.loadService(ServiceLocator.PLATEAU_SERVICE, new MockPlateauServiceImpl());
		ServiceLocator.load(mockServiceLocator);
	}

	@BeforeMethod
	public void resetGame() {
		gameContext.reset();
		roversList.clear();
	}

	@Test
	public void testInitializePlateau() {
		gameService.initializePlateau(new TwoDimensionalCoordinates(WIDTH, HEIGHT));
		assertThat(plateau.getWidth()).isEqualTo(WIDTH);
		assertThat(plateau.getHeight()).isEqualTo(HEIGHT);
		assertThat(gameContext.isInitialized());
		assertThat(gameContext.getPlateau()).isEqualTo(plateau);
	}

	@Test
	public void testInitializeRelativisticPlateau() {
		gameService.initializeRelativisticPlateau(12, new TwoDimensionalCoordinates(WIDTH, HEIGHT));
		assertThat(plateau.getWidth()).isEqualTo(WIDTH - 1);
		assertThat(plateau.getHeight()).isEqualTo(HEIGHT - 1);
		assertThat(gameContext.isInitialized());
		assertThat(gameContext.getPlateau()).isEqualTo(plateau);
	}

	@Test
	public void testInitializeRover() {
		gameContext.addPlateau(getPlateau());
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);
		gameService.initializeRover(coordinates, Orientation.SOUTH);
		assertThat(roversList.contains(
				new Rover(GameContext.ROVER_NAME_PREFIX + gameContext.getCounter(), coordinates, Orientation.SOUTH)))
						.isTrue();
		gameService.initializeRover(coordinates, Orientation.EAST);
		assertThat(roversList.contains(
				new Rover(GameContext.ROVER_NAME_PREFIX + gameContext.getCounter(), coordinates, Orientation.EAST)))
						.isTrue();
	}

	/**
	 * Expected error message: "[ERR-000] Missing Plateau configuration - It is not
	 * allowed to add a Rover. Please initialize the Plateau first."
	 */
	@Test
	public void testInitializeRoverWithoutPlateau() {
		Throwable thrown = catchThrowable(
				() -> gameService.initializeRover(new TwoDimensionalCoordinates(X, Y), Orientation.SOUTH));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
						String.format(GameExceptionLabels.ERROR_MESSAGE_SEPARATION_PATTERN,
								GameExceptionLabels.MISSING_PLATEAU_CONFIGURATION,
								GameExceptionLabels.NOT_ALLOWED_ADDING_ROVER_ERROR)));
	}
	
	
	public void testMoveRoverWithOrientation() {
		String roverName = GameContext.ROVER_NAME_PREFIX +3;
		gameService.moveRoverwithOrientation(roverName, Orientation.SOUTH);
		assertThat(roversList).contains(new Rover(roverName, new TwoDimensionalCoordinates(2,3), Orientation.SOUTH));
		
	}

	private Plateau getPlateau() {
		return new Plateau(new TwoDimensions(new TwoDimensionalCoordinates(WIDTH, HEIGHT)));
	}

	/**
	 * Simple MockClass for the RoverServiceImpl
	 *
	 */
	private class MockRoverServiceImpl implements RoverService {

		@Override
		public void initializeRover(String roverName, TwoDimensionalCoordinates coordinates, Orientation orientation) {
			GameServiceImplTest.this.roversList.add(new Rover(roverName, coordinates, orientation));
		}

		@Override
		public void faceToOrientation(String roverName, Orientation orientation) {

		}

		@Override
		public void moveRoverWithOrientation(String roverName, Orientation orientation) {
			GameServiceImplTest.this.roversList.add(new Rover(roverName, new TwoDimensionalCoordinates(2,3), orientation));
		}

	}

	/**
	 * Simple MockClass for the RoverServiceImpl
	 *
	 */
	private class MockPlateauServiceImpl implements PlateauService {

		@Override
		public Plateau initializePlateau(TwoDimensionalCoordinates coordinates) {
			GameServiceImplTest.this.plateau = new Plateau(new TwoDimensions(
					new TwoDimensionalCoordinates(coordinates.getAbscissa(), coordinates.getOrdinate())));
			return GameServiceImplTest.this.plateau;
		}

		@Override
		public Plateau initializeRelativisticPlateau(int speed, TwoDimensionalCoordinates coordinates) {
			GameServiceImplTest.this.plateau = new Plateau(new RelativisticTwoDimensions(speed,
					new TwoDimensionalCoordinates(coordinates.getAbscissa(), coordinates.getOrdinate())));
			return GameServiceImplTest.this.plateau;
		}

	}

}
