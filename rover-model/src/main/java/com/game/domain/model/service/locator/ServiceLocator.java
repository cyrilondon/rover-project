package com.game.domain.model.service.locator;

import java.util.HashMap;
import java.util.Map;

import com.game.domain.application.service.ApplicationService;
import com.game.domain.application.service.GameService;
import com.game.domain.model.event.store.EventStore;
import com.game.domain.model.service.DomainService;
import com.game.domain.model.service.plateau.PlateauService;
import com.game.domain.model.service.rover.RoverService;

public class ServiceLocator {

	public static String GAME_SERVICE = "game_service";

	public static String ROVER_SERVICE = "rover_service";

	public static String PLATEAU_SERVICE = "plateau_service";
	
	public static String EVENT_STORE = "event_store";

	private Map<String, DomainService> domainServices = new HashMap<>();

	private Map<String, ApplicationService> applicationServices = new HashMap<>();
	
	private Map<String, EventStore> eventStore = new HashMap<>();

	private static ServiceLocator soleInstance = new ServiceLocator();
	
	public static GameService getGameService() {
		return (GameService) soleInstance.applicationServices.get(GAME_SERVICE);
	}

	public static RoverService getRoverService() {
		return (RoverService) soleInstance.domainServices.get(ROVER_SERVICE);
	}

	public static PlateauService getPlateauService() {
		return (PlateauService) soleInstance.domainServices.get(PLATEAU_SERVICE);
	}
	
	public static EventStore getEventStore() {
		return (EventStore) soleInstance.eventStore.get(EVENT_STORE);
	}

	public static void load(ServiceLocator arg) {
		soleInstance = arg;
	}

	public void loadDomainService(String key, DomainService service) {
		domainServices.put(key, service);
	}
	
	public void loadApplicationService(String key, ApplicationService service) {
		applicationServices.put(key, service);
	}
	
	public void loadEventStore(String key, EventStore store) {
		eventStore.put(key, store);
	}

}
