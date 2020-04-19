package com.game.domain.model.entity;

import java.util.function.BiFunction;
import java.util.function.Function;

import com.game.domain.model.event.BaseDomainEventPublisher;
import com.game.domain.model.event.DomainEvent;

public class EntityBaseDomainEventPublisher extends BaseDomainEventPublisher  {
	
	public void applyAndPublishEvent(DomainEvent event, Function<DomainEvent, DomainEvent> function,
			BiFunction<Exception, DomainEvent, DomainEvent> exceptionFunction) {
		try {
			function.andThen(publishEventFunction).andThen(eventStoreFunction).apply(event);
		} catch (Exception exception) {
			exceptionFunction.andThen(publishEventFunction).apply(exception, event);
		} finally {
			eventStoreFunction.apply(event);
		}
	}

	public void applyAndPublishEvent(DomainEvent event, Function<DomainEvent, DomainEvent> function) {
		function.andThen(publishEventFunction).andThen(eventStoreFunction).apply(event);
	}

}
