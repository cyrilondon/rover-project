package com.game.adapter.file;

import java.io.File;
import java.util.List;
import java.util.UUID;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.rover.Rover;

public class GameIntegration {
	

	public static void main(String[] args) {

		GameIntegration integrationTest = new GameIntegration();
		integrationTest.runWithFileAdapter();
	}

	private void runWithFileAdapter() {
		
		// the client will run only this part
		GameFileAdapter adapter = new GameFileAdapter();
		adapter.executeGame(new File("myFileNotNeededForNow"));
	
		// extra part just to print the results on the console
		printInfos();
	}
	
	private void printInfos() {
		
		UUID plateauId = GameContext.getInstance().getAllPlateau().get(0).getId();
		
		// prints all the rover persistent state from the application repository
		List<Rover> rovers = GameContext.getInstance().getRoverService().getAllRoversOnPlateau(plateauId);
		rovers.forEach(rover -> System.out.println("Persistent Rover: " + rover));
		
		// print the Plateau persistent state from the application repository
		rovers.forEach(rover -> System.out.println(String.format("Persistent Plateau [%s] occupied at coordinates [%s]? [%s]", plateauId, rover.getCoordinates(),
				String.valueOf(GameContext.getInstance().getPlateauService().isLocationBusy(plateauId,
						rover.getCoordinates())))));
		
		// print all the Events stored in the Event Store
		GameContext.getInstance().getEventStore().getAllEvents().forEach(System.out::println);
		
		System.out.println("***************** Using Read/Projected Model ********************");
		System.out.println("Number of rovers currently in play: " + GameContext.getInstance().getRoverService().getReadRoverRepository().getNumberOfEntities());
	}

}
