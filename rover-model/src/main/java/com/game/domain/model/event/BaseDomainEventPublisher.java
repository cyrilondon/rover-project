package com.game.domain.model.event;

import java.util.function.BiFunction;
import java.util.function.Function;

import com.game.domain.application.context.GameContext;

public class BaseDomainEventPublisher implements DomainEventPublisher {
	
	protected final Function<DomainEvent, DomainEvent> publishEventFunction = event -> {
		DomainEventPublisherSubscriber.instance().publish(event);
		return event;
	};
	
	
	protected final Function<DomainEvent, Void> eventStoreFunction = event -> {
		GameContext.getInstance().getEventStore().addEvent(event);
		return null;
	};
	
	protected Function<DomainEvent, Void> publishAndStore = (publishEventFunction).andThen(eventStoreFunction);

	
	@Override
	public void publishEvent(DomainEvent event) {
		publishAndStore.apply(event);
	}
	

	@Override
	public void publishEvent(DomainEvent event, Function<DomainEvent, DomainEvent> publishEventFunction, Function<DomainEvent, Void> eventStoreFunction) {
		publishEventFunction.andThen(eventStoreFunction).apply(event);
	}
	
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
