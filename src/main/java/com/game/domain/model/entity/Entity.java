package com.game.domain.model.entity;

import com.game.domain.model.validation.ValidationNotificationHandler;

public interface Entity {
	
	public void validate(ValidationNotificationHandler handler);

}
