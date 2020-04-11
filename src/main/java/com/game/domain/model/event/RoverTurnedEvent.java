package com.game.domain.model.event;

import com.game.domain.model.entity.Orientation;
import com.game.domain.model.entity.RoverIdentifier;

public class RoverTurnedEvent implements DomainEvent {

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
	

}
