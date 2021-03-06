package com.game.domain.model.event.rover;

import java.util.UUID;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.rover.RoverIdentifierDto;
import com.game.domain.model.event.BaseDomainEvent;

public class RoverMovedEvent extends BaseDomainEvent {

	private RoverIdentifierDto roverId;

	TwoDimensionalCoordinates previousPosition;

	TwoDimensionalCoordinates currentPosition;
	

	protected RoverMovedEvent(Builder builder) {
		super();
		this.roverId = builder.roverId;
		this.previousPosition = builder.previousPosition;
		this.currentPosition = builder.currentPosition;
	}

	public RoverIdentifierDto getRoverId() {
		return roverId;
	}
	
	public UUID getPlateauUUID() {
		return roverId.getId().getPlateauId();
	}

	public TwoDimensionalCoordinates getPreviousPosition() {
		return previousPosition;
	}

	public TwoDimensionalCoordinates getCurrentPosition() {
		return currentPosition;
	}

	public static class Builder {
		
		private RoverIdentifierDto roverId;

		private TwoDimensionalCoordinates previousPosition, currentPosition;

		public Builder withRoverId(RoverIdentifierDto roverId) {
			   this.roverId = roverId;
			   return this;
		   }

		public Builder withPreviousPosition(TwoDimensionalCoordinates previousPosition) {
			this.previousPosition = previousPosition;
			return this;
		}

		public Builder withCurrentPosition(TwoDimensionalCoordinates currentPosition) {
			this.currentPosition = currentPosition;
			return this;
		}

		public RoverMovedEvent build() {
			return new RoverMovedEvent(this);
		}

	}
	
	@Override
	public String toString() {
		return String.format("RoverMovedEvent published at [%s] with rover id [%s], previous position [%s], current position [%s]" , super.occuredOn(), roverId, previousPosition, currentPosition);
	}

}
