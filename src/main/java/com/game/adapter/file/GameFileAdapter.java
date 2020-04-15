package com.game.adapter.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.game.domain.application.GameContext;
import com.game.domain.application.GameService;
import com.game.domain.application.command.ApplicationCommand;
import com.game.domain.application.command.InitializePlateauCommand;
import com.game.domain.application.command.InitializeRoverCommand;
import com.game.domain.application.command.MakeTurnRoverCommand;
import com.game.domain.application.command.MoveRoverCommand;
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

	private List<ApplicationCommand> getCommandsFromFile(File file) {
		
		UUID plateauUuid = UUID.randomUUID();
		// ********* Given **********
		// plateau command
		List<ApplicationCommand> commands = new ArrayList<>();
		commands.add(new InitializePlateauCommand.Builder().withObserverSpeed(0).withUuid(plateauUuid).withAbscissa(5)
				.withOrdinate(5).build());

		// rover1 commands
		// Rover 1 initialization + sLMLMLMLMMs
		String rover1Name = GameContext.ROVER_NAME_PREFIX + 1;
		commands.add(new InitializeRoverCommand.Builder().withPlateauUuid(plateauUuid).withName(rover1Name)
				.withAbscissa(1).withOrdinate(2).withOrientation('N').build());
		RoverIdentifier rover1 = new RoverIdentifier(plateauUuid, rover1Name);
		commands.add(new MakeTurnRoverCommand(rover1, RoverTurnInstruction.LEFT));
		commands.add(new MoveRoverCommand(rover1, 1));
		commands.add(new MakeTurnRoverCommand(rover1, RoverTurnInstruction.LEFT));
		commands.add(new MoveRoverCommand(rover1, 1));
		commands.add(new MakeTurnRoverCommand(rover1, RoverTurnInstruction.LEFT));
		commands.add(new MoveRoverCommand(rover1, 1));
		commands.add(new MakeTurnRoverCommand(rover1, RoverTurnInstruction.LEFT));
		commands.add(new MoveRoverCommand(rover1, 2));

		// rover2 commands
		// rover 2 initialization + MMRMMRMRRM
		String rover2Name = GameContext.ROVER_NAME_PREFIX + 2;
		commands.add(new InitializeRoverCommand.Builder().withPlateauUuid(plateauUuid).withName(rover2Name)
				.withAbscissa(3).withOrdinate(3).withOrientation('E').build());
		RoverIdentifier rover2 = new RoverIdentifier(plateauUuid, rover2Name);
		commands.add(new MoveRoverCommand(rover2, 2));
		commands.add(new MakeTurnRoverCommand(rover2, RoverTurnInstruction.RIGHT));
		commands.add(new MoveRoverCommand(rover2, 2));
		commands.add(new MakeTurnRoverCommand(rover2, RoverTurnInstruction.RIGHT));
		commands.add(new MoveRoverCommand(rover2, 1));
		commands.add(new MakeTurnRoverCommand(rover2, RoverTurnInstruction.RIGHT));
		commands.add(new MakeTurnRoverCommand(rover2, RoverTurnInstruction.RIGHT));
		commands.add(new MoveRoverCommand(rover2, 1));
		
		return commands;
	}

}
