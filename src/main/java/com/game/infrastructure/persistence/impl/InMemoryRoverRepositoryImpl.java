package com.game.infrastructure.persistence.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.game.domain.model.entity.Rover;
import com.game.domain.model.repository.RoverRepository;

/**
 * Repository adapter as defined by Hexagonal Architecture
 * Should implement a repository port/interface from the model
 *
 */
public class InMemoryRoverRepositoryImpl implements RoverRepository {

	Map<String, Rover> rovers = new ConcurrentHashMap<>();

	@Override
	public Rover load(String name) {
		return rovers.get(name);
	}

	@Override
	public void add(Rover rover) {
		rovers.putIfAbsent(rover.getName(), rover);
	}

	@Override
	public void update(Rover rover) {
		rovers.put(rover.getName(), rover);
	}

	@Override
	public void remove(String name) {
		rovers.remove(name);

	}
	
	public int getNumberOfRovers() {
		return rovers.size();
	}

	public void removeAllRovers() {
		rovers.clear();
	}


}
