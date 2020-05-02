package com.game.domain.model.event;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.testng.annotations.Test;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.entity.rover.RoverIdentifierDto;
import com.game.domain.model.event.rover.RoverMovedEvent;

public class RoverMovedEventTest {

	@Test
	public void testEvent() {
		UUID uuid = UUID.randomUUID();
		RoverIdentifier id = new RoverIdentifier(uuid, GameContext.ROVER_NAME_PREFIX);
		TwoDimensionalCoordinates previousPosition = new TwoDimensionalCoordinates(3, 4);
		TwoDimensionalCoordinates currentPosition = new TwoDimensionalCoordinates(3, 5);
		RoverMovedEvent event = new RoverMovedEvent.Builder().withRoverId(new RoverIdentifierDto(id,0)).withCurrentPosition(currentPosition)
				.withPreviousPosition(previousPosition).build();
		assertThat(event.getRoverId().getId()).isEqualTo(id);
		assertThat(event.getPreviousPosition()).isEqualTo(previousPosition);
		assertThat(event.getCurrentPosition()).isEqualTo(currentPosition);
	}

}
