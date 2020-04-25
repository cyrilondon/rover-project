package com.game.domain.model.event;

public abstract class AbstractDomainEventSubscriber<T> implements DomainEventSubscriber<T, String> {
	
	protected String id = this.getClass().getSimpleName();

	@Override
	public String getId(){
		return id;
	}
}
