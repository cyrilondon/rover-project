package com.game.domain.model;

import java.util.UUID;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

public class RoverMovedEvent {
	
	private String roverName;
	
	private UUID plateauUuid;
	
	TwoDimensionalCoordinates previousPosition;
	
	TwoDimensionalCoordinates currentPosition;
	
	private RoverMovedEvent(Builder builder) {
		this.roverName = builder.roverName;
		this.previousPosition = builder.previousPosition;
		this.currentPosition = builder.currentPosition;
		this.plateauUuid = builder.plateauUuid;
	}
	
	 public UUID getPlateauUuid() {
		return plateauUuid;
	}

	public String getRoverName() {
			return roverName;
		}

		public TwoDimensionalCoordinates getPreviousPosition() {
			return previousPosition;
		}

		public TwoDimensionalCoordinates getCurrentPosition() {
			return currentPosition;
		}

	public static class Builder{

		   private  TwoDimensionalCoordinates  previousPosition, currentPosition;

		   private String roverName;
		   
		   private UUID plateauUuid;

			public Builder withPreviousPosition(TwoDimensionalCoordinates  previousPosition) {
				this.previousPosition = previousPosition;
				return this;
			}

			public Builder withCurrentPosition(TwoDimensionalCoordinates  currentPosition) {
				this.currentPosition = currentPosition;
				return this;
			}

			public Builder withRoverName(String roverName) {
				this.roverName = roverName;
				return this;
			}
			
			public Builder withPlateauUuid(UUID uuid) {
				this.plateauUuid = uuid;
				return this;
			}

			public RoverMovedEvent build() {
				return new RoverMovedEvent(this);
			}   

	   }

}
