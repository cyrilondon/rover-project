package com.game.domain.model.event;

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

	
	@Override
	public void publishEvent(DomainEvent event) {
		publishEventFunction.andThen(eventStoreFunction).apply(event);
	}
	

	@Override
	public void publishEvent(DomainEvent event, Function<DomainEvent, DomainEvent> publishEventFunction, Function<DomainEvent, Void> eventStoreFunction) {
		publishEventFunction.andThen(eventStoreFunction).apply(event);
	}

}
