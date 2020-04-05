package com.game.domain.application;

import com.game.domain.model.entity.Plateau;
import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.IllegalArgumentGameException;
import com.game.domain.model.service.PlateauServiceImpl;
import com.game.domain.model.service.RoverServiceImpl;

/**
 * Application service which acts as a facade to the application and delegates
 * the execution of the process to the two Domain services
 * {@link RoverServiceImpl}, {@link PlateauServiceImpl} and the Application
 * State {@link GameContext} All the write commands should have return type =
 * void
 *
 */
public class GameServiceImpl implements GameService {

	GameContext gameContext = GameContext.getInstance();

	public void initializePlateau(TwoDimensionalCoordinates coordinates) {
		Plateau plateau = gameContext.getPlateauService().initializePlateau(coordinates);
		addPlateauToContext(plateau);
	}

	public void initializeRelativisticPlateau(int speed, TwoDimensionalCoordinates coordinates) {
		Plateau plateau = gameContext.getPlateauService().initializeRelativisticPlateau(speed, coordinates);
		addPlateauToContext(plateau);
	}

	public void initializeRover(TwoDimensionalCoordinates coordinates, Orientation orientation) {
		if (!gameContext.isInitialized())
			throw new IllegalArgumentGameException(String.format(GameExceptionLabels.ERROR_MESSAGE_SEPARATION_PATTERN,
					GameExceptionLabels.MISSING_PLATEAU_CONFIGURATION,
					GameExceptionLabels.NOT_ALLOWED_ADDING_ROVER_ERROR));
		int robotNumber = gameContext.getCounter().addAndGet(1);
		gameContext.getRoverService().initializeRover(GameContext.ROVER_NAME_PREFIX + robotNumber, coordinates,
				orientation);
		// if (gameContext.getPlateauService().)
		gameContext.getPlateauService().markLocationBusy(gameContext.getPlateau(), coordinates);
	}

	public void moveRoverwithOrientation(String roverName, Orientation orientation) {
		gameContext.getRoverService().moveRoverWithOrientation(roverName, orientation);
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
		gameContext.addPlateau(plateau);
	}

}
