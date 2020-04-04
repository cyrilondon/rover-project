package com.game.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.game.domain.model.entity.Board;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;

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
		assertThat(gameContext.getBoard()).isNull();
	}
	
	@Test
	public void testGetGameInstanceWithStep() {
		GameContext gameContextWithStep = GameContext.getInstance(5);
		assertThat(gameContextWithStep.getRoverStepLength()).isEqualTo(5);
	}
	
	@Test
	public void testAddBoard() {
		gameContext.addBoard(getBoard());
		assertThat(gameContext.getBoard().getWidth()).isEqualTo(WIDTH);
		assertThat(gameContext.getBoard().getHeight()).isEqualTo(HEIGHT);
	}

	@Test
	public void testReset() {
		gameContext.addBoard(getBoard());
		gameContext.reset();
		assertThat(gameContext.isInitialized()).isFalse();
		assertThat(gameContext.getBoard()).isNull();
		assertThat(gameContext.getRoverStepLength()).isEqualTo(GameContext.ROVER_STEP_LENGTH);
	}

	private Board getBoard() {
		TwoDimensions dimensions = new TwoDimensions(new TwoDimensionalCoordinates(3, 3));
		return new Board(dimensions);
	}

}
