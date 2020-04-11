package com.game.domain.application;

import com.game.domain.application.command.MakeTurnRoverCommand;
import com.game.domain.application.command.InitializePlateauCommand;
import com.game.domain.application.command.InitializeRoverCommand;
import com.game.domain.application.command.MoveRoverCommand;

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

}
