package com.game.domain.model.event;

public interface DomainEventSubscriber<T,U> {

	public void handleEvent(T event);

	public Class<T> subscribedToEventType();
	
	public U getId();

}
