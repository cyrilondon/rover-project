package com.game.domain.model.event.subscriber.rover;

import java.util.Objects;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.event.AbstractDomainEventSubscriber;
import com.game.domain.model.event.rover.RoverMovedEvent;

public class RoverMovedEventSubscriber extends AbstractDomainEventSubscriber<RoverMovedEvent> {

	@Override
	public void handleEvent(RoverMovedEvent event) {
	    
		// update persistent Rover with last position
		GameContext.getInstance().getRoverService().updateRoverWithPosition(event.getRoverId(), event.getCurrentPosition());
		
	}

	@Override
	public Class<RoverMovedEvent> subscribedToEventType() {
		return RoverMovedEvent.class;
	}
	
	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (obj instanceof RoverMovedEventSubscriber) {
			RoverMovedEventSubscriber other = (RoverMovedEventSubscriber) obj;
			return Objects.equals(getId(), other.getId());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

}
