package com.game.domain.model.service;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

	public static String ROVER_SERVICE = "rover_service";
	
	public static String PLATEAU_SERVICE = "plateau_service";

	private Map<String, DomainService> services = new HashMap<>();

	private static ServiceLocator soleInstance = new ServiceLocator();

	public static RoverService getRoverService() {
		return (RoverService) soleInstance.services.get(ROVER_SERVICE);
	}
	
	public static PlateauService getPlateauService() {
		return (PlateauService)soleInstance.services.get(PLATEAU_SERVICE);
	}
	
	public static void load(ServiceLocator arg) {
		soleInstance = arg;
	}
	
	public void loadService (String key, DomainService service) {
	      services.put(key, service);
	  }

}
