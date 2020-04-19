package com.game.domain.application.service;

import java.util.function.BiFunction;
import java.util.function.Function;

import com.game.domain.model.event.BaseDomainEventPublisher;
import com.game.domain.model.event.DomainEvent;

public class ServiceBaseDomainEventPublisher extends BaseDomainEventPublisher  {
	
	public void applyAndPublishEvent(DomainEvent event, Function<DomainEvent, DomainEvent> function,
			BiFunction<Exception, DomainEvent, DomainEvent> exceptionFunction) {
		try {
			function.andThen(publishEventFunction).andThen(eventStoreFunction).apply(event);
		} catch (Exception exception) {
			exceptionFunction.andThen(publishEventFunction).andThen(eventStoreFunction).apply(exception, event);
		}
	}

	public void applyAndPublishEvent(DomainEvent event, Function<DomainEvent, DomainEvent> function) {
		function.andThen(publishEventFunction).andThen(eventStoreFunction).apply(event);
	}

}
