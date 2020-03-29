package com.game.domain.model.entity;

import java.util.Objects;

public class Coordinates {

	private int absciss, ordinate;

	public Coordinates(int x, int y) {
		this.absciss = x;
		this.ordinate = y;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj == this) {
			return true;
		}
		
		if (obj instanceof Coordinates) {
			Coordinates other = (Coordinates)obj;
			return Objects.equals(absciss, other.getAbsciss()) && Objects.equals(ordinate, other.getOrdinate());
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getAbsciss(), getOrdinate());
	}
	
	public int[] getCoordinates() {
		return new int[] {getAbsciss(), getOrdinate()};
	}
	
	

	public int getAbsciss() {
		return absciss;
	}

	public int getOrdinate() {
		return ordinate;
	}
	
	@Override
	public String toString() {
		return String.format("Coordinates [absciss = %s, ordinate = %s]", getAbsciss(), getOrdinate());
	}

}
