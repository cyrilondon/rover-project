package com.game.domain.model.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

public class CoordinatesTest {
	
	@Test
	public void testCoordinatesConstructor() {
		Coordinates coordinates = new Coordinates(3, 4);
		assertThat(coordinates.getAbscissa()).isEqualTo(3);
		assertThat(coordinates.getOrdinate()).isEqualTo(4);
	}
	
	@Test
	public void testCoordinatesEquals() {
		Coordinates coordinates = new Coordinates(3, 4);
		Coordinates otherCoordinates = new Coordinates(3, 4);
		assertThat(coordinates).isEqualTo(otherCoordinates);
	}
	
	@Test
	public void testCoordinatesNotEqualsEquals() {
		Coordinates coordinates = new Coordinates(3, 4);
		Orientation orientation = Orientation.EAST;
		assertThat(coordinates).isNotEqualTo(orientation);
	}
	
	@Test
	public void testCoordinatesHashCode() {
		Coordinates coordinates = new Coordinates(3, 4);
		Coordinates otherCoordinates = new Coordinates(3, 4);
		assertThat(coordinates.hashCode()).isEqualTo(otherCoordinates.hashCode());
	}
	
	
	@Test
	public void testGetCoordinates() {
		Coordinates coordinates = new Coordinates(3, 4);
		assertThat(coordinates.getCoordinates()).containsExactly(3,4);
	}
	
	@Test
	public void testAnyShiftOnCoordinates() {
		Coordinates coordinates = new Coordinates(3, 4);
		coordinates.shiftMinusOneAlongAbscissa();
		coordinates.shiftMinusOneAlongOrdinate();
		assertThat(coordinates.getAbscissa()).isEqualTo(2);
		assertThat(coordinates.getOrdinate()).isEqualTo(3);
	}
	
	@Test
	public void testToString() {
		Coordinates coordinates = new Coordinates(3, 4);
		assertThat(coordinates.toString()).isEqualTo("Coordinates [abscissa = 3, ordinate = 4]");
	}

}
