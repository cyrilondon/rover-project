package com.game.adapter.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.game.domain.application.GameContext;
import com.game.domain.application.GameService;
import com.game.domain.application.command.ApplicationCommand;
import com.game.domain.application.command.plateau.PlateauInitializeCommand;
import com.game.domain.application.command.rover.RoverInitializeCommand;
import com.game.domain.application.command.rover.RoverMoveCommand;
import com.game.domain.application.command.rover.RoverTurnCommand;
import com.game.domain.model.entity.RoverIdentifier;
import com.game.domain.model.entity.RoverTurnInstruction;

/**
 * Primary adapter as defined by Hexagonal Architecture
 * Clients using a File to start a game should use this adapter as unique contact point to
 * the rest of the application.
 * The bridge with the Domain is done via the  application service interface {@link GameService}
 */
public class GameFileAdapter {

	GameService gameService = GameContext.getInstance().getGameService();

	public void executeGame(File file) {
		gameService.execute(getCommandsFromFile(file));
	}

	/**
	 * We mock the File Parsing here as we are only
	 * interested in the domain model in this presentation.
	 * We leave it to you as an exercise ;-)
	 * @param file
	 * @return
	 */
	private List<ApplicationCommand> getCommandsFromFile(File file) {
		
		UUID plateauId = UUID.randomUUID();
		// ********* Given **********
		// initialization plateau command (5,5)
		List<ApplicationCommand> commands = new ArrayList<>();
		commands.add(new PlateauInitializeCommand.Builder().withObserverSpeed(0).withId(plateauId).withAbscissa(5)
				.withOrdinate(5).build());

		// rover1 commands
		// Rover 1 initialization (1,2) and Orientation 'N' 
		String rover1Name = GameContext.ROVER_NAME_PREFIX + 1;
		commands.add(new RoverInitializeCommand.Builder().withPlateauUuid(plateauId).withName(rover1Name)
				.withAbscissa(1).withOrdinate(2).withOrientation('N').build());
		RoverIdentifier rover1 = new RoverIdentifier(plateauId, rover1Name);
		// Rover 1 move commands  LMLMLMLMMs
		commands.add(new RoverTurnCommand(rover1, RoverTurnInstruction.LEFT));
		commands.add(new RoverMoveCommand(rover1, 1));
		commands.add(new RoverTurnCommand(rover1, RoverTurnInstruction.LEFT));
		commands.add(new RoverMoveCommand(rover1, 1));
		commands.add(new RoverTurnCommand(rover1, RoverTurnInstruction.LEFT));
		commands.add(new RoverMoveCommand(rover1, 1));
		commands.add(new RoverTurnCommand(rover1, RoverTurnInstruction.LEFT));
		commands.add(new RoverMoveCommand(rover1, 2));

		// rover2 commands
		// rover 2 initialization  (3,2) and Orientation 'E'
		String rover2Name = GameContext.ROVER_NAME_PREFIX + 2;
		commands.add(new RoverInitializeCommand.Builder().withPlateauUuid(plateauId).withName(rover2Name)
				.withAbscissa(3).withOrdinate(3).withOrientation('E').build());
		RoverIdentifier rover2 = new RoverIdentifier(plateauId, rover2Name);
		// Rover 2 move commands  MMRMMRMRRM
		commands.add(new RoverMoveCommand(rover2, 2));
		commands.add(new RoverTurnCommand(rover2, RoverTurnInstruction.RIGHT));
		commands.add(new RoverMoveCommand(rover2, 2));
		commands.add(new RoverTurnCommand(rover2, RoverTurnInstruction.RIGHT));
		commands.add(new RoverMoveCommand(rover2, 1));
		commands.add(new RoverTurnCommand(rover2, RoverTurnInstruction.RIGHT));
		commands.add(new RoverTurnCommand(rover2, RoverTurnInstruction.RIGHT));
		commands.add(new RoverMoveCommand(rover2, 1));
		
		return commands;
	}

}
