package com.game.domain.model.event.rover;

import java.util.UUID;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.event.BaseDomainEvent;

/**
 * Event published in case of something went wrong
 * during Rover moves
 *
 */
public class RoverMovedWithExceptionEvent extends BaseDomainEvent {

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
		return getRoverId().getPlateauId();
	}
	
	public TwoDimensionalCoordinates getRoverPreviousPosition() {
		return getMovedEvent().getPreviousPosition();
	}
	
	@Override
	public String toString() {
		return String.format("RoverMovedWithExceptionEvent published at [%s] with Rover Moved Event [%s], exception [%s]" , super.occuredOn(), movedEvent, exception);
	}

}
