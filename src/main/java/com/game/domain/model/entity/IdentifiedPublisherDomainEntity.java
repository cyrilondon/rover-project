package com.game.domain.model.entity;

public abstract class IdentifiedPublisherDomainEntity<T, U> extends EntityBaseDomainEventPublisher implements Entity<T, U> {

	protected U id;

	@Override
	public U getId() {
		return this.id;
	}

}
