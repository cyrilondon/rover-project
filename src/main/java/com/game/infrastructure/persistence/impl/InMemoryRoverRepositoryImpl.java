package com.game.infrastructure.persistence.impl;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.game.domain.model.entity.rover.Rover;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.exception.RoverNotFoundException;
import com.game.domain.model.repository.RoverRepository;

/**
 * Repository adapter as defined by Hexagonal Architecture
 * Should implement a repository port/interface from the model
 *
 */
public class InMemoryRoverRepositoryImpl implements RoverRepository {

	Map<RoverIdentifier, Rover> rovers = new ConcurrentHashMap<>();

	@Override
	public Rover load(RoverIdentifier id) {
		if (rovers.get(id) == null) {
			throw new RoverNotFoundException(id);
		}
		return rovers.get(id);
	}

	@Override
	public void add(Rover rover) {
		rovers.putIfAbsent(rover.getId(), rover);
	}

	@Override
	public void update(Rover rover) {
		rovers.put(rover.getId(), rover);
	}

	@Override
	public void remove(RoverIdentifier id) {
		rovers.remove(id);

	}
	
	public int getNumberOfRovers() {
		return rovers.size();
	}

	public void removeAllRovers() {
		rovers.clear();
	}

	@Override
	public Collection<Rover> getAllRovers() {
		return rovers.values();
	}


}
