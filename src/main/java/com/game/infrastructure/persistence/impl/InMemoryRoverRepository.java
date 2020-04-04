package com.game.infrastructure.persistence.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.game.domain.model.GameContext;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.repository.RoverRepository;

/**
 * "Secondary" port interface as described by Alistair CockBurn in his
 * original paper, i.e. port on the right side of the hexagon.
 * https://alistair.cockburn.us/hexagonal-architecture/
 *
 */
public class InMemoryRoverRepository implements RoverRepository {
	
	private AtomicInteger counter = new AtomicInteger(0);
	
	Map<String, Rover> rovers = new ConcurrentHashMap<>();

	public Rover getRover(String name) {
		return rovers.get(name);
	}

	public void addRover(Rover rover) {
		int robotNumber = counter.addAndGet(1);
		rover.setName(GameContext.ROVER_NAME_PREFIX + robotNumber);
		rovers.putIfAbsent(rover.getName(), rover);
	}

	/**
	 * In case for example of a rover moving out of the board, it will be removed
	 * from the game
	 * 
	 * @param roverName
	 */
	public void removeRover(String roverName) {
		rovers.remove(roverName);
	}

	public int getNumberOfRovers() {
		return rovers.size();
	}
	
	public void removeAllRovers() {
		rovers.clear();
	}

}
