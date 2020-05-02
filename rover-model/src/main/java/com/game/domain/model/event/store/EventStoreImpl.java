package com.game.domain.model.event.store;

import java.util.ArrayList;
import java.util.List;

import com.game.domain.model.event.DomainEvent;

/**
 * Dummy event store
 * Loaded by the GameContext via ServiceLocator
 * Used by the Domain Entities when publishing the event
 *
 */
public class EventStoreImpl implements EventStore {
	
	private List<DomainEvent> events = new ArrayList<>();
	
	@Override
	public void addEvent(DomainEvent event) {
		events.add(event);
	}
	
	@Override
	public List<DomainEvent> getAllEvents(){
		return events;
	}
	
	

}
