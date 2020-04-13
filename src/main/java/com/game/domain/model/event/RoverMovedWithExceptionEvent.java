package com.game.domain.model.event;

import java.util.UUID;

import com.game.domain.model.entity.RoverIdentifier;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

/**
 * Event published in case of something went wrong
 * during Rover moves
 *
 */
public class RoverMovedWithExceptionEvent implements DomainEvent {

	RoverMovedEvent movedEvent;

	Exception exception;
	
	public RoverMovedWithExceptionEvent(RoverMovedEvent movedEvent, Exception exception) {
		this.movedEvent = movedEvent;
		this.exception = exception;
	}

	public RoverMovedEvent getMovedEvent() {
		return movedEvent;
	}

	public Exception getException() {
		return exception;
	}
	
	public RoverIdentifier getRoverId() {
		return getMovedEvent().getRoverId();
	}
	
	public UUID getPlateauUuid() {
		return getRoverId().getPlateauUuid();
	}
	
	public TwoDimensionalCoordinates getRoverPreviousPosition() {
		return getMovedEvent().getPreviousPosition();
	}
	
	@Override
	public String toString() {
		return String.format("RoverMovedEvent published with Rover Moved Event [%s], exception [%s]" , movedEvent, exception);
	}

}
