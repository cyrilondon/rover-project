package com.game.domain.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

public class RoverServiceTest {
	
	private static final int X = 3;
	
	private static final int Y = 4;
	
	private RoverServiceImpl roverService = new RoverServiceImpl();

	@Test
	public void testInitializeRover() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);
		Rover rover = roverService.initializeRover(coordinates, Orientation.SOUTH);
		assertThat(rover.getCoordinates()).isEqualTo(new TwoDimensionalCoordinates(3, 4));
		assertThat(rover.getOrientation()).isEqualTo(Orientation.SOUTH);
		assertThat(rover.getName()).isNull();
	}
	
	@Test
	public void testMoveRoverwithOrientation() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);
		Rover rover = new Rover(coordinates, Orientation.SOUTH);
		roverService.moveRoverwithOrientation(rover, Orientation.EAST);
		assertThat(rover.getCoordinates()).isEqualTo(new TwoDimensionalCoordinates(X+1, Y));
		assertThat(rover.getOrientation()).isEqualTo(Orientation.EAST);
	}

}
