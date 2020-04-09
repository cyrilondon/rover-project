package com.game.infrastructure.persistence.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.Rover;
import com.game.domain.model.entity.RoverIdentifier;
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
		UUID plateauUuid = UUID.randomUUID();
		RoverIdentifier roverId = new RoverIdentifier(plateauUuid, ROVER_PREFIX);
		Rover rover = getRover(roverId);
		roverRepository.add(rover);
		Rover roverFromRepo = roverRepository.load(roverId);
		assertThat(roverFromRepo).isNotNull();
		assertThat(roverFromRepo.getId().getName()).isEqualTo(ROVER_PREFIX);
		assertThat(roverFromRepo.getXPosition()).isEqualTo(X);
		assertThat(roverFromRepo.getYPosition()).isEqualTo(Y);
		assertThat(roverFromRepo.getOrientation()).isEqualTo(Orientation.SOUTH);
		assertThat(roverRepository.getNumberOfRovers()).isEqualTo(1);
	}
	
	@Test
	public void testRemove() {
		UUID plateauUuid = UUID.randomUUID();
		RoverIdentifier roverId = new RoverIdentifier(plateauUuid, ROVER_PREFIX);
		Rover rover = getRover(roverId);
		roverRepository.add(rover );
		Rover roverFromRepo = roverRepository.load(roverId);
		assertThat(roverFromRepo).isNotNull();
		assertThat(roverRepository.getNumberOfRovers()).isEqualTo(1);
		roverRepository.remove(roverId);
		Rover roverFromRepoRemoved = roverRepository.load(roverId);
		assertThat(roverFromRepoRemoved).isNull();
		assertThat(roverRepository.getNumberOfRovers()).isEqualTo(0);
	}
	
	@Test
	public void testRemoveAll() {
		UUID plateauUuid = UUID.randomUUID();
		RoverIdentifier roverId1 = new RoverIdentifier(plateauUuid, ROVER_PREFIX + 1);
		Rover rover1 = getRover(roverId1);
		roverRepository.add(rover1);
		RoverIdentifier roverId2 = new RoverIdentifier(plateauUuid, ROVER_PREFIX + 2);
		Rover rover2 = getRover(roverId2);
		roverRepository.add(rover2);
		assertThat(roverRepository.getNumberOfRovers()).isEqualTo(2);
		roverRepository.removeAllRovers();
		assertThat(roverRepository.getNumberOfRovers()).isEqualTo(0);
	}
	
	private Rover getRover(RoverIdentifier id) {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);
		return new Rover(id, coordinates, Orientation.SOUTH);
	}

}
