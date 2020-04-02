package com.game.domain.model.validation;

/**
 * Entity generic Validator which delegates the validation responsibility to a {@link ValidationNotificationHandler} component.
 * Example of Strategy pattern, which allows to enable the selection of the validation algorithm at runtime.
 * {@link https://en.wikipedia.org/wiki/Strategy_pattern}
 * Extract: "For instance, a class that performs validation on incoming data may use the strategy pattern 
 * to select a validation algorithm depending on the type of data, the source of the data, user choice, or other discriminating factors".
 *
 */
public abstract class EntityValidator<T> {

	private T entity;

	private ValidationNotificationHandler notificationHandler;

	public EntityValidator(T entity, ValidationNotificationHandler handler) {
		super();
		this.notificationHandler = handler;
		this.entity = entity;
	}

	/**
	 * Example of Template method pattern.
	 * The overall method process is defined here and can not be overridden as marked as final.
	 * Subclasses can only override the protected {@link doValidate} method.
	 * @see https://en.wikipedia.org/wiki/Template_method_pattern
	 */
	public final void validate() {
		doValidate();
		notificationHandler.checkValidationResult();
	}
	
	/**
	 * Method to be overridden by the subclasses
	 */
	protected  abstract void doValidate();

	protected ValidationNotificationHandler notificationHandler() {
		return this.notificationHandler;
	}

	protected T entity() {
		return entity;
	}

}
