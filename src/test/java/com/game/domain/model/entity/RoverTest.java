package com.game.domain.model.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.testng.annotations.Test;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.IllegalArgumentGameException;

public class RoverTest {

	private static final String ROVER_NAME = "ROVER_TEST";

	@Test
	public void testConstructorWithName() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(3, 4);
		Rover rover = new Rover(ROVER_NAME, coordinates, Orientation.SOUTH);
		assertThat(rover.getCoordinates()).isEqualTo(new TwoDimensionalCoordinates(3, 4));
		assertThat(rover.getOrientation()).isEqualTo(Orientation.SOUTH);
		assertThat(rover.getName()).isEqualTo(ROVER_NAME);
	}

	@Test
	public void testGetPosition() {
		Rover rover = initializeRover(Orientation.SOUTH);
		assertThat(rover.getXPosition()).isEqualTo(3);
		assertThat(rover.getYPosition()).isEqualTo(4);
	}

	@Test
	public void testTurnLeft() {
		Rover rover = initializeRover(Orientation.SOUTH);
		rover.turnLeft();
		assertThat(rover.getOrientation()).isEqualTo(Orientation.EAST);
	}

	@Test
	public void testTurnRight() {
		Rover rover = initializeRover(Orientation.SOUTH);
		rover.turnRight();
		assertThat(rover.getOrientation()).isEqualTo(Orientation.WEST);
	}

	@Test
	public void testMoveNorth() {
		Rover rover = initializeRover(Orientation.NORTH);
		rover.move();
		assertThat(rover.getOrientation()).isEqualTo(Orientation.NORTH);
		assertThat(rover.getXPosition()).isEqualTo(3);
		assertThat(rover.getYPosition()).isEqualTo(5);
	}

	@Test
	public void testMoveWest() {
		Rover rover = initializeRover(Orientation.WEST);
		rover.move();
		assertThat(rover.getOrientation()).isEqualTo(Orientation.WEST);
		assertThat(rover.getXPosition()).isEqualTo(2);
		assertThat(rover.getYPosition()).isEqualTo(4);
	}

	@Test
	public void testMoveSouth() {
		Rover rover = initializeRover(Orientation.SOUTH);
		rover.move();
		assertThat(rover.getOrientation()).isEqualTo(Orientation.SOUTH);
		assertThat(rover.getXPosition()).isEqualTo(3);
		assertThat(rover.getYPosition()).isEqualTo(3);
	}

	@Test
	public void testMoveEast() {
		Rover rover = initializeRover(Orientation.EAST);
		rover.move();
		assertThat(rover.getOrientation()).isEqualTo(Orientation.EAST);
		assertThat(rover.getXPosition()).isEqualTo(4);
		assertThat(rover.getYPosition()).isEqualTo(4);
	}

	@Test
	public void testEqualsWithDifferentInstance() {
		Rover rover = initializeDefaultRover();
		assertThat(rover).isNotEqualTo(new TwoDimensionalCoordinates(3, 4));
	}

	@Test
	public void testEqualsWithSameInstance() {
		Rover rover = initializeDefaultRover();
		assertThat(rover).isEqualTo(rover);
	}

	@Test
	public void testEqualsWithName() {
		Rover rover = initializeDefaultRover();
		Rover otherrover = initializeDefaultRover();
		assertThat(rover).isEqualTo(otherrover);
	}

	@Test
	public void testHashCodeWithName() {
		Rover rover = initializeDefaultRover();
		Rover otherrover = initializeDefaultRover();
		assertThat(rover.hashCode()).isEqualTo(otherrover.hashCode());
	}

	@Test
	public void testToString() {
		Rover rover = new Rover(ROVER_NAME, new TwoDimensionalCoordinates(3, 4), Orientation.SOUTH);
		assertThat(rover.toString()).isEqualTo(
				"Rover [ROVER_TEST] with [Coordinates [abscissa = 3, ordinate = 4]] and [Orientation [SOUTH]]");
	}

	@Test
	public void testWithNullPosition() {
		Throwable thrown = catchThrowable(() -> new Rover(ROVER_NAME, null, Orientation.SOUTH));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
						String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE,
								GameExceptionLabels.MISSING_ROVER_POSITION)));
	}

	@Test
	public void testWithNullOrientation() {
		Throwable thrown = catchThrowable(() -> new Rover(ROVER_NAME, new TwoDimensionalCoordinates(3, 4), null));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
		.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
				GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
				String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE,
						GameExceptionLabels.MISSING_ROVER_ORIENTATION)));
	}
	

	@Test
	public void testWithNullName() {
		Throwable thrown = catchThrowable(() -> new Rover(null, new TwoDimensionalCoordinates(3, 4), Orientation.EAST));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
		.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
				GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
				String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE,
						GameExceptionLabels.MISSING_ROVER_NAME)));
	}

	private Rover initializeDefaultRover() {
		return new Rover(ROVER_NAME, new TwoDimensionalCoordinates(3, 4), Orientation.SOUTH);
	}

	private Rover initializeRover(Orientation orientation) {
		return new Rover(ROVER_NAME, new TwoDimensionalCoordinates(3, 4), orientation);
	}

}