package com.game.integration;

import java.util.UUID;

import com.game.domain.application.GameContext;
import com.game.domain.application.GameService;
import com.game.domain.application.command.InitializePlateauCommand;
import com.game.domain.application.command.InitializeRoverCommand;
import com.game.domain.application.command.MakeTurnRoverCommand;
import com.game.domain.application.command.MoveRoverCommand;
import com.game.domain.model.entity.Plateau;
import com.game.domain.model.entity.RoverIdentifier;
import com.game.domain.model.entity.RoverInstruction;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.event.store.EventStore;

public class GameIntegrationTest {

	GameService gameService = GameContext.getInstance().getGameService();

	EventStore eventStore = GameContext.getInstance().getEventStore();

	public static void main(String[] args) {
		
		GameIntegrationTest integrationTest = new GameIntegrationTest();
		integrationTest.runExample();
	}

	private void runExample() {

		int plateauWidth = 5, plateauHeight = 5;

		// initialize plateau command
		UUID plateauUuid = UUID.randomUUID();
		InitializePlateauCommand initializePlateauCommand = new InitializePlateauCommand.Builder().withObserverSpeed(0)
				.withUuid(plateauUuid).withAbscissa(plateauWidth).withOrdinate(plateauHeight).build();

		// initialize Rover 1 command
		int x = 1, y = 2;
		String rover1Name = GameContext.ROVER_NAME_PREFIX + 1;
		InitializeRoverCommand intializeRoverCommand = new InitializeRoverCommand.Builder().withPlateauUuid(plateauUuid)
				.withName(rover1Name).withAbscissa(x).withOrdinate(y).withOrientation('N').build();

		// executes commands
		gameService.execute(initializePlateauCommand);

		gameService.execute(intializeRoverCommand);

		RoverIdentifier rover1 = new RoverIdentifier(plateauUuid, rover1Name);
		
		//Rover 1 LMLMLMLMM

		executeCommand(new MakeTurnRoverCommand(rover1, RoverInstruction.LEFT));
		executeCommand(new MoveRoverCommand(rover1, 1));
		
		executeCommand(new MakeTurnRoverCommand(rover1, RoverInstruction.LEFT));

		executeCommand(new MoveRoverCommand(rover1, 1));
		
		executeCommand(new MakeTurnRoverCommand(rover1, RoverInstruction.LEFT));

		executeCommand(new MoveRoverCommand(rover1, 1));
		
		executeCommand(new MakeTurnRoverCommand(rover1, RoverInstruction.LEFT));

		executeCommand(new MoveRoverCommand(rover1, 2));
			
		
		// Rover 2 initialization
		// initialize Rover command
		int x2 = 3, y2 = 3;
		String rover2Name = GameContext.ROVER_NAME_PREFIX + 2;
		InitializeRoverCommand intializeRover2Command = new InitializeRoverCommand.Builder().withPlateauUuid(plateauUuid)
				.withName(rover2Name).withAbscissa(x2).withOrdinate(y2).withOrientation('E').build();
		
		gameService.execute(intializeRover2Command);
				
		RoverIdentifier rover2 = new RoverIdentifier(plateauUuid, rover2Name);
		
		// executes commands
		// MMRMMRMRRM
		executeCommand(new MoveRoverCommand(rover2, 2));
		
		executeCommand(new MakeTurnRoverCommand(rover2, RoverInstruction.RIGHT));
		
		executeCommand(new MoveRoverCommand(rover2, 2));
		
		executeCommand(new MakeTurnRoverCommand(rover2, RoverInstruction.RIGHT));
		
		executeCommand(new MoveRoverCommand(rover2, 1));
		
		executeCommand(new MakeTurnRoverCommand(rover2, RoverInstruction.RIGHT));
		
		executeCommand(new MakeTurnRoverCommand(rover2, RoverInstruction.RIGHT));
		
		executeCommand(new MoveRoverCommand(rover2, 1));
		
		printInfos(plateauUuid);
		eventStore.getAllEvents().forEach(System.out::println);
		
	}
	
	private void executeCommand(MoveRoverCommand command) {
		gameService.execute(command);
	}
	
	private void executeCommand(MakeTurnRoverCommand command) {
		gameService.execute(command);
	}

	private void printInfos(UUID plateauUuid) {
		GameContext.getInstance().getRoverService().getAllRoversOnPlateau(plateauUuid).forEach(System.out::println);
		Plateau inMemoryPlateau = GameContext.getInstance().getPlateau(plateauUuid);
		System.out.println(String.format("In-memory Plateau with coordinates 1,3 busy ? [%s]", String.valueOf(inMemoryPlateau.isLocationBusy(new TwoDimensionalCoordinates(1, 3)))));
		System.out.println(String.format("In-memory Plateau with coordinates 5,2 busy ? [%s]", String.valueOf(inMemoryPlateau.isLocationBusy(new TwoDimensionalCoordinates(5, 1)))));
		System.out.println(String.format("Persisent Plateau with coordinates 1,3 busy ? [%s]", String.valueOf(GameContext.getInstance().getPlateauService().isLocationBusy(plateauUuid, new TwoDimensionalCoordinates(1, 3)))));
		System.out.println(String.format("Persisent Plateau with coordinates 5,2 busy ? [%s]", String.valueOf(GameContext.getInstance().getPlateauService().isLocationBusy(plateauUuid, new TwoDimensionalCoordinates(5, 1)))));
		
	}

}
