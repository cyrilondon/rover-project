package com.game.domain.model.entity;

import java.util.function.BiFunction;
import java.util.function.Function;

import com.game.domain.model.event.DomainEvent;
import com.game.domain.model.validation.ValidationNotificationHandler;

/**
 * Marker interface for the domain entities.
 * Each entity is supposed to be validated against business rules 
 * @see #validate(ValidationNotificationHandler)
 *
 */
public interface Entity<T, U> {
	
	/**
	 * Returns the id
	 * @return
	 */
	 U getId();
	
	/**
	 * Validates the entity with runtime validation handler
	 * @param handler
	 * @return
	 */
	T validate(ValidationNotificationHandler handler);

	/**
	 * Apply the event to current instance and publish the event
	 * In case of exception, call the exceptionFunction
	 * @param event
	 * @param function
	 */
	public void applyAndPublishEvent(DomainEvent event, Function<DomainEvent, DomainEvent> function,  BiFunction<Exception, DomainEvent, DomainEvent> exceptionFunction);

	/**
	 * Apply and publish event if no exception handler is needed
	 * @param event
	 * @param function
	 */
	void applyAndPublishEvent(DomainEvent event, Function<DomainEvent, DomainEvent> function);

	/**
	 * Publish and stores the event
	 * Used in case of inter-Aggregates communication
	 * @param event
	 */
	void publishEvent(DomainEvent event);

}
