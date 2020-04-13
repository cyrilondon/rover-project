package com.game.integration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.game.domain.application.CommandVisitor;
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

import com.game.domain.application.command.DomainCommand;

public class GameIntegrationTest {

	GameService gameService = GameContext.getInstance().getGameService();

	EventStore eventStore = GameContext.getInstance().getEventStore();

	// random plateau UUID
	UUID plateauUuid = UUID.randomUUID();

	CommandVisitor visitor = new CommandVisitor();

	public static void main(String[] args) {

		GameIntegrationTest integrationTest = new GameIntegrationTest();
		integrationTest.runExample();
	}

	private void runExample() {

		// ********* Given **********
		// plateau command
		List<DomainCommand> commands = new ArrayList<>();
		commands.add(new InitializePlateauCommand.Builder().withObserverSpeed(0).withUuid(plateauUuid).withAbscissa(5)
				.withOrdinate(5).build());

		// rover1 commands
		// Rover 1 initialization + sLMLMLMLMMs
		String rover1Name = GameContext.ROVER_NAME_PREFIX + 1;
		commands.add(new InitializeRoverCommand.Builder().withPlateauUuid(plateauUuid).withName(rover1Name)
				.withAbscissa(1).withOrdinate(2).withOrientation('N').build());
		RoverIdentifier rover1 = new RoverIdentifier(plateauUuid, rover1Name);
		commands.add(new MakeTurnRoverCommand(rover1, RoverInstruction.LEFT));
		commands.add(new MoveRoverCommand(rover1, 1));
		commands.add(new MakeTurnRoverCommand(rover1, RoverInstruction.LEFT));
		commands.add(new MoveRoverCommand(rover1, 1));
		commands.add(new MakeTurnRoverCommand(rover1, RoverInstruction.LEFT));
		commands.add(new MoveRoverCommand(rover1, 1));
		commands.add(new MakeTurnRoverCommand(rover1, RoverInstruction.LEFT));
		commands.add(new MoveRoverCommand(rover1, 2));

		// rover2 commands
		// rover 2 initialization + MMRMMRMRRM
		String rover2Name = GameContext.ROVER_NAME_PREFIX + 2;
		commands.add(new InitializeRoverCommand.Builder().withPlateauUuid(plateauUuid).withName(rover2Name)
				.withAbscissa(3).withOrdinate(3).withOrientation('E').build());
		RoverIdentifier rover2 = new RoverIdentifier(plateauUuid, rover2Name);
		commands.add(new MoveRoverCommand(rover2, 2));
		commands.add(new MakeTurnRoverCommand(rover2, RoverInstruction.RIGHT));
		commands.add(new MoveRoverCommand(rover2, 2));
		commands.add(new MakeTurnRoverCommand(rover2, RoverInstruction.RIGHT));
		commands.add(new MoveRoverCommand(rover2, 1));
		commands.add(new MakeTurnRoverCommand(rover2, RoverInstruction.RIGHT));
		commands.add(new MakeTurnRoverCommand(rover2, RoverInstruction.RIGHT));
		commands.add(new MoveRoverCommand(rover2, 1));
		
		// ******** When **************
		// execute the commands
		commands.forEach(command -> command.acceptVisitor(visitor));
		

		// ******* Then **************
		// print the results
		printInfos(plateauUuid);
		eventStore.getAllEvents().forEach(System.out::println);

	}

	private void printInfos(UUID plateauUuid) {
		// prints all the Persistent rovers
		GameContext.getInstance().getRoverService().getAllRoversOnPlateau(plateauUuid).forEach(System.out::println);
		// print the Plateau in-memory state
		Plateau inMemoryPlateau = GameContext.getInstance().getPlateau(plateauUuid);
		System.out.println(String.format("In-memory Plateau with coordinates 1,3 busy ? [%s]",
				String.valueOf(inMemoryPlateau.isLocationBusy(new TwoDimensionalCoordinates(1, 3)))));
		System.out.println(String.format("In-memory Plateau with coordinates 5,2 busy ? [%s]",
				String.valueOf(inMemoryPlateau.isLocationBusy(new TwoDimensionalCoordinates(5, 1)))));
		// print the Plateau persistent state from the application repository
		System.out.println(String.format("Persisent Plateau with coordinates 1,3 busy ? [%s]",
				String.valueOf(GameContext.getInstance().getPlateauService().isLocationBusy(plateauUuid,
						new TwoDimensionalCoordinates(1, 3)))));
		System.out.println(String.format("Persisent Plateau with coordinates 5,2 busy ? [%s]",
				String.valueOf(GameContext.getInstance().getPlateauService().isLocationBusy(plateauUuid,
						new TwoDimensionalCoordinates(5, 1)))));
	}

}
