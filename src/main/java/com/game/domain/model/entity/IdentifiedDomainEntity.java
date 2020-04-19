package com.game.domain.model.entity;

import java.util.function.BiFunction;
import java.util.function.Function;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.event.DomainEvent;
import com.game.domain.model.event.DomainEventPublisher;

public abstract class IdentifiedDomainEntity<T, U> implements Entity<T, U> {

	protected U id;

	@Override
	public U getId() {
		return this.id;
	}
	
	final Function<DomainEvent, DomainEvent> publishEventFunction = event -> {
		DomainEventPublisher.instance().publish(event);
		return event;
	};
	
	/**
	 * Event Store function defined in GameContext for more flexibility
	 */
	final Function<DomainEvent, Void> eventStoreFunction = GameContext.getInstance().storeEventFunction;

	@Override
	public void applyAndPublishEvent(DomainEvent event, Function<DomainEvent, DomainEvent> function,
			BiFunction<Exception, DomainEvent, DomainEvent> exceptionFunction) {
		try {
			function.andThen(publishEventFunction).andThen(eventStoreFunction).apply(event);
		} catch (Exception exception) {
			exceptionFunction.andThen(publishEventFunction).andThen(eventStoreFunction).apply(exception, event);
		}
	}

	@Override
	public void applyAndPublishEvent(DomainEvent event, Function<DomainEvent, DomainEvent> function) {
		function.andThen(publishEventFunction).andThen(eventStoreFunction).apply(event);
	}
	
	@Override
	public void publishEvent(DomainEvent event) {
		publishEventFunction.andThen(eventStoreFunction).apply(event);
	}

}
