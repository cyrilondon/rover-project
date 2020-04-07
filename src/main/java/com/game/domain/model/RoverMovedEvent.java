package com.game.domain.model;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

public class RoverMovedEvent {
	
	private String roverName;
	
	TwoDimensionalCoordinates previousPosition;
	
	TwoDimensionalCoordinates currentPosition;
	
	private RoverMovedEvent(Builder builder) {
		this.roverName = builder.roverName;
		this.previousPosition = builder.previousPosition;
		this.currentPosition = builder.currentPosition;
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

			public RoverMovedEvent build() {
				return new RoverMovedEvent(this);
			}   

	   }

}
