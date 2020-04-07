package com.game.infrastructure.persistence.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.repository.RoverRepository;

public class InMemoryRoverRepositoryImplTest {
	
	private static final int X = 3;

	private static final int Y = 4;
	
	private static final String ROVER_PREFIX = "ROVER_TEST";
	
	private RoverRepository roverRepository = new InMemoryRoverRepositoryImpl();
	
	@BeforeMethod
	public void reset() {
		roverRepository.removeAllRovers();
	}
	
	@Test
	public void testAdd() {
		Rover rover = getRover(ROVER_PREFIX);
		roverRepository.add(rover);
		Rover roverFromRepo = roverRepository.load(ROVER_PREFIX);
		assertThat(roverFromRepo).isNotNull();
		assertThat(roverFromRepo.getName()).isEqualTo(ROVER_PREFIX);
		assertThat(roverFromRepo.getXPosition()).isEqualTo(X);
		assertThat(roverFromRepo.getYPosition()).isEqualTo(Y);
		assertThat(roverFromRepo.getOrientation()).isEqualTo(Orientation.SOUTH);
		assertThat(roverRepository.getNumberOfRovers()).isEqualTo(1);
	}
	
	@Test
	public void testRemove() {
		Rover rover = getRover(ROVER_PREFIX);
		roverRepository.add(rover);
		Rover roverFromRepo = roverRepository.load(ROVER_PREFIX);
		assertThat(roverFromRepo).isNotNull();
		assertThat(roverRepository.getNumberOfRovers()).isEqualTo(1);
		roverRepository.remove(ROVER_PREFIX);
		Rover roverFromRepoRemoved = roverRepository.load(ROVER_PREFIX);
		assertThat(roverFromRepoRemoved).isNull();
		assertThat(roverRepository.getNumberOfRovers()).isEqualTo(0);
	}
	
	@Test
	public void testRemoveAll() {
		Rover rover1 = getRover(ROVER_PREFIX+1);
		roverRepository.add(rover1);
		Rover rover2 = getRover(ROVER_PREFIX+2);
		roverRepository.add(rover2);
		assertThat(roverRepository.getNumberOfRovers()).isEqualTo(2);
		roverRepository.removeAllRovers();
		assertThat(roverRepository.getNumberOfRovers()).isEqualTo(0);
	}
	
	private Rover getRover(String name) {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);
		return new Rover(name, coordinates, Orientation.SOUTH);
	}

}
