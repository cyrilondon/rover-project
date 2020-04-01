package com.game.domain.model.validation;

public abstract class EntityValidator<T> {

	private T entity;

	private ValidationNotificationHandler notificationHandler;

	public EntityValidator(T entity, ValidationNotificationHandler handler) {
		super();
		this.notificationHandler = handler;
		this.entity = entity;
	}

	protected abstract void validate();

	protected ValidationNotificationHandler notificationHandler() {
		return this.notificationHandler;
	}

	protected T entity() {
		return entity;
	}

}
