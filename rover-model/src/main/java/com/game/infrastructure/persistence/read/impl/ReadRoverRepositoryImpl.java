package com.game.infrastructure.persistence.read.impl;

import java.util.LinkedList;
import java.util.List;

import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.repository.ReadRoverRepository;

public class ReadRoverRepositoryImpl implements ReadRoverRepository {
	
	List<RoverIdentifier> roverIds = new LinkedList<>();

	@Override
	public void add(RoverIdentifier roverId) {
		roverIds.add(roverId);
	}

	@Override
	public int getNumberOfEntities() {
		return roverIds.size();
	}

}
