package com.game.domain.model.service;

import java.util.HashMap;
import java.util.Map;

import com.game.infrastructure.persistence.impl.InMemoryRoverRepository;

public class ServiceLocator {

	public static String ROVER_SERVICE = "rover_service";
	
	public static String BOARD_SERVICE = "board_service";

	private Map<String, DomainService> services = new HashMap<>();

	private static ServiceLocator soleInstance = new ServiceLocator();

	static {
		soleInstance.services.put(ROVER_SERVICE, new RoverServiceImpl(new InMemoryRoverRepository()));
		soleInstance.services.put(BOARD_SERVICE, new BoardServiceImpl());
	}

	public static void load(ServiceLocator arg) {
		soleInstance = arg;
	}

	public static RoverServiceImpl getRoverService() {
		return (RoverServiceImpl)soleInstance.services.get(ROVER_SERVICE);
	}
	
	public static BoardServiceImpl getBoardService() {
		return (BoardServiceImpl)soleInstance.services.get(BOARD_SERVICE);
	}

}
