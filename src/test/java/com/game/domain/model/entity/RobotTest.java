package com.game.domain.model.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

public class RobotTest {
	
	private static final String ROBOT_NAME = "Robot_test";	
	
	@Test
	public void testConstructor() {
		Coordinates coordinates = new Coordinates(3, 4);
		Robot robot = new Robot(coordinates, Orientation.SOUTH);
		assertThat(robot.getCoordinates()).isEqualTo(coordinates);
		assertThat(robot.getOrientation()).isEqualTo(Orientation.SOUTH);
		assertThat(robot.getName()).isNullOrEmpty();
	}
	
	@Test
	public void testConstructorWithName() {
		Coordinates coordinates = new Coordinates(3, 4);
		Robot robot = new Robot(ROBOT_NAME, coordinates, Orientation.SOUTH);
		assertThat(robot.getCoordinates()).isEqualTo(new Coordinates(3, 4));
		assertThat(robot.getOrientation()).isEqualTo(Orientation.SOUTH);
		assertThat(robot.getName()).isEqualTo(ROBOT_NAME);
	}
	
	@Test
	public void testGetPosition() {
		Robot robot = initializeRobot(Orientation.SOUTH);
		assertThat(robot.getXPosition()).isEqualTo(3);
		assertThat(robot.getYPosition()).isEqualTo(4);
	}
	
	@Test
	public void testTurnLeft() {
		Robot robot = initializeRobot(Orientation.SOUTH);
		robot.turnLeft();
		assertThat(robot.getOrientation()).isEqualTo(Orientation.EAST);
	}
	
	@Test
	public void testTurnRight() {
		Robot robot = initializeRobot(Orientation.SOUTH);
		robot.turnRight();
		assertThat(robot.getOrientation()).isEqualTo(Orientation.WEST);
	}
	
	@Test
	public void testMoveNorth() {
		Robot robot = initializeRobot(Orientation.NORTH);
		robot.move();
		assertThat(robot.getOrientation()).isEqualTo(Orientation.NORTH);
		assertThat(robot.getXPosition()).isEqualTo(3);
		assertThat(robot.getYPosition()).isEqualTo(5);
	}
	
	@Test
	public void testMoveWest() {
		Robot robot = initializeRobot(Orientation.WEST);
		robot.move();
		assertThat(robot.getOrientation()).isEqualTo(Orientation.WEST);
		assertThat(robot.getXPosition()).isEqualTo(2);
		assertThat(robot.getYPosition()).isEqualTo(4);
	}
	
	@Test
	public void testMoveSouth() {
		Robot robot = initializeRobot(Orientation.SOUTH);
		robot.move();
		assertThat(robot.getOrientation()).isEqualTo(Orientation.SOUTH);
		assertThat(robot.getXPosition()).isEqualTo(3);
		assertThat(robot.getYPosition()).isEqualTo(3);
	}
	
	@Test
	public void testEqualsWithName() {
		Coordinates coordinates = new Coordinates(3, 4);
		Robot robot = new Robot(ROBOT_NAME, coordinates, Orientation.SOUTH);
		Coordinates otherCoordinates = new Coordinates(3, 4);
		Robot otherRobot = new Robot(ROBOT_NAME, otherCoordinates, Orientation.SOUTH);;
		assertThat(robot).isEqualTo(otherRobot);
	}
	
	@Test
	public void testEqualsWithoutName() {
		Coordinates coordinates = new Coordinates(3, 4);
		Robot robot = new Robot(coordinates, Orientation.SOUTH);
		Coordinates otherCoordinates = new Coordinates(3, 4);
		Robot otherRobot = new Robot(otherCoordinates, Orientation.SOUTH);;
		assertThat(robot).isEqualTo(otherRobot);
	}
	
	@Test
	public void testHashCodeWithName() {
		Coordinates coordinates = new Coordinates(3, 4);
		Robot robot = new Robot(ROBOT_NAME, coordinates, Orientation.SOUTH);
		Coordinates otherCoordinates = new Coordinates(3, 4);
		Robot otherRobot = new Robot(ROBOT_NAME, otherCoordinates, Orientation.SOUTH);;
		assertThat(robot.hashCode()).isEqualTo(otherRobot.hashCode());
	}
	
	@Test
	public void testHashCodeWithoutName() {
		Coordinates coordinates = new Coordinates(3, 4);
		Robot robot = new Robot(coordinates, Orientation.SOUTH);
		Coordinates otherCoordinates = new Coordinates(3, 4);
		Robot otherRobot = new Robot(otherCoordinates, Orientation.SOUTH);;
		assertThat(robot.hashCode()).isEqualTo(otherRobot.hashCode());
	}
	
	@Test
	public void testToString() {
		Coordinates coordinates = new Coordinates(3, 4);
		Robot robot = new Robot(ROBOT_NAME, coordinates, Orientation.SOUTH);
		assertThat(robot.toString()).isEqualTo("Robot [Robot_test] with [Coordinates [abscissa = 3, ordinate = 4]] and [Orientation [SOUTH]]");
	}
	
	private Robot initializeRobot(Orientation orientation) {
		Coordinates coordinates = new Coordinates(3, 4);
		return new Robot(coordinates, orientation);
	}

}
