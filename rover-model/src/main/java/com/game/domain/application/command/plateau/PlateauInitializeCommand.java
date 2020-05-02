package com.game.domain.application.command.plateau;

import java.util.UUID;

import com.game.domain.application.command.ApplicationCommand;
import com.game.domain.application.service.GameServiceCommandVisitor;

public class PlateauInitializeCommand implements ApplicationCommand {

	private UUID plateauId;

	private int width, height;

	private int observerSpeed;

	private PlateauInitializeCommand(Builder builder) {
		this.plateauId = builder.plateauId;
		this.width = builder.abscissa;
		this.height = builder.ordinate;
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

		public Builder withWidth(int x) {
			this.abscissa = x;
			return this;
		}

		public Builder withHeight(int y) {
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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getObserverSpeed() {
		return observerSpeed;
	}

	@Override
	public void acceptVisitor(GameServiceCommandVisitor visitor) {
		visitor.visit(this);
	}

}
