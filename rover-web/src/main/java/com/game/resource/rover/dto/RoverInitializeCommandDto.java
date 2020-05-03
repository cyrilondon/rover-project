package com.game.resource.rover.dto;

import java.util.UUID;

public class RoverInitializeCommandDto {
	
	public RoverInitializeCommandDto() {}
	
	String name;

	private int abscissa, ordinate;

	String orientation;

	UUID plateauUuid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAbscissa() {
		return abscissa;
	}

	public void setAbscissa(int abscissa) {
		this.abscissa = abscissa;
	}

	public int getOrdinate() {
		return ordinate;
	}

	public void setOrdinate(int ordinate) {
		this.ordinate = ordinate;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public UUID getPlateauUuid() {
		return plateauUuid;
	}

	public void setPlateauUuid(UUID plateauUuid) {
		this.plateauUuid = plateauUuid;
	}

}
