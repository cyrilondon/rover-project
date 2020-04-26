package com.game.domain.model.event.plateau;

import java.util.UUID;

import com.game.domain.model.entity.dimensions.TwoDimensionalSpace;
import com.game.domain.model.event.BaseDomainEvent;

public class PlateauInitializedEvent extends BaseDomainEvent {

	private UUID plateauId;
	
	private TwoDimensionalSpace dimensions;
	
	
	private PlateauInitializedEvent(Builder builder) {
		this.plateauId = builder.plateauId;
		this.dimensions = builder.dimensions;
	}
	
	
	public static class Builder {

		private UUID plateauId;
		
		private TwoDimensionalSpace dimensions;
		
		int observerSpeed;
	
		public Builder withPlateauId(UUID plateauId) {
			this.plateauId = plateauId;
			return this;
		}

		public Builder withDimensions(TwoDimensionalSpace dimensions) {
			this.dimensions = dimensions;
			return this;
		}

		public PlateauInitializedEvent build() {
			return new PlateauInitializedEvent(this);
		}

	}
	
	public UUID getPlateauId() {
		return plateauId;
	}

	public TwoDimensionalSpace getDimensions() {
		return dimensions;
	}

	
	@Override
	public String toString() {
		return String.format("PlateauInitializedEvent published at [%s] with plateau id [%s], dimensions [%s]" , super.occuredOn(), plateauId, dimensions);
	}

}
