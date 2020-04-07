package com.game.domain.model.entity.dimensions;

import java.util.Objects;

public class TwoDimensionalCoordinates {

	private int abscissa, ordinate;

	public TwoDimensionalCoordinates(int x, int y) {
		this.abscissa = x;
		this.ordinate = y;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (obj instanceof TwoDimensionalCoordinates) {
			TwoDimensionalCoordinates other = (TwoDimensionalCoordinates) obj;
			return Objects.equals(abscissa, other.getAbscissa()) && Objects.equals(ordinate, other.getOrdinate());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAbscissa(), getOrdinate());
	}

	public int[] getCoordinates() {
		return new int[] { getAbscissa(), getOrdinate() };
	}

	public int getAbscissa() {
		return abscissa;
	}

	public int getOrdinate() {
		return ordinate;
	}

	/**
	 * We don't mutate the state, i.e. functional behaviour
	 * @param step
	 * @return new coordinates
	 */
	public TwoDimensionalCoordinates shiftAlongAbscissa(int step) {
		return new TwoDimensionalCoordinates(abscissa + step, ordinate);
	}

	/**
	 * We don't mutate the state, i.e. functional behaviour
	 * @param step
	 * @return new coordinates
	 */
	public TwoDimensionalCoordinates shiftAlongOrdinate(int step) {
		return new TwoDimensionalCoordinates(abscissa, ordinate + step);
	}
	
	@Override
	public String toString() {
		return String.format("Coordinates [abscissa = %s, ordinate = %s]", getAbscissa(), getOrdinate());
	}

}
