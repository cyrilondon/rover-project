package com.game.domain.model.event.subscriber;
import com.game.domain.application.GameContext;
import com.game.domain.model.event.DomainEventSubscriber;
import com.game.domain.model.event.RoverMovedEvent;

public class RoverMovedEventSubscriber implements DomainEventSubscriber<RoverMovedEvent> {

	@Override
	public void handleEvent(RoverMovedEvent event) {
		// 1. update persistent Rover with last position
		updateRoverWithLastPosition(event);

		// 2 . update plateau locations
		updatePlateauWithLastLocations(event);

		// 3. store the event
		// TODO with Kafka Producer?
	}

	@Override
	public Class<RoverMovedEvent> subscribedToEventType() {
		return RoverMovedEvent.class;
	}

	private void updateRoverWithLastPosition(RoverMovedEvent event) {
		GameContext.getInstance().getRoverService().updateRoverWithPosition(event.getRoverId(), event.getCurrentPosition());
	}

	private void updatePlateauWithLastLocations(RoverMovedEvent event) {
		GameContext.getInstance().getPlateauService().updatePlateauWithLocations(event.getRoverId().getPlateauUuid(),
				event.getPreviousPosition(), event.getCurrentPosition());
	}

}
