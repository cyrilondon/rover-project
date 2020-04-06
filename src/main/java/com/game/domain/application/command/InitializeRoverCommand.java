package com.game.domain.application.command;

/**
 * Initializes a rover with coordinates and orientation
 *
 */
public class InitializeRoverCommand {
	
	private int abscissa, ordinate;
	
	char orientation;
	
	private InitializeRoverCommand(Builder builder) {
		this.abscissa = builder.abscissa;
		this.ordinate = builder.ordinate;
		this.orientation = builder.orientation;
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
	   
	   private int abscissa, ordinate;
		
		char orientation;
		
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
