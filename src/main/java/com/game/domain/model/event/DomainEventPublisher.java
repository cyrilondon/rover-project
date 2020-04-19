package com.game.domain.model.event;

import java.util.function.Function;

public interface DomainEventPublisher {
	

	/**
	 * Default Publish and stores algorithm
	 * Used in case of inter-Aggregates communication
	 * @param event
	 */
	void publishEvent(DomainEvent event);

	/**
	 * Custom Publish and stores algorithm if needed in a child class
	 * @param publishEventFunction
	 * @param eventStoreFunction
	 * @param event
	 */
	void publishEvent(DomainEvent event, Function<DomainEvent, DomainEvent> publishEventFunction,
			Function<DomainEvent, Void> eventStoreFunction);

}
