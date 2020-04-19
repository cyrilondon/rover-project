package com.game.domain.model.event.subscriber.rover;

import com.game.domain.application.GameContext;
import com.game.domain.model.event.DomainEventSubscriber;
import com.game.domain.model.event.rover.RoverMovedEvent;

public class RoverMovedEventSubscriber implements DomainEventSubscriber<RoverMovedEvent> {

	@Override
	public void handleEvent(RoverMovedEvent event) {
	    
		// update persistent Rover with last position
		GameContext.getInstance().getRoverService().updateRoverWithPosition(event.getRoverId(), event.getCurrentPosition());
		
	}

	@Override
	public Class<RoverMovedEvent> subscribedToEventType() {
		return RoverMovedEvent.class;
	}

}
