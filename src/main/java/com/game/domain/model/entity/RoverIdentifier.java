package com.game.domain.model.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import com.game.core.validation.ArgumentCheck;
import com.game.domain.model.exception.GameExceptionLabels;

/**
 * Rover identifier which includes Plateau UUID + Rover name
 * This combination identifies the Rover with unicity
 *
 */
public class RoverIdentifier implements Serializable {
	
	private static final long serialVersionUID = -136210258558861060L;
	
	/**
	 * Many-to-one association to a Plateau instance
	 * We keep track of the plateau UUID
	 */
	private UUID plateauUuid;
	
	private String name;
	
	public RoverIdentifier(UUID plateauUuid, String name) {
		this.plateauUuid = ArgumentCheck.preNotNull(plateauUuid, GameExceptionLabels.MISSING_PLATEAU_UUID);
		this.name = ArgumentCheck.preNotEmpty(name, GameExceptionLabels.MISSING_ROVER_NAME);
	}

	public UUID getPlateauUuid() {
		return plateauUuid;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (obj instanceof RoverIdentifier) {
			RoverIdentifier other = (RoverIdentifier) obj;
			return Objects.equals(plateauUuid, other.getPlateauUuid()) && Objects.equals(name, other.getName());
		}

		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getPlateauUuid(), getName());
	}

}
