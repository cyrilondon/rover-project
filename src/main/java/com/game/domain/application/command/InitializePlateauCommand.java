package com.game.domain.application.command;

import java.util.UUID;

public class InitializePlateauCommand {
	
	private UUID plateauUuid;

	private int abscissa, ordinate;
	
	private InitializePlateauCommand(Builder builder) {
		this.plateauUuid = builder.plateauUuid;
		this.abscissa = builder.abscissa;
		this.ordinate = builder.ordinate;
	}

	public static class Builder {

		UUID plateauUuid;

		private int abscissa, ordinate;

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


		public InitializePlateauCommand build() {
			return new InitializePlateauCommand(this);
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

}
