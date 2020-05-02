package com.game.domain.model.entity;

public interface ConcurrencySafeEntity<T,U> extends Entity<T, U>{
	
	public int getVersion();
	
	public void setVersion(int version);

}
