package com.game.domain.application.command;

/**
 * Initializes a rover with coordinates and orientation
 *
 */
public class InitializeRoverCommand {
	
	private int abscissa, ordinate;
	
	String orientation;
	
	public InitializeRoverCommand(int abscissa, int ordinate, String orientation) {
		this.abscissa = abscissa;
		this.ordinate = ordinate;
		this.orientation = orientation;
	}

	public int getAbscissa() {
		return abscissa;
	}

	public int getOrdinate() {
		return ordinate;
	}

	public String getOrientation() {
		return orientation;
	}


	

}
