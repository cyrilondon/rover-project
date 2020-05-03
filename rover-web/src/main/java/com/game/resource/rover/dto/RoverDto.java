package com.game.resource.rover.dto;

/**
 * Rover DTO sent back to the user.
 * We use a DTO as we do NOT want the Model to 'leak' to the outside world
 *
 */
public class RoverDto {
	
	String name;

	private int abscissa, ordinate;

	String orientation;

	String plateauUuid;
	
	public RoverDto(String name, String plateauUuid, String orientation, int abscissa, int ordinate) {
		this.name = name;
		this.plateauUuid = plateauUuid;
		this.orientation = orientation;
		this.abscissa = abscissa;
		this.ordinate = ordinate;
	}

	public String getName() {
		return name;
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

	public String getPlateauUuid() {
		return plateauUuid;
	}

}
