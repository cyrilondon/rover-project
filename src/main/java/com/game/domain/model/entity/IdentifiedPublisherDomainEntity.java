package com.game.domain.model.entity;

import com.game.domain.model.event.BaseDomainEventPublisher;

public abstract class IdentifiedPublisherDomainEntity<T, U> extends BaseDomainEventPublisher implements Entity<T, U> {

	protected U id;

	@Override
	public U getId() {
		return this.id;
	}

}
