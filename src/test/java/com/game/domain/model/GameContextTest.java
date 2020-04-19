package com.game.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.game.domain.application.GameContext;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.entity.plateau.Plateau;

public class GameContextTest {

	private GameContext gameContext = GameContext.getInstance();

	private static final int WIDTH = 3;

	private static final int HEIGHT = 3;

	@BeforeMethod
	public void resetGame() {
		gameContext.reset();
	}

	@Test
	public void testGetGameInstance() {
		assertThat(gameContext.getPlateau(UUID.randomUUID())).isNull();
	}
	
	@Test
	public void testGetGameInstanceWithStep() {
		GameContext gameContextWithStep = GameContext.getInstance(5);
		assertThat(gameContextWithStep.getRoverStepLength()).isEqualTo(5);
	}
	
	@Test
	public void testAddPlateau() {
		UUID uuid = UUID.randomUUID();
		gameContext.addPlateau(newPlateau(uuid));
		assertThat(gameContext.getPlateau(uuid).getWidth()).isEqualTo(WIDTH);
		assertThat(gameContext.getPlateau(uuid).getHeight()).isEqualTo(HEIGHT);
	}

	@Test
	public void testReset() {
		UUID uuid = UUID.randomUUID();
		gameContext.addPlateau(newPlateau(uuid));
		gameContext.reset();
		assertThat(gameContext.getPlateau(uuid)).isNull();
		assertThat(gameContext.getRoverStepLength()).isEqualTo(GameContext.ROVER_STEP_LENGTH);
	}

	private Plateau newPlateau(UUID uuid) {
		TwoDimensions dimensions = new TwoDimensions(new TwoDimensionalCoordinates(3, 3));
		return new Plateau(uuid, dimensions);
	}

}
