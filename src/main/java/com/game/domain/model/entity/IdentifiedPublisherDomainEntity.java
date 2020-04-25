package com.game.domain.model.entity;

import com.game.domain.model.event.BaseDomainEventPublisher;

public abstract class IdentifiedPublisherDomainEntity<T, U> extends BaseDomainEventPublisher implements ConcurrencySafeEntity<T, U> {

	protected U id;
	
	protected int version;

	@Override
	public int getVersion() {
		return version;
	}
	
	@Override
	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public U getId() {
		return this.id;
	}

}
