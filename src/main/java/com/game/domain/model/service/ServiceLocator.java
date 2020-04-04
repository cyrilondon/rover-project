package com.game.domain.model.service;

import java.util.HashMap;
import java.util.Map;

import com.game.infrastructure.persistence.impl.InMemoryRoverRepositoryImpl;

public class ServiceLocator {

	public static String ROVER_SERVICE = "rover_service";
	
	public static String PLATEAU_SERVICE = "plateau_service";

	private Map<String, DomainService> services = new HashMap<>();

	private static ServiceLocator soleInstance = new ServiceLocator();

	static {
		soleInstance.services.put(ROVER_SERVICE, new RoverServiceImpl(new InMemoryRoverRepositoryImpl()));
		soleInstance.services.put(PLATEAU_SERVICE, new PlateauServiceImpl());
	}

	public static void load(ServiceLocator arg) {
		soleInstance = arg;
	}

	public static RoverServiceImpl getRoverService() {
		return (RoverServiceImpl)soleInstance.services.get(ROVER_SERVICE);
	}
	
	public static PlateauServiceImpl getPlateauService() {
		return (PlateauServiceImpl)soleInstance.services.get(PLATEAU_SERVICE);
	}

}
