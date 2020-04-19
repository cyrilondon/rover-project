package com.game.domain.application.command.rover;

import java.util.UUID;

import com.game.domain.application.command.ApplicationCommand;
import com.game.domain.application.service.GameServiceCommandVisitor;

/**
 * Initializes a rover with coordinates and orientation
 *
 */
public class RoverInitializeCommand implements ApplicationCommand {

	String name;

	private int abscissa, ordinate;

	char orientation;

	UUID plateauUuid;

	private RoverInitializeCommand(Builder builder) {
		this.name = builder.name;
		this.plateauUuid = builder.plateauUuid;
		this.abscissa = builder.abscissa;
		this.ordinate = builder.ordinate;
		this.orientation = builder.orientation;
	}

	public String getName() {
		return name;
	}

	public UUID getPlateauUuid() {
		return plateauUuid;
	}

	public int getAbscissa() {
		return abscissa;
	}

	public int getOrdinate() {
		return ordinate;
	}

	public char getOrientation() {
		return orientation;
	}

	public static class Builder {

		String name;

		private int abscissa, ordinate;

		char orientation;

		UUID plateauUuid;

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withPlateauUuid(UUID uuid) {
			this.plateauUuid = uuid;
			return this;
		}

		public Builder withAbscissa(int x) {
			this.abscissa = x;
			return this;
		}

		public Builder withOrdinate(int y) {
			this.ordinate = y;
			return this;
		}

		public Builder withOrientation(char orientation) {
			this.orientation = orientation;
			return this;
		}

		public RoverInitializeCommand build() {
			return new RoverInitializeCommand(this);
		}

	}

	@Override
	public void acceptVisitor(GameServiceCommandVisitor visitor) {
		visitor.visit(this);
	}

}
