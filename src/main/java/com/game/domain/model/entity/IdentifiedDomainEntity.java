package com.game.domain.model.entity;

public abstract class IdentifiedDomainEntity<T, U> implements Entity<T, U> {
	
	protected U id;
	
	@Override
	public  U getId() {
		return this.id;
	}

}
