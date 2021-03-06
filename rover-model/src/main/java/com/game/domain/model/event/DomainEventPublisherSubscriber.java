package com.game.domain.model.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer to publish/subscribe {@link DomainEvent}
 *
 */
public class DomainEventPublisherSubscriber {

	@SuppressWarnings("rawtypes")
	private static final ThreadLocal<List> subscribers = new ThreadLocal<>();

	private static final ThreadLocal<Boolean> publishing = new ThreadLocal<>();

	public static DomainEventPublisherSubscriber instance() {
		return new DomainEventPublisherSubscriber();
	}

	@SuppressWarnings("unchecked")
	public <T extends DomainEvent> void publish(final T domainEvent) {
		if (null !=publishing.get() && publishing.get()) return;

		try {
			publishing.set(Boolean.TRUE);
			List<DomainEventSubscriber<T,String>> registeredSubscribers = subscribers.get();
			if (registeredSubscribers != null) {
				Class<?> eventType = domainEvent.getClass();
				registeredSubscribers.forEach(subscriber -> {
					handleEvent(subscriber, eventType, domainEvent);
				});
			}

		} finally {
			publishing.set(Boolean.FALSE);
		}

	}

	
	@SuppressWarnings("unchecked")
	public <T extends DomainEvent> void subscribe(DomainEventSubscriber<T, String> subscriber) {
		if (null !=publishing.get() && publishing.get()) return;
		
		List<DomainEventSubscriber<T,String>> registeredSubscribers = subscribers.get();
		if (registeredSubscribers == null) {
			registeredSubscribers = new ArrayList<DomainEventSubscriber<T,String>>();
			subscribers.set(registeredSubscribers);
		}
		if (!registeredSubscribers.contains(subscriber))
		registeredSubscribers.add(subscriber);
	}

	private <T extends DomainEvent> void handleEvent(DomainEventSubscriber<T,String> subscriber, Class<?> eventType, T domainEvent) {
		Class<?> subscribedTo = subscriber.subscribedToEventType();
		if (subscribedTo == eventType) {
			subscriber.handleEvent(domainEvent);
		}

	}
	
	public void clear() {
		subscribers.remove();
	}
	
	@SuppressWarnings("rawtypes")
	public static ThreadLocal<List> getSubscribers() {
		return subscribers;
	}

	public static ThreadLocal<Boolean> getPublishing() {
		return publishing;
	}
}
