package com.game.domain.model.event.subscriber.rover;

import com.game.domain.application.GameContext;
import com.game.domain.model.event.DomainEventSubscriber;
import com.game.domain.model.event.rover.RoverTurnedEvent;

public class RoverTurnedEventSubscriber implements DomainEventSubscriber<RoverTurnedEvent> {

	@Override
	public void handleEvent(RoverTurnedEvent event) {
		// 1. update persistent Rover with last orientation
		updateRoverWithOrientation(event);
	}

	@Override
	public Class<RoverTurnedEvent> subscribedToEventType() {
		return RoverTurnedEvent.class;
	}

	private void updateRoverWithOrientation(RoverTurnedEvent event) {
		GameContext.getInstance().getRoverService().updateRoverWithOrientation(event.getRoverId(),
				event.getCurrentOrientation());
	}

}
