package com.game.domain.model.repository;

import java.util.Collection;

import com.game.domain.model.entity.Rover;
import com.game.domain.model.entity.RoverIdentifier;
import com.game.infrastructure.persistence.impl.InMemoryRoverRepositoryImpl;

/**
 * "Secondary" port interface as described by Alistair CockBurn in his original
 * paper, i.e. port on the right side of the hexagon.
 * https://alistair.cockburn.us/hexagonal-architecture/ 
 * Located in the model layer
 * Implemented by the secondary port adapter {@link InMemoryRoverRepositoryImpl} located in infrastructure package/module
 */
public interface RoverRepository extends DomainRepository<Rover, RoverIdentifier> {

	public int getNumberOfRovers();
	
	public void removeAllRovers();
	
	public Collection<Rover> getAllRovers();

}
