package com.game.integration;

import java.io.File;
import java.util.UUID;

import com.game.adapter.file.GameFileAdapter;
import com.game.domain.application.GameContext;
import com.game.domain.model.entity.Plateau;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

public class GameIntegrationTest {
	

	public static void main(String[] args) {

		GameIntegrationTest integrationTest = new GameIntegrationTest();
		integrationTest.runWithFileAdapter();
	}

	private void runWithFileAdapter() {
		
		// the client will run only this part
		GameFileAdapter adapter = new GameFileAdapter();
		adapter.executeGame(new File("C:/toto"));

		// extra part just to print the results on the console
		UUID plateauId = GameContext.getInstance().getAllPlateau().get(0).getId();
		printInfos(plateauId);
		GameContext.getInstance().getEventStore().getAllEvents().forEach(System.out::println);
	
	}
	
	private void printInfos(UUID plateauId) {
		// prints all the Persistent rovers
		GameContext.getInstance().getRoverService().getAllRoversOnPlateau(plateauId).forEach(rover -> System.out.println("Persistent Rover: " + rover));
		// print the Plateau in-memory state
		Plateau inMemoryPlateau = GameContext.getInstance().getPlateau(plateauId);
		System.out.println(String.format("In-Memory Plateau with coordinates 1,3 busy ? [%s]",
				String.valueOf(inMemoryPlateau.isLocationBusy(new TwoDimensionalCoordinates(1, 3)))));
		System.out.println(String.format("In-Memory Plateau with coordinates 5,2 busy ? [%s]",
				String.valueOf(inMemoryPlateau.isLocationBusy(new TwoDimensionalCoordinates(5, 1)))));
		// print the Plateau persistent state from the application repository
		System.out.println(String.format("Persistent Plateau with coordinates 1,3 busy ? [%s]",
				String.valueOf(GameContext.getInstance().getPlateauService().isLocationBusy(plateauId,
						new TwoDimensionalCoordinates(1, 3)))));
		System.out.println(String.format("Persistent Plateau with coordinates 5,2 busy ? [%s]",
				String.valueOf(GameContext.getInstance().getPlateauService().isLocationBusy(plateauId,
						new TwoDimensionalCoordinates(5, 1)))));
	}

}
