package com.game.domain.model.event;

import java.time.LocalDateTime;

public interface DomainEvent {
	
	LocalDateTime occuredOn();

}
