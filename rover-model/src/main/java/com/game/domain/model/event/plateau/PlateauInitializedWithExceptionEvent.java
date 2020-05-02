package com.game.domain.model.event.plateau;

import com.game.domain.model.event.exception.BaseDomainEventWithException;

public class PlateauInitializedWithExceptionEvent extends BaseDomainEventWithException<PlateauInitializedEvent> {

	public PlateauInitializedWithExceptionEvent(PlateauInitializedEvent event, Exception exception) {
		super(event, exception);
	}

}
