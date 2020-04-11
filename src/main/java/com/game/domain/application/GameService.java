package com.game.domain.application;

import com.game.domain.application.command.MakeTurnRoverCommand;

import java.util.List;
import java.util.UUID;

import com.game.domain.application.command.InitializePlateauCommand;
import com.game.domain.application.command.InitializeRoverCommand;
import com.game.domain.application.command.MoveRoverCommand;
import com.game.domain.model.entity.Rover;

/**
 * 
 *
 */
public interface GameService extends ApplicationService {

	/**
	 * Initializes the plateau
	 * @param command
	 */
	public void execute(InitializePlateauCommand command);

	/**
	 * Initializes the rover
	 * @param command
	 */
	public void execute(InitializeRoverCommand command);

	/**
	 * Makes the rover move 
	 * @param command
	 */
	public void execute(MoveRoverCommand command);

	/**
	 * Makes the rover face given orientation
	 * @param command
	 */
	void execute(MakeTurnRoverCommand command);

	/**Get all the rovers of a given plateau at a given time
	 * 
	 * @param uuid
	 * @return
	 */
	List<Rover> getAllRoversByPlateau(UUID uuid);

}
