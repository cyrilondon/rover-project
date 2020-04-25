package com.game.integration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.game.domain.application.command.ApplicationCommand;
import com.game.domain.application.command.plateau.PlateauInitializeCommand;
import com.game.domain.application.command.rover.RoverInitializeCommand;
import com.game.domain.application.command.rover.RoverMoveCommand;
import com.game.domain.application.command.rover.RoverTurnCommand;
import com.game.domain.application.context.GameContext;
import com.game.domain.application.service.GameService;
import com.game.domain.model.entity.rover.Rover;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.entity.rover.RoverTurnInstruction;
import com.game.domain.model.event.DomainEventPublisherSubscriber;
import com.game.domain.model.event.subscriber.rover.RoverTurnedEventSubscriber;
import com.game.domain.model.service.plateau.PlateauService;
import com.game.domain.model.service.rover.RoverService;

public class GameIntegrationTest {
	
	GameService gameService = GameContext.getInstance().getGameService();
	
	ExecutorService executorService = Executors.newFixedThreadPool(5);

	public static void main(String[] args) {	
		
		GameIntegrationTest integrationTest = new GameIntegrationTest();
		// uncomment one of those lines to see the exception stacktrace
		try {
			//integrationTest.simulateRoversCollision();
			//integrationTest.simulateRoverInitialization();
			//integrationTest.simulateRoverMovesOutPlateau();
			integrationTest.simulateConcurrentCommands();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		printInfos();
		
	}

	//com.game.domain.model.exception.GameException: RoverMovedWithExceptionEvent published at [2020-04-24T23:40:02.779] with Rover Moved Event [RoverMovedEvent published at
	//[2020-04-24T23:40:02.775] with rover id [Name [ROVER_2] - Plateau UUID [849ef7ce-a071-466b-9475-20b9aa3ab48f]],
	//previous position [Coordinates [abscissa = 1, ordinate = 2]], current position [Coordinates [abscissa = 0, ordinate = 2]]]
	//exception [com.game.domain.model.exception.IllegalRoverMoveException: [ERR-004] There is already a Rover at position X = [0] and Y = [2]]
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
		// Rover 1 Rover 2 commands  LM
		commands.add(new RoverTurnCommand(rover1, RoverTurnInstruction.LEFT));
		commands.add(new RoverMoveCommand(rover1, 1));
		
		String rover2Name = GameContext.ROVER_NAME_PREFIX + 2;
		commands.add(new RoverInitializeCommand.Builder().withPlateauUuid(plateauId).withName(rover2Name)
				.withAbscissa(1).withOrdinate(2).withOrientation('N').build());
		RoverIdentifier rover2 = new RoverIdentifier(plateauId, rover2Name);
		// Rover 2 commands  LM
		commands.add(new RoverTurnCommand(rover2, RoverTurnInstruction.LEFT));
		commands.add(new RoverMoveCommand(rover2, 1));
		
		gameService.execute(commands);
	
	}
	
	    //com.game.domain.model.exception.GameException: RoverMovedWithExceptionEvent published at [2020-04-24T23:37:21.268] with Rover Moved Event [RoverMovedEvent published at [2020-04-24T23:37:21.265] with rover id [Name [ROVER_1] - 
	    //Plateau UUID [84a3050e-36ce-4420-893b-866f159a3f36]], previous position [Coordinates [abscissa = 1, ordinate = 2]], current position [Coordinates [abscissa = 1, ordinate = 3]]], 
	    //exception [com.game.domain.model.exception.IllegalRoverMoveException: [ERR-004] Rover with Y-position [3] is out of the Plateau with height [2]]
		private void simulateRoverMovesOutPlateau() {
			
			UUID plateauId = UUID.randomUUID();
			// ********* Given **********
			// initialization plateau command (2,2)
			List<ApplicationCommand> commands = new ArrayList<>();
			commands.add(new PlateauInitializeCommand.Builder().withObserverSpeed(0).withId(plateauId).withAbscissa(2)
					.withOrdinate(2).build());

			// rover1 commands
			// Rover 1 initialization (1,2) and Orientation 'N' 
			String rover1Name = GameContext.ROVER_NAME_PREFIX;
			commands.add(new RoverInitializeCommand.Builder().withPlateauUuid(plateauId).withName(rover1Name)
					.withAbscissa(1).withOrdinate(1).withOrientation('N').build());
			RoverIdentifier rover1 = new RoverIdentifier(plateauId, rover1Name);
			// Rover 1 commands  MM
			commands.add(new RoverMoveCommand(rover1, 2));
			
			gameService.execute(commands);
		
		}
	
	//com.game.domain.model.exception.IllegalArgumentGameException: [ERR-000] Broken precondition: Missing Plateau identifiant
	private void simulateRoverInitialization() {
		ApplicationCommand command = new RoverInitializeCommand.Builder().withName(GameContext.ROVER_NAME_PREFIX)
				.withAbscissa(1).withOrdinate(2).withOrientation('N').build();
		gameService.execute(command);
	}
	
	
	
	private void simulateConcurrentCommands() {
		
		RoverService roverService = GameContext.getInstance().getRoverService();
		PlateauService plateauService = GameContext.getInstance().getPlateauService();
		
		
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
		RoverIdentifier roverId1 = new RoverIdentifier(plateauId, rover1Name);
		
		gameService.execute(commands);
		
		
		Runnable runnableTask = () -> {
			Rover rover = roverService.getRover(roverId1);
			// copy to a new Rover with same version to prevent the update from in memory datastore by the main updates
			Rover roverNew = new Rover(rover.getId(), rover.getCoordinates(), rover.getOrientation());
			roverNew.setVersion(rover.getVersion());
			// register the subscriber for the given type of event = RoverMovedEvent as we dont go through GameService
			DomainEventPublisherSubscriber.instance().subscribe(new RoverTurnedEventSubscriber());
		    try {
		    	GameContext.getInstance().addPlateau(plateauService.loadPlateau(roverId1.getPlateauId()));
		        TimeUnit.MILLISECONDS.sleep(1000);
		        roverNew.turnLeft();
		        roverNew.move();
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		    GameIntegrationTest.printInfos();
		};
		executorService.execute(runnableTask);
		
		gameService.execute(new RoverMoveCommand(roverId1, 2));
		gameService.execute(new RoverTurnCommand(roverId1, RoverTurnInstruction.RIGHT));
	
	}
	
	
	private static void printInfos() {
		// extra part just to print the results on the console
		UUID plateauId = GameContext.getInstance().getAllPlateau().get(0).getId();
		// prints all the Persistent rovers
		GameContext.getInstance().getRoverService().getAllRoversOnPlateau(plateauId).forEach(rover -> System.out.println("Persistent Rover: " + rover));
	
		GameContext.getInstance().getEventStore().getAllEvents().forEach(System.out::println);
	}

}
