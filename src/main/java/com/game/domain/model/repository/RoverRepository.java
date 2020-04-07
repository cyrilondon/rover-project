package com.game.domain.model.repository;

import com.game.domain.model.entity.Rover;

/**
 * "Secondary" port interface as described by Alistair CockBurn in his original
 * paper, i.e. port on the right side of the hexagon.
 * https://alistair.cockburn.us/hexagonal-architecture/ 
 * Located in the model layer
 * Implemented by the secondary port adapter {@link InMemoryRoverRepositoryImpl} located in infrastructure package/module
 */

public interface RoverRepository extends DomainRepository<Rover, String> {

	public int getNumberOfRovers();
	
	public void removeAllRovers();

}
