package com.game.domain.application.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import com.game.core.validation.ArgumentCheck;
import com.game.domain.application.service.GameService;
import com.game.domain.application.service.GameServiceImpl;
import com.game.domain.model.entity.dimensions.RelativisticTwoDimensions;
import com.game.domain.model.entity.plateau.Plateau;
import com.game.domain.model.event.DomainEvent;
import com.game.domain.model.event.store.EventStore;
import com.game.domain.model.event.store.EventStoreImpl;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.service.locator.ServiceLocator;
import com.game.domain.model.service.plateau.PlateauService;
import com.game.domain.model.service.plateau.PlateauServiceImpl;
import com.game.domain.model.service.rover.RoverService;
import com.game.domain.model.service.rover.RoverServiceImpl;
import com.game.infrastructure.persistence.impl.InMemoryPlateauRepositoryImpl;
import com.game.infrastructure.persistence.impl.InMemoryRoverRepositoryImpl;

/**
 * Application context whose responsibility is to keep track of the game state.
 * Exposed as a singleton to the rest of the application via
 * {@link GameContext#getInstance()} or
 * {@link GameContext#getInstance(int step)} if we want the Rover to move with a
 * step length different than the default one = 1 Equivalent to an Application
 * Spring context
 * Provides the different application and domain services (via service locator) 
 * to the rest of the application
 * Defines as well the way the event are stored via a Java 8 function {@link #storeEventFunction}
 * which simulates a kind of poor AOP (i.e externalize the code to run somewhere in the application)
 *
 */
public class GameContext {

	public static final String ROVER_NAME_PREFIX = "ROVER_";

	/**
	 * By default, the rover moves by one step forward
	 */
	public static final int ROVER_STEP_LENGTH = 1;
	
	/**
	 * Minimal speed for which relativistic effects should be taken in effect
	 * Here speed of light / 3
	 */
	public static final int MINIMAL_RELATIVISTIC_SPEED = RelativisticTwoDimensions.SPEED_OF_LIGHT/3; 

	private int roverStepLength = ROVER_STEP_LENGTH;

	private static GameContext GAME_CONTEXT = new GameContext();

	private Map<UUID, Plateau> plateauMap = new ConcurrentHashMap<>();
	
	public static final Function<DomainEvent, Void> storeEventFunction = event -> {
		getInstance().getEventStore().addEvent(event);
		return null;
	};

	private GameContext() {
		configure();
	}

	/**
	 * Configure the game with the on-demand implementations
	 */
	private void configure() {
		ServiceLocator locator = new ServiceLocator();
		locator.loadApplicationService(ServiceLocator.GAME_SERVICE, new GameServiceImpl());
		PlateauService plateauService = new PlateauServiceImpl(new InMemoryPlateauRepositoryImpl());
		locator.loadDomainService(ServiceLocator.PLATEAU_SERVICE, plateauService);
		locator.loadDomainService(ServiceLocator.ROVER_SERVICE, new RoverServiceImpl(plateauService , new InMemoryRoverRepositoryImpl()));
		locator.loadEventStore(ServiceLocator.EVENT_STORE, new EventStoreImpl());
		ServiceLocator.load(locator);
	}

	public static GameContext getInstance() {
		return GAME_CONTEXT;
	}

	public static GameContext getInstance(int step) {
		GAME_CONTEXT.roverStepLength = step;
		return GAME_CONTEXT;
	}
	
	public GameService getGameService() {
		return ServiceLocator.getGameService();
	}

	public RoverService getRoverService() {
		return ServiceLocator.getRoverService();
	}

	public PlateauService getPlateauService() {
		return ServiceLocator.getPlateauService();
	}
	
	public EventStore getEventStore() {
		return ServiceLocator.getEventStore();
	}

	public int getRoverStepLength() {
		return roverStepLength;
	}

	/**
	 * Adding a plateau to the game will initialize the game
	 *  Rovers are then allowed to be added/initialized as well
	 */
	 public Plateau addPlateau(Plateau plateau) {
		 plateauMap.putIfAbsent(plateau.getId(), ArgumentCheck.preNotNull(plateau, GameExceptionLabels.MISSING_PLATEAU_CONFIGURATION));
		 return plateau;
	}

	public void reset() {
		plateauMap.clear();
		roverStepLength = 1;
		configure();
	}

	public Plateau getPlateau(UUID uuid) {
		return plateauMap.get(uuid);
	}
	
	public List<Plateau> getAllPlateau(){
		return new ArrayList<Plateau>(plateauMap.values());
	}

}
