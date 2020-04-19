package com.game.domain.application.command.plateau;

import java.util.UUID;

import com.game.domain.application.CommandVisitor;
import com.game.domain.application.command.ApplicationCommand;

public class PlateauInitializeCommand implements ApplicationCommand {

	private UUID plateauId;

	private int abscissa, ordinate;

	private int observerSpeed;

	private PlateauInitializeCommand(Builder builder) {
		this.plateauId = builder.plateauId;
		this.abscissa = builder.abscissa;
		this.ordinate = builder.ordinate;
		this.observerSpeed = builder.observerSpeed;
	}

	public static class Builder {

		UUID plateauId;

		private int abscissa, ordinate;

		private int observerSpeed;

		public Builder withId(UUID uuid) {
			this.plateauId = uuid;
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
		return plateauId;
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
