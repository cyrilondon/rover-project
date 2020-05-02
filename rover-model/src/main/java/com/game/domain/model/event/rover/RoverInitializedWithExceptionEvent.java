package com.game.domain.model.event.rover;

import com.game.domain.model.event.exception.BaseDomainEventWithException;

public class RoverInitializedWithExceptionEvent extends BaseDomainEventWithException<RoverInitializedEvent> {

	public RoverInitializedWithExceptionEvent(RoverInitializedEvent event, Exception exception) {
		super(event, exception);
	}

}
