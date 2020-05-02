package com.game.domain.model.entity.rover;

import java.util.Objects;

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
	
	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (obj instanceof RoverIdentifierDto) {
			RoverIdentifierDto other = (RoverIdentifierDto) obj;
			return Objects.equals(id, other.getId()) && Objects.equals(getVersion(), other.getVersion());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getVersion());
	}

	@Override
	public String toString() {
		return String.format("Rover [%s] attached to Plateau [%s] with version [%s]", this.getId().getName(),
				this.getId().getPlateauId(), this.getVersion());
	}

}
