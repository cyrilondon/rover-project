package com.game.domain.application.command;

import java.util.UUID;

/**
 * Command sent from the client to move a Rover with given name
 * by a certain number of moves
 *
 */
public class MoveRoverCommand {

	public UUID plateauUuid;

	public String roverName;

	public int numberOfMoves;
	
	public MoveRoverCommand(UUID plateauUUID, String roverName, int numberOfMoves) {
		this.plateauUuid = plateauUUID;
		this.roverName = roverName;
		this.numberOfMoves = numberOfMoves;
	}

	public String getRoverName() {
		return roverName;
	}

	public int getNumberOfMoves() {
		return numberOfMoves;
	}
	
	public UUID getPlateauUuid() {
		return plateauUuid;
	}

}
