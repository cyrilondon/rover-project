package com.game.domain.model.entity;

import java.util.function.BiFunction;
import java.util.function.Function;

import com.game.domain.model.event.BaseDomainEventPublisher;
import com.game.domain.model.event.DomainEvent;

public class EntityBaseDomainEventPublisher extends BaseDomainEventPublisher {

	public void applyAndPublishEvent(DomainEvent event, Function<DomainEvent, DomainEvent> function,
			BiFunction<Exception, DomainEvent, DomainEvent> exceptionFunction) {
		try {
			function.andThen(publishAndStore).apply(event);
		} catch (Exception exception) {
			DomainEvent exceptionEvent = null;
			try {
				exceptionEvent = exceptionFunction.apply(exception, event);
				publishEventFunction.apply(exceptionEvent);
				// needed as whatever the exceptionFunction is supposed to do
				// (throwing an exception or not) we want to store the event
			} finally {
				eventStoreFunction.apply(exceptionEvent);
			}
		}
	}

	public void applyAndPublishEvent(DomainEvent event, Function<DomainEvent, DomainEvent> function) {
		function.andThen(publishAndStore).apply(event);
	}

}
