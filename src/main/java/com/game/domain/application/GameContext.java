package com.game.domain.application;

import java.util.concurrent.atomic.AtomicInteger;

import com.game.core.validation.ArgumentCheck;
import com.game.domain.model.entity.Plateau;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.service.PlateauService;
import com.game.domain.model.service.PlateauServiceImpl;
import com.game.domain.model.service.RoverService;
import com.game.domain.model.service.RoverServiceImpl;
import com.game.domain.model.service.ServiceLocator;
import com.game.infrastructure.persistence.impl.InMemoryPlateauRepositoryImpl;
import com.game.infrastructure.persistence.impl.InMemoryRoverRepositoryImpl;

/**
 * Application context whose responsibility is to keep track of the game state.
 * Exposed as a singleton to the rest of the application via
 * {@link GameContext#getInstance()} or
 * {@link GameContext#getInstance(int step)} if we want the Rover to move with a
 * step length different than the default one = 1 Equivalent to an Application
 * Spring context
 *
 */
public class GameContext {

	public static final String ROVER_NAME_PREFIX = "ROVER_";

	public static final int ROVER_STEP_LENGTH = 1;

	/**
	 * By default, the rover moves one step forward
	 */
	private int roverStepLength = ROVER_STEP_LENGTH;

	private static GameContext GAME_CONTEXT = new GameContext();

	AtomicInteger counter = new AtomicInteger(0);

	private Plateau plateau;

	private GameContext() {
		configure();
	}

	/**
	 * Configure the game with the on-demand implementations
	 */
	private void configure() {
		ServiceLocator locator = new ServiceLocator();
		locator.loadService(ServiceLocator.ROVER_SERVICE, new RoverServiceImpl(new InMemoryRoverRepositoryImpl()));
		locator.loadService(ServiceLocator.PLATEAU_SERVICE, new PlateauServiceImpl(new InMemoryPlateauRepositoryImpl()));
		ServiceLocator.load(locator);
	}

	public static GameContext getInstance() {
		return GAME_CONTEXT;
	}

	public static GameContext getInstance(int step) {
		GAME_CONTEXT.roverStepLength = step;
		return GAME_CONTEXT;
	}

	public RoverService getRoverService() {
		return ServiceLocator.getRoverService();
	}

	public PlateauService getPlateauService() {
		return ServiceLocator.getPlateauService();
	}

	public int getRoverStepLength() {
		return roverStepLength;
	}

	/**
	 * Adding a plateau to the game will initialize the game
	 *  Rovers are then allowed to be added/initialized as well
	 */
	 public void addPlateau(Plateau plateau) {
		reset();
		this.plateau = ArgumentCheck.preNotNull(plateau, GameExceptionLabels.MISSING_PLATEAU_CONFIGURATION);
	}

	public void reset() {
		plateau = null;
		roverStepLength = 1;
		counter = new AtomicInteger(0);
	}

	public Plateau getPlateau() {
		return plateau;
	}

	public AtomicInteger getCounter() {
		return counter;
	}

}
