package com.game.domain.application.command;

import java.util.UUID;

public class MakeTurnRoverCommand {
	
	String name;
	
	char turn;
	
	UUID plateauUuid;
	
	public MakeTurnRoverCommand(UUID plateauUUID, String name, char orientation) {
		this.plateauUuid = plateauUUID;
		this.name = name;
		this.turn = orientation;
	}

	public String getName() {
		return name;
	}

	public char getTurn() {
		return turn;
	}

	public UUID getPlateauUuid() {
		return plateauUuid;
	}

}
