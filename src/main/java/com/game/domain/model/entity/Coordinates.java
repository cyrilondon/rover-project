package com.game.domain.model.entity;

import java.util.Objects;

public class Coordinates {

	private int abscissa, ordinate;

	public Coordinates(int x, int y) {
		this.abscissa = x;
		this.ordinate = y;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj == this) {
			return true;
		}
		
		if (obj instanceof Coordinates) {
			Coordinates other = (Coordinates)obj;
			return Objects.equals(abscissa, other.getAbscissa()) && Objects.equals(ordinate, other.getOrdinate());
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getAbscissa(), getOrdinate());
	}
	
	public int[] getCoordinates() {
		return new int[] {getAbscissa(), getOrdinate()};
	}
	
	

	public int getAbscissa() {
		return abscissa;
	}

	public int getOrdinate() {
		return ordinate;
	}
	
	@Override
	public String toString() {
		return String.format("Coordinates [abscissa = %s, ordinate = %s]", getAbscissa(), getOrdinate());
	}

}
