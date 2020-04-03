package com.game.domain.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.game.domain.model.GameContext;
import com.game.domain.model.entity.Board;
import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;

public class GameServiceImplTest {

	private static final int X = 3;

	private static final int Y = 4;

	private GameContext gameContext = GameContext.getInstance();

	private GameService gameService = new GameServiceImpl();

	@BeforeMethod
	public void resetGame() {
		gameContext.reset();
	}

	@Test
	public void testInitializeRover() {
		gameContext.addBoard(getBoard());
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);
		Rover rover = gameService.initializeRover(coordinates, Orientation.SOUTH);
		assertThat(rover.getCoordinates()).isEqualTo(new TwoDimensionalCoordinates(X, Y));
		assertThat(rover.getOrientation()).isEqualTo(Orientation.SOUTH);
		assertThat(rover.getName()).isEqualTo(GameContext.ROVER_NAME_PREFIX + 1);
		Rover otherRover = gameService.initializeRover(coordinates, Orientation.EAST);
		assertThat(otherRover.getName()).isEqualTo(GameContext.ROVER_NAME_PREFIX + 2);
	}

	private Board getBoard() {
		TwoDimensions dimensions = new TwoDimensions(new TwoDimensionalCoordinates(5, 5));
		return new Board(dimensions);
	}

}
