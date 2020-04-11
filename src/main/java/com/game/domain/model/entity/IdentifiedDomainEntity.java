package com.game.domain.model.entity;

import java.util.function.Function;

import com.game.domain.model.event.DomainEvent;
import com.game.domain.model.event.DomainEventPublisher;

public abstract class IdentifiedDomainEntity<T, U> implements Entity<T, U> {
	
	protected U id;
	
	@Override
	public  U getId() {
		return this.id;
	}
	
	@Override
	public void applyAndPublishEvent(DomainEvent event, Function<DomainEvent, Void> function) {
		function.apply((DomainEvent) event);
		publishEvent(event);
	}
	
	/**
	 * Publish the event for persistence purpose, which means:
	 *  - updating the DB instance with current state 
	 *  - store the Event in event store
	 * 
	 * @param event the Domain Event
	 */
	private void publishEvent(DomainEvent event) {
		// publish the event for persistence purpose (current instance + event)
		DomainEventPublisher.instance().publish(event);
	}

}
