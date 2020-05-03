package com.game.resource.rover.dto;

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
