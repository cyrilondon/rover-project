package com.game.domain.application;

import java.util.UUID;

import com.game.domain.application.command.InitializePlateauCommand;
import com.game.domain.application.command.InitializeRoverCommand;
import com.game.domain.application.command.MoveRoverCommand;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

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
	 * 
	 * @param speed
	 * @param coordinates
	 */
	public void initializeRelativisticPlateau(UUID uuid, int speed, TwoDimensionalCoordinates coordinates);

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

}
