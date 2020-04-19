package com.game.domain.model.entity.rover;

import java.util.UUID;

import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.rover.Orientation;
import com.game.domain.model.entity.rover.Rover;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.IllegalArgumentGameException;

public class RoverIdentifierTest {
	
	@Test
	public void testConstructor() {
		UUID uuid = UUID.randomUUID();
		RoverIdentifier id = new RoverIdentifier(uuid, GameContext.ROVER_NAME_PREFIX);
		assertThat(id.getName()).isEqualTo(GameContext.ROVER_NAME_PREFIX);
		assertThat(id.getPlateauId()).isEqualTo(uuid);
	}
	
	@Test
	public void testEquals() {
		UUID uuid = UUID.randomUUID();
		RoverIdentifier id = new RoverIdentifier(uuid, GameContext.ROVER_NAME_PREFIX);
		RoverIdentifier otherId = new RoverIdentifier(uuid, GameContext.ROVER_NAME_PREFIX);
		assertThat(id).isEqualTo(otherId);
	}
	
	@Test
	public void testHashCode() {
		UUID uuid = UUID.randomUUID();
		RoverIdentifier id = new RoverIdentifier(uuid, GameContext.ROVER_NAME_PREFIX);
		RoverIdentifier otherId = new RoverIdentifier(uuid, GameContext.ROVER_NAME_PREFIX);
		assertThat(id.hashCode()).isEqualTo(otherId.hashCode());
	}
	
	@Test
	public void testWithNullPlateauId() {
		Throwable thrown = catchThrowable(() -> new Rover(new RoverIdentifier(null, GameContext.ROVER_NAME_PREFIX), new TwoDimensionalCoordinates(2, 3), Orientation.SOUTH));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
						String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE,
								GameExceptionLabels.MISSING_PLATEAU_UUID)));
	}
	
	
	@Test
	public void testWithNullName() {
		Throwable thrown = catchThrowable(() -> new Rover(new RoverIdentifier(UUID.randomUUID(), null), new TwoDimensionalCoordinates(2, 3), Orientation.SOUTH));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
						String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE,
								GameExceptionLabels.MISSING_ROVER_NAME)));
	}
	
	@Test
	public void testWithEmptyName() {
		Throwable thrown = catchThrowable(() -> new Rover(new RoverIdentifier(UUID.randomUUID(), "  "), new TwoDimensionalCoordinates(2, 3), Orientation.SOUTH));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
						String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE,
								GameExceptionLabels.MISSING_ROVER_NAME)));
	}
	

}
