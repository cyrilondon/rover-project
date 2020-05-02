package com.game.domain.model.event;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.testng.annotations.Test;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.entity.rover.RoverIdentifierDto;
import com.game.domain.model.event.rover.RoverMovedEvent;

public class BaseDomainEventPublisherTest {
	
	private List<DomainEvent> publishedEvents = new ArrayList<>();
	
	private List<DomainEvent> storedEvents = new ArrayList<>();
	
	protected final Function<DomainEvent, DomainEvent> publishEventFunction = event -> {
		publishedEvents.add(event);
		return event;
	};
	
	protected final Function<DomainEvent, Void> eventStoreFunction = event -> {
		storedEvents.add(event);
		return null;
	};
	
	@Test
	public void testPublishEvent() {
		RoverIdentifier id = new RoverIdentifier(UUID.randomUUID(), GameContext.ROVER_NAME_PREFIX);
		TwoDimensionalCoordinates previousPosition = new TwoDimensionalCoordinates(3, 4);
		TwoDimensionalCoordinates currentPosition = new TwoDimensionalCoordinates(3, 5);
		RoverMovedEvent event = new RoverMovedEvent.Builder().withRoverId(new RoverIdentifierDto(id, 0)).withCurrentPosition(currentPosition)
				.withPreviousPosition(previousPosition).build();
		new BaseDomainEventPublisher().publishEvent(event, publishEventFunction, eventStoreFunction);
		assertThat(publishedEvents).contains(event);
		assertThat(storedEvents).contains(event);
		
	}

}
