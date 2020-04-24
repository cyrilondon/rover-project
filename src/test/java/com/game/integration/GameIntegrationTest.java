package com.game.integration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.game.domain.application.command.ApplicationCommand;
import com.game.domain.application.command.plateau.PlateauInitializeCommand;
import com.game.domain.application.command.rover.RoverInitializeCommand;
import com.game.domain.application.command.rover.RoverMoveCommand;
import com.game.domain.application.command.rover.RoverTurnCommand;
import com.game.domain.application.context.GameContext;
import com.game.domain.application.service.GameService;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.entity.rover.RoverTurnInstruction;

public class GameIntegrationTest {
	
	GameService gameService = GameContext.getInstance().getGameService();

	public static void main(String[] args) {	
		
		GameIntegrationTest integrationTest = new GameIntegrationTest();
		
		try {
			//integrationTest.simulateRoversCollision();
			//integrationTest.simulateRoverInitialization();
			integrationTest.simulateRoverMovesOutPlateau();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		integrationTest.printInfos();
		
	}

	//com.game.domain.model.exception.GameException: [ERR-004] There is already a Rover at position X = [0] and Y = [2]
	private void simulateRoversCollision() {
		
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
		
		String rover2Name = GameContext.ROVER_NAME_PREFIX + 2;
		commands.add(new RoverInitializeCommand.Builder().withPlateauUuid(plateauId).withName(rover2Name)
				.withAbscissa(1).withOrdinate(2).withOrientation('N').build());
		RoverIdentifier rover2 = new RoverIdentifier(plateauId, rover2Name);
		// Rover 1 move commands  LMLMLMLMMs
		commands.add(new RoverTurnCommand(rover2, RoverTurnInstruction.LEFT));
		commands.add(new RoverMoveCommand(rover2, 1));
		
		gameService.execute(commands);
	
	}
	
	    //com.game.domain.model.exception.GameException: [ERR-004] Rover with Y-position [3] is out of the Plateau with height [2]
		private void simulateRoverMovesOutPlateau() {
			
			UUID plateauId = UUID.randomUUID();
			// ********* Given **********
			// initialization plateau command (2,2)
			List<ApplicationCommand> commands = new ArrayList<>();
			commands.add(new PlateauInitializeCommand.Builder().withObserverSpeed(0).withId(plateauId).withAbscissa(2)
					.withOrdinate(2).build());

			// rover1 commands
			// Rover 1 initialization (1,2) and Orientation 'N' 
			String rover1Name = GameContext.ROVER_NAME_PREFIX + 1;
			commands.add(new RoverInitializeCommand.Builder().withPlateauUuid(plateauId).withName(rover1Name)
					.withAbscissa(1).withOrdinate(1).withOrientation('N').build());
			RoverIdentifier rover1 = new RoverIdentifier(plateauId, rover1Name);
			// Rover 1 move commands  MMMs
			commands.add(new RoverMoveCommand(rover1, 2));
			
			gameService.execute(commands);
		
		}
	
	//com.game.domain.model.exception.IllegalArgumentGameException: [ERR-000] Broken precondition: Missing Plateau identifiant
	private void simulateRoverInitialization() {
		List<ApplicationCommand> commands = new ArrayList<>();
		commands.add(new RoverInitializeCommand.Builder().withName("ROVER_TEST")
				.withAbscissa(1).withOrdinate(2).withOrientation('N').build());
		gameService.execute(commands);
	}
	
	private void printInfos() {
		// extra part just to print the results on the console
		UUID plateauId = GameContext.getInstance().getAllPlateau().get(0).getId();
		// prints all the Persistent rovers
		GameContext.getInstance().getRoverService().getAllRoversOnPlateau(plateauId).forEach(rover -> System.out.println("Persistent Rover: " + rover));
	
		GameContext.getInstance().getEventStore().getAllEvents().forEach(System.out::println);
	}

}
