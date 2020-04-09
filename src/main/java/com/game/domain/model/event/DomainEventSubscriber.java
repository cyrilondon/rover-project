package com.game.domain.model.event;

public interface DomainEventSubscriber<T> {

	public void handleEvent(T event);

	public Class<T> subscribedToEventType();

}
