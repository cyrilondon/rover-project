package com.game.domain.model.service;

import java.util.HashMap;
import java.util.Map;

import com.game.domain.application.ApplicationService;
import com.game.domain.application.GameService;

public class ServiceLocator {

	public static String GAME_SERVICE = "game_service";

	public static String ROVER_SERVICE = "rover_service";

	public static String PLATEAU_SERVICE = "plateau_service";

	private Map<String, DomainService> domainServices = new HashMap<>();

	private Map<String, ApplicationService> applicationServices = new HashMap<>();

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

	public static void load(ServiceLocator arg) {
		soleInstance = arg;
	}

	public void loadDomainService(String key, DomainService service) {
		domainServices.put(key, service);
	}
	
	public void loadApplicationService(String key, ApplicationService service) {
		applicationServices.put(key, service);
	}	

}
