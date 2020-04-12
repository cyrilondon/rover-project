package com.game.domain.model.event;

import com.game.domain.model.entity.RoverIdentifier;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

public class RoverMovedEvent implements DomainEvent {

	private RoverIdentifier roverId;

	TwoDimensionalCoordinates previousPosition;

	TwoDimensionalCoordinates currentPosition;


	protected RoverMovedEvent(Builder builder) {
		this.roverId = builder.roverId;
		this.previousPosition = builder.previousPosition;
		this.currentPosition = builder.currentPosition;

	}

	public RoverIdentifier getRoverId() {
		return roverId;
	}

	public TwoDimensionalCoordinates getPreviousPosition() {
		return previousPosition;
	}

	public TwoDimensionalCoordinates getCurrentPosition() {
		return currentPosition;
	}

	public static class Builder {
		
		private RoverIdentifier roverId;

		private TwoDimensionalCoordinates previousPosition, currentPosition;

		public Builder withRoverId(RoverIdentifier roverId) {
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

}
