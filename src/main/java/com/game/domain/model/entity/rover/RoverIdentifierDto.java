package com.game.domain.model.entity.rover;

public class RoverIdentifierDto {
	
	private RoverIdentifier id;
	
	private int version;
	
	public RoverIdentifierDto(RoverIdentifier id, int version) {
		this.id = id;
		this.version = version;
	}

	public RoverIdentifier getId() {
		return id;
	}

	public int getVersion() {
		return version;
	}

}
