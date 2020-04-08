package com.game.domain.model.entity.dimensions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.testng.annotations.Test;

import com.game.domain.model.entity.Plateau;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;

public class TwoDimensionsTest {
	
	@Test
	public void testDimensions() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(3, 5);
		TwoDimensions dimensions = new TwoDimensions(coordinates);
		Plateau plateau = new Plateau(UUID.randomUUID(), dimensions);
		assertThat(plateau.getWidth()).isEqualTo(coordinates.getAbscissa());
		assertThat(plateau.getHeight()).isEqualTo(coordinates.getOrdinate());
	}

}
