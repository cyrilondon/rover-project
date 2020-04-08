package com.game.domain.application.command;

import java.util.UUID;

/**
 * Initializes a rover with coordinates and orientation
 *
 */
public class InitializeRoverCommand {

	String name;
	
	private int abscissa, ordinate;
	
	char orientation;
	
	UUID plateauUuid;
	
	private InitializeRoverCommand(Builder builder) {
		this.name = builder.name;
		this.plateauUuid = builder.plateauUuid;
		this.abscissa = builder.abscissa;
		this.ordinate = builder.ordinate;
		this.orientation = builder.orientation;
	}

	public String getName() {
		return name;
	}
	
	public UUID getPlateauUuid() {
		return plateauUuid;
	}

	public int getAbscissa() {
		return abscissa;
	}

	public int getOrdinate() {
		return ordinate;
	}

	public char getOrientation() {
		return orientation;
	}

   public static class Builder{
	   
	   String name;
	   
	   private int abscissa, ordinate;
		
		char orientation;
		
		UUID plateauUuid;
		
		public Builder withName(String name) {
			this.name = name;
			return this;
		}
		
		public Builder withPlateauUuid(UUID uuid) {
			this.plateauUuid = uuid;
			return this;
		}
		
		public Builder withAbscissa(int x) {
			this.abscissa = x;
			return this;
		}
		
		public Builder withOrdinate(int y) {
			this.ordinate = y;
			return this;
		}
		
		public Builder withOrientation(char orientation) {
			this.orientation = orientation;
			return this;
		}
		
		public InitializeRoverCommand build() {
			return new InitializeRoverCommand(this);
		}   
	   
   }
	

}
