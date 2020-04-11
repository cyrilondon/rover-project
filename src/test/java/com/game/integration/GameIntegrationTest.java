package com.game.integration;
import java.util.UUID;

import com.game.domain.application.GameContext;
import com.game.domain.application.GameService;
import com.game.domain.application.command.InitializePlateauCommand;
import com.game.domain.application.command.InitializeRoverCommand;
import com.game.domain.application.command.MoveRoverCommand;

public class GameIntegrationTest {

	public static void main(String[] args) {

		GameService gameService = GameContext.getInstance().getGameService();

		// initialize plateau command
		UUID plateauUuid = UUID.randomUUID();
		InitializePlateauCommand plateauCommand = new InitializePlateauCommand.Builder().withObserverSpeed(0)
				.withUuid(plateauUuid).withAbscissa(5).withOrdinate(7).build();

		// initialize Rover command
		int x = 3, y = 8;
		String rover1 = GameContext.ROVER_NAME_PREFIX + 1;
		InitializeRoverCommand intializeRoverCommand = new InitializeRoverCommand.Builder().withPlateauUuid(plateauUuid)
				.withName(rover1).withAbscissa(x).withOrdinate(y).withOrientation('W').build();

		// executes commands
		gameService.execute(plateauCommand);

		gameService.execute(intializeRoverCommand);

		// then
		// [Rover [ROVER_1] attached to Plateau [3aa891b8-80e5-473e-a0c5-a9dbefe5af81]
		// with [Coordinates [abscissa = 3, ordinate = 4]] and [Orientation [WEST]]]
		System.out.println(gameService.getAllRoversByPlateau(plateauUuid));

		// initialize Rover command
		int x2 = 2, y2 = 6;
		String rover2 = GameContext.ROVER_NAME_PREFIX + 2;
		InitializeRoverCommand intializeRoverCommand2 = new InitializeRoverCommand.Builder()
				.withPlateauUuid(plateauUuid).withName(rover2).withAbscissa(x2).withOrdinate(y2).withOrientation('N')
				.build();
		
		gameService.execute(intializeRoverCommand2);

		// then
		//[Rover [ROVER_1] attached to Plateau [101d04e5-7ba1-4d73-8ef6-11dd4a746a7a] with [Coordinates [abscissa = 1, ordinate = 4]] and [Orientation [WEST]],
		//s Rover [ROVER_2] attached to Plateau [101d04e5-7ba1-4d73-8ef6-11dd4a746a7a] with [Coordinates [abscissa = 2, ordinate = 6]] and [Orientation [SOUTH]]]
		System.out.println(gameService.getAllRoversByPlateau(plateauUuid));
	
		gameService.execute(new MoveRoverCommand(plateauUuid, rover1, 1));
		
		//[Rover [ROVER_1] attached to Plateau [590b0b3e-9c11-4380-9af4-362597d53572] with [Coordinates [abscissa = 1, ordinate = 4]] and [Orientation [WEST]],
		// Rover [ROVER_2] attached to Plateau [590b0b3e-9c11-4380-9af4-362597d53572] with [Coordinates [abscissa = 2, ordinate = 6]] and [Orientation [SOUTH]]]
		System.out.println(gameService.getAllRoversByPlateau(plateauUuid));
		
		gameService.execute(new MoveRoverCommand(plateauUuid, rover2, 2));
		System.out.println(gameService.getAllRoversByPlateau(plateauUuid));

	}

}
