package com.game.domain.model.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.testng.annotations.Test;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.IllegalArgumentGameException;

public class RobotTest {

	private static final String ROBOT_NAME = "Robot_test";

	@Test
	public void testConstructorWithName() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(3, 4);
		Robot robot = new Robot(ROBOT_NAME, coordinates, Orientation.SOUTH);
		assertThat(robot.getCoordinates()).isEqualTo(new TwoDimensionalCoordinates(3, 4));
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
	public void testMoveEast() {
		Robot robot = initializeRobot(Orientation.EAST);
		robot.move();
		assertThat(robot.getOrientation()).isEqualTo(Orientation.EAST);
		assertThat(robot.getXPosition()).isEqualTo(4);
		assertThat(robot.getYPosition()).isEqualTo(4);
	}

	@Test
	public void testEqualsWithDifferentInstance() {
		Robot robot = initializeDefaultRobot();
		assertThat(robot).isNotEqualTo(new TwoDimensionalCoordinates(3, 4));
	}

	@Test
	public void testEqualsWithSameInstance() {
		Robot robot = initializeDefaultRobot();
		assertThat(robot).isEqualTo(robot);
	}

	@Test
	public void testEqualsWithName() {
		Robot robot = initializeDefaultRobot();
		Robot otherRobot = initializeDefaultRobot();
		assertThat(robot).isEqualTo(otherRobot);
	}

	@Test
	public void testHashCodeWithName() {
		Robot robot = initializeDefaultRobot();
		Robot otherRobot = initializeDefaultRobot();
		assertThat(robot.hashCode()).isEqualTo(otherRobot.hashCode());
	}

	@Test
	public void testToString() {
		Robot robot = new Robot(ROBOT_NAME, new TwoDimensionalCoordinates(3, 4), Orientation.SOUTH);
		assertThat(robot.toString()).isEqualTo(
				"Robot [Robot_test] with [Coordinates [abscissa = 3, ordinate = 4]] and [Orientation [SOUTH]]");
	}

	@Test
	public void testRobotWithNullPosition() {
		Throwable thrown = catchThrowable(() -> new Robot(ROBOT_NAME, null, Orientation.SOUTH));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
						String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE,
								GameExceptionLabels.MISSING_ROBOT_POSITION)));
	}

	@Test
	public void testRobotWithNullOrientation() {
		Throwable thrown = catchThrowable(() -> new Robot(ROBOT_NAME, new TwoDimensionalCoordinates(3, 4), null));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
		.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
				GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
				String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE,
						GameExceptionLabels.MISSING_ROBOT_ORIENTATION)));
	}
	

	@Test
	public void testRobotWithNullName() {
		Throwable thrown = catchThrowable(() -> new Robot(null, new TwoDimensionalCoordinates(3, 4), Orientation.EAST));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
		.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
				GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
				String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE,
						GameExceptionLabels.MISSING_ROBOT_NAME)));
	}

	private Robot initializeDefaultRobot() {
		return new Robot(ROBOT_NAME, new TwoDimensionalCoordinates(3, 4), Orientation.SOUTH);
	}

	private Robot initializeRobot(Orientation orientation) {
		return new Robot(ROBOT_NAME, new TwoDimensionalCoordinates(3, 4), orientation);
	}

}
