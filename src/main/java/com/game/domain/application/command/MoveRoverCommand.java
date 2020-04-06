package com.game.domain.application.command;

/**
 * Command sent from the client to move a Rover with given name
 * by a certain number of moves
 *
 */
public class MoveRoverCommand {

	public String roverName;

	public int numberOfMoves;
	
	public MoveRoverCommand(String roverName, int numberOfMoves) {
		this.roverName = roverName;
		this.numberOfMoves = numberOfMoves;
	}

	public String getRoverName() {
		return roverName;
	}

	public int getNumberOfMoves() {
		return numberOfMoves;
	}

}
