package com.game.domain.application.command;

import java.util.UUID;

import com.game.domain.application.CommandVisitor;

public class PlateauInitializeCommand implements ApplicationCommand {

	private UUID plateauUuid;

	private int abscissa, ordinate;

	private int observerSpeed;

	private PlateauInitializeCommand(Builder builder) {
		this.plateauUuid = builder.plateauUuid;
		this.abscissa = builder.abscissa;
		this.ordinate = builder.ordinate;
		this.observerSpeed = builder.observerSpeed;
	}

	public static class Builder {

		UUID plateauUuid;

		private int abscissa, ordinate;

		private int observerSpeed;

		public Builder withUuid(UUID uuid) {
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

		public Builder withObserverSpeed(int speed) {
			this.observerSpeed = speed;
			return this;
		}

		public PlateauInitializeCommand build() {
			return new PlateauInitializeCommand(this);
		}

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

	public int getObserverSpeed() {
		return observerSpeed;
	}

	@Override
	public void acceptVisitor(CommandVisitor visitor) {
		visitor.visit(this);
	}

}
