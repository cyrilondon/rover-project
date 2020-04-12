package com.game.domain.model.entity;

import java.util.function.BiFunction;
import java.util.function.Function;

import com.game.domain.model.event.DomainEvent;
import com.game.domain.model.event.DomainEventPublisher;

public abstract class IdentifiedDomainEntity<T, U> implements Entity<T, U> {

	protected U id;

	@Override
	public U getId() {
		return this.id;
	}
	
	final Function<DomainEvent, Void> publishEventFunction = event -> {
		DomainEventPublisher.instance().publish(event);
		return null;
	};

	@Override
	public void applyAndPublishEvent(DomainEvent event, Function<DomainEvent, DomainEvent> function,
			BiFunction<Exception, DomainEvent, DomainEvent> exceptionFunction) {
		try {
			function.andThen(publishEventFunction).apply(event);
		} catch (Exception exception) {
			exceptionFunction.andThen(publishEventFunction).apply(exception, event);
		}
	}

	@Override
	public void applyAndPublishEvent(DomainEvent event, Function<DomainEvent, DomainEvent> function) {
		function.andThen(publishEventFunction).apply(event);
	}

}
