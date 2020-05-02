package com.game.domain.model.entity.dimensions;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.rover.Orientation;

public class TwoDimensionalCoordinatesTest {

	@Test
	public void testCoordinatesConstructor() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(3, 4);
		assertThat(coordinates.getAbscissa()).isEqualTo(3);
		assertThat(coordinates.getOrdinate()).isEqualTo(4);
	}

	@Test
	public void testCoordinatesEquals() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(3, 4);
		TwoDimensionalCoordinates otherCoordinates = new TwoDimensionalCoordinates(3, 4);
		assertThat(coordinates).isEqualTo(otherCoordinates);
	}

	@Test
	public void testCoordinatesEqualsSameInstance() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(3, 4);
		assertThat(coordinates).isEqualTo(coordinates);
	}

	@Test
	public void testCoordinatesNotEqualsEquals() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(3, 4);
		Orientation orientation = Orientation.EAST;
		assertThat(coordinates).isNotEqualTo(orientation);
	}

	@Test
	public void testCoordinatesHashCode() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(3, 4);
		TwoDimensionalCoordinates otherCoordinates = new TwoDimensionalCoordinates(3, 4);
		assertThat(coordinates.hashCode()).isEqualTo(otherCoordinates.hashCode());
	}

	@Test
	public void testGetCoordinates() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(3, 4);
		assertThat(coordinates.getCoordinates()).containsExactly(3, 4);
	}

	@Test
	public void testCoordinatesWidthAndHeight() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(3, 4);
		assertThat(coordinates.getAbscissa()).isEqualTo(3);
		assertThat(coordinates.getOrdinate()).isEqualTo(4);
	}

	@Test
	public void testToString() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(3, 4);
		assertThat(coordinates.toString()).isEqualTo("Coordinates [abscissa = 3, ordinate = 4]");
	}

}
