package com.game.domain.model.event.rover;

import java.util.UUID;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.event.exception.BaseDomainEventWithException;

/**
 * Event published in case of something went wrong
 * during Rover moves
 *
 */
public class RoverMovedWithExceptionEvent extends BaseDomainEventWithException<RoverMovedEvent> {
	
	public RoverMovedWithExceptionEvent(RoverMovedEvent movedEvent, Exception exception) {
		super(movedEvent, exception);
	}
	
	public RoverIdentifier getRoverId() {
		return getEvent().getRoverId().getId();
	}
	
	public UUID getPlateauUuid() {
		return getRoverId().getPlateauId();
	}
	
	public TwoDimensionalCoordinates getRoverPreviousPosition() {
		return getEvent().getPreviousPosition();
	}
	
	@Override
	public String toString() {
		return String.format("RoverMovedWithExceptionEvent published at [%s] with Rover Moved Event [%s], exception [%s]" , super.occuredOn(), getEvent(), getException());
	}

}
