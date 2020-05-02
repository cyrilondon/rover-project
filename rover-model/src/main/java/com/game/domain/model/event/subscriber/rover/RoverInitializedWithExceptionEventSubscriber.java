package com.game.domain.model.event.subscriber.rover;

import java.util.Objects;

import com.game.domain.model.event.AbstractDomainEventSubscriber;
import com.game.domain.model.event.rover.RoverInitializedWithExceptionEvent;
import com.game.domain.model.exception.RoverInitializationException;

public class RoverInitializedWithExceptionEventSubscriber extends AbstractDomainEventSubscriber<RoverInitializedWithExceptionEvent> {

	@Override
	public void handleEvent(RoverInitializedWithExceptionEvent event) {
		throw new RoverInitializationException(event.getException().getMessage());
	}

	@Override
	public Class<RoverInitializedWithExceptionEvent> subscribedToEventType() {
		return RoverInitializedWithExceptionEvent.class;
	}
	
	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (obj instanceof RoverInitializedWithExceptionEventSubscriber) {
			RoverInitializedWithExceptionEventSubscriber other = (RoverInitializedWithExceptionEventSubscriber) obj;
			return Objects.equals(id, other.getId());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

}
