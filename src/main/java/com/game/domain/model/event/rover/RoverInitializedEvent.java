package com.game.domain.model.event.rover;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.rover.Orientation;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.event.BaseDomainEvent;

public class RoverInitializedEvent extends BaseDomainEvent {
	
	private RoverIdentifier roverId;

	private TwoDimensionalCoordinates position;

	Orientation orientation;

	private RoverInitializedEvent(Builder builder) {
		this.roverId = builder.roverId;
		this.position = builder.position;
		this.orientation = builder.orientation;
	}

	public RoverIdentifier getRoverId() {
		return roverId;
	}


	
	public TwoDimensionalCoordinates getPosition() {
		return position;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public static class Builder {

		private RoverIdentifier roverId;
		
		private TwoDimensionalCoordinates position;

		Orientation orientation;
		
		public Builder withRoverId(RoverIdentifier roverId) {
			this.roverId = roverId;
			return this;
		}

		public Builder withPosition(TwoDimensionalCoordinates position) {
			this.position = position;
			return this;
		}

		public Builder withOrientation(Orientation orientation) {
			this.orientation = orientation;
			return this;
		}

		public RoverInitializedEvent build() {
			return new RoverInitializedEvent(this);
		}

	}
	
	@Override
	public String toString() {
		return String.format("RoverInitializedEvent published at [%s] with rover id [%s], position [%s], orientation [%s]" , super.occuredOn(), roverId, position, orientation);
	}

}
