package com.game.domain.application.command;

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
