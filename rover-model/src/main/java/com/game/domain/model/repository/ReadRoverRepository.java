package com.game.domain.model.repository;

import com.game.domain.model.entity.rover.RoverIdentifier;

public interface ReadRoverRepository extends ReadRepository<RoverIdentifier>{

	@Override
	public void add(RoverIdentifier entityId);

	@Override
	public int getNumberOfEntities();

}
