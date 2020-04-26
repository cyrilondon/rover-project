package com.game.adapter.file;

import java.io.File;
import java.util.UUID;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

public class GameIntegration {
	

	public static void main(String[] args) {

		GameIntegration integrationTest = new GameIntegration();
		integrationTest.runWithFileAdapter();
	}

	private void runWithFileAdapter() {
		
		// the client will run only this part
		GameFileAdapter adapter = new GameFileAdapter();
		adapter.executeGame(new File("C:/toto"));
	
		printInfos();
	}
	
	private void printInfos() {
		// extra part just to print the results on the console
		UUID plateauId = GameContext.getInstance().getAllPlateau().get(0).getId();
		// prints all the Persistent rovers
		GameContext.getInstance().getRoverService().getAllRoversOnPlateau(plateauId).forEach(rover -> System.out.println("Persistent Rover: " + rover));
		// print the Plateau persistent state from the application repository
		System.out.println(String.format("Persistent Plateau with coordinates 1,3 busy ? [%s]",
				String.valueOf(GameContext.getInstance().getPlateauService().isLocationBusy(plateauId,
						new TwoDimensionalCoordinates(1, 3)))));
		System.out.println(String.format("Persistent Plateau with coordinates 5,1 busy ? [%s]",
				String.valueOf(GameContext.getInstance().getPlateauService().isLocationBusy(plateauId,
						new TwoDimensionalCoordinates(5, 1)))));
		GameContext.getInstance().getEventStore().getAllEvents().forEach(System.out::println);
	}

}
