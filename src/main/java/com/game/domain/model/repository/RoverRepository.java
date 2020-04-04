package com.game.domain.model.repository;

import com.game.domain.model.entity.Rover;

public interface RoverRepository extends DomainRepository {

	public Rover getRover(String name);

	public void addRover(Rover rover);

	/**
	 * In case for example of a rover moving out of the plateau, it will be removed
	 * from the game
	 * 
	 * @param roverName
	 */
	public void removeRover(String roverName);

	public int getNumberOfRovers();
	
	public void removeAllRovers();

}
