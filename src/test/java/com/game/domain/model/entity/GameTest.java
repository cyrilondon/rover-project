package com.game.domain.model.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensionalSpace;

public class GameTest {

	@BeforeMethod
	public void resetGame() {
		Game.getInstance().reset();
	}

	@Test
	public void testGetGameInstance() {
		Game game = Game.getInstance();
		assertThat(game.getBoard()).isNotNull();
		assertThat(game.getBoard().getWidth()).isEqualTo(TwoDimensionalSpace.DEFAULT_WIDTH);
		assertThat(game.getBoard().getHeight()).isEqualTo(TwoDimensionalSpace.DEFAULT_HEIGHT);
		assertThat(game.getNumberOfRobots()).isEqualTo(0);
	}

	@Test
	public void testGetSameGameInstance() {
		Game game = Game.getInstance();
		Game otherGame = Game.getInstance();
		assertThat(game).isSameAs(otherGame);
	}

	@Test
	public void testAddRobots() {
		Game game = Game.getInstance();
		game.addRobot(getRobot());
		assertThat(game.getRobot(Game.ROBOT_NAME_PREFIX + 1)).isNotNull();
		game.addRobot(getRobot());
		assertThat(game.getRobot(Game.ROBOT_NAME_PREFIX + 2)).isNotNull();
		assertThat(Game.getInstance().getNumberOfRobots()).isEqualTo(2);
	}
	
	@Test
	public void testRemoveRobots() {
		Game game = Game.getInstance();
		game.addRobot(getRobot());
		assertThat(game.getRobot(Game.ROBOT_NAME_PREFIX + 1)).isNotNull();
		game.removeRobot(Game.ROBOT_NAME_PREFIX + 1);
		assertThat(game.getNumberOfRobots()).isEqualTo(0);
	}
	
	@Test
	public void testReset() {
		Game game = Game.getInstance();
		game.addRobot(getRobot());
		game.addRobot(getRobot());
		game.reset();
		assertThat(game.getNumberOfRobots()).isEqualTo(0);
		game.addRobot(getRobot());
		assertThat(game.getRobot(Game.ROBOT_NAME_PREFIX + 1)).isNotNull();
	}


	private Robot getRobot() {
		return new Robot(new TwoDimensionalCoordinates(3, 4), Orientation.SOUTH);
	}

}
