package com.game.domain.model.event;

import java.time.LocalDateTime;

public class BaseDomainEvent implements DomainEvent {

	private LocalDateTime occuredOn;

	public BaseDomainEvent() {
		this.occuredOn = LocalDateTime.now();
	}

	@Override
	public LocalDateTime occuredOn() {
		return occuredOn;
	}

}
