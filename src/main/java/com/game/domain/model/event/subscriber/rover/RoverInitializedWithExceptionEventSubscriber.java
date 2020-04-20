package com.game.domain.model.event.subscriber.rover;

import com.game.domain.model.event.DomainEventSubscriber;
import com.game.domain.model.event.rover.RoverInitializedWithExceptionEvent;
import com.game.domain.model.exception.RoverInitializationException;

public class RoverInitializedWithExceptionEventSubscriber implements DomainEventSubscriber<RoverInitializedWithExceptionEvent> {

	@Override
	public void handleEvent(RoverInitializedWithExceptionEvent event) {
		throw new RoverInitializationException(event.getException().getMessage());
	}

	@Override
	public Class<RoverInitializedWithExceptionEvent> subscribedToEventType() {
		return RoverInitializedWithExceptionEvent.class;
	}

}
