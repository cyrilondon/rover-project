package com.game.domain.model.event.rover;

import com.game.domain.model.entity.rover.Orientation;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.event.BaseDomainEvent;

public class RoverTurnedEvent extends BaseDomainEvent {

	private RoverIdentifier roverId;

	private Orientation  previousOrientation;
	
	private Orientation  currentOrientation;

	private RoverTurnedEvent(Builder builder) {
		this.roverId = builder.roverId;
		this.previousOrientation = builder.previousOrientation;
		this.currentOrientation = builder.currentOrientation;
	}

	public RoverIdentifier getRoverId() {
		return roverId;
	}

	public Orientation getPreviousOrientation() {
		return previousOrientation;
	}

	public Orientation getCurrentOrientation() {
		return currentOrientation;
	}
	
	public static class Builder {
		
		private RoverIdentifier roverId;

		private Orientation  previousOrientation;
		
		private Orientation  currentOrientation;
		
		public Builder withRoverId(RoverIdentifier roverId) {
			this.roverId = roverId;
			return this;
		}
		
		public Builder withPreviousOrientation(Orientation orientation) {
			this.previousOrientation = orientation;
			return this;
		}
		
		public Builder withCurrentOrientation(Orientation orientation) {
			this.currentOrientation = orientation;
			return this;
		}
		
		public RoverTurnedEvent build() {
			return new RoverTurnedEvent(this);
		}
				
	}
	
	@Override
	public String toString() {
		return String.format("RoverTurnedEvent published at [%s] with rover id [%s], previous orientation [%s], current orientation [%s]" , super.occuredOn(), roverId, previousOrientation, currentOrientation);
	}
	

}
