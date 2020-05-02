package com.game.domain.model.event.exception;

import com.game.domain.model.event.BaseDomainEvent;
import com.game.domain.model.event.DomainEvent;

public class BaseDomainEventWithException<T extends DomainEvent> extends BaseDomainEvent {
	
	T event;

	Exception exception;
	
	public BaseDomainEventWithException(T event, Exception exception) {
		this.event = event;
		this.exception = exception;
	}

	public T getEvent() {
		return event;
	}

	public Exception getException() {
		return exception;
	}
	

}
