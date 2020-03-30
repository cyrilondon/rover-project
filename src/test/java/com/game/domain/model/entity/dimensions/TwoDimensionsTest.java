package com.game.domain.model.entity.dimensions;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.game.domain.model.entity.Board;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;

public class TwoDimensionsTest {
	
	@Test
	public void testDimensions() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(3, 5);
		TwoDimensions dimensions = new TwoDimensions(coordinates);
		Board board = new Board(dimensions);
		assertThat(board.getWidth()).isEqualTo(coordinates.getAbscissa());
		assertThat(board.getHeight()).isEqualTo(coordinates.getOrdinate());
	}

}
