package com.game.resource.rover.dto;

import java.util.UUID;

public class RoverTurnCommandDto {
	
	String name;
	
	UUID plateauUuid;
	
	String turn;

	/**
	 * Empty constructor as required by JAX-RS
	 */
	public RoverTurnCommandDto() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getPlateauUuid() {
		return plateauUuid;
	}

	public void setPlateauUuid(UUID plateauUuid) {
		this.plateauUuid = plateauUuid;
	}
	
	public String getTurn() {
		return turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}

}
