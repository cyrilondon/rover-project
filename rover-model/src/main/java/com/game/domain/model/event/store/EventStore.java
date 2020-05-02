package com.game.domain.model.event.store;
import java.util.List;

import com.game.domain.model.event.DomainEvent;

public interface EventStore {
	
	void addEvent(DomainEvent event);

	List<DomainEvent> getAllEvents();

}
