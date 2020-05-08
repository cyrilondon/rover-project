package com.game.resource.rover.dto;

import java.util.UUID;

public class RoverMoveCommandDto {

	String name;

	UUID plateauUuid;

	int moves;
	
	/**
	 * Empty constructor as required by JAX-RS
	 */
	public RoverMoveCommandDto() {
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

	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}


}
