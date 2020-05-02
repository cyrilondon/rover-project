package com.game.domain.model.entity;

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

}
