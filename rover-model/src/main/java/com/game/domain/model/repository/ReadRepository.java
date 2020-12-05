package com.game.domain.model.repository;

public interface ReadRepository<T> {
	
	public void add(T entityId);
	
	public int getNumberOfEntities();

}
