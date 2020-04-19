package com.game.domain.model.event.plateau;

import java.util.UUID;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.event.DomainEvent;

public class PlateauSwitchedLocationEvent implements DomainEvent {
	
	private UUID plateauId;

	TwoDimensionalCoordinates releasedPosition;

	TwoDimensionalCoordinates occupiedPosition;


	protected PlateauSwitchedLocationEvent(Builder builder) {
		this.plateauId = builder.plateauId;
		this.releasedPosition = builder.previousPosition;
		this.occupiedPosition = builder.currentPosition;

	}
	
	public UUID getPlateauId() {
		return plateauId;
	}

	public TwoDimensionalCoordinates getPreviousPosition() {
		return releasedPosition;
	}

	public TwoDimensionalCoordinates getCurrentPosition() {
		return occupiedPosition;
	}

	public static class Builder {
		
		private UUID plateauId;

		private TwoDimensionalCoordinates previousPosition, currentPosition;

		public Builder withPlateauId(UUID plateauId) {
			   this.plateauId = plateauId;
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

		public PlateauSwitchedLocationEvent build() {
			return new PlateauSwitchedLocationEvent(this);
		}

	}
	
	@Override
	public String toString() {
		return String.format("PlateauSwitchedLocationEvent published with plateau id [%s], position released [%s], position occupied [%s]" , plateauId, releasedPosition, occupiedPosition);
	}


}
