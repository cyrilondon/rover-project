package com.game.domain.model.event;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.testng.annotations.Test;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.rover.Orientation;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.entity.rover.RoverIdentifierDto;
import com.game.domain.model.event.rover.RoverTurnedEvent;

public class RoverTurnedEventTest {

	@Test
	public void testEvent() {
		UUID uuid = UUID.randomUUID();
		RoverIdentifier id = new RoverIdentifier(uuid, GameContext.ROVER_NAME_PREFIX);
		Orientation previousOrientation = Orientation.SOUTH;
		Orientation currentOrientation = Orientation.WEST;
		RoverTurnedEvent event = new RoverTurnedEvent.Builder().withRoverId(new RoverIdentifierDto(id, 0))
				.withPreviousOrientation(previousOrientation).withCurrentOrientation(currentOrientation).build();
		assertThat(event.getRoverId().getId()).isEqualTo(id);
		assertThat(event.getPreviousOrientation()).isEqualTo(previousOrientation);
		assertThat(event.getCurrentOrientation()).isEqualTo(currentOrientation);
		
	}

}
