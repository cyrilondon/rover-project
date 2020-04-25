package com.game.domain.model.event.subscriber.rover;

import java.util.Objects;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.event.AbstractDomainEventSubscriber;
import com.game.domain.model.event.rover.RoverTurnedEvent;

public class RoverTurnedEventSubscriber extends AbstractDomainEventSubscriber<RoverTurnedEvent> {

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
	
	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (obj instanceof RoverTurnedEventSubscriber) {
			RoverTurnedEventSubscriber other = (RoverTurnedEventSubscriber) obj;
			return Objects.equals(id, other.getId());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}


}
