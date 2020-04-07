package com.game.domain.model.repository;

public interface DomainRepository<T,U> {

	T load(U id);

	void add(T entity);

	void update(T entity);

	void remove(U id);

}
