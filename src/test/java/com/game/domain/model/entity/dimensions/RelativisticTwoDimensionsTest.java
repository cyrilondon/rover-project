package com.game.domain.model.entity.dimensions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.testng.annotations.Test;

import com.game.domain.model.entity.plateau.Plateau;

public class RelativisticTwoDimensionsTest {

	/**
	 * Speed of 2x10^5 m/s => contraction from 50 to 49 That's special relativity..
	 */
	private static final int OBSERVER_SPEED = Math.multiplyExact(2, (int) Math.pow(10, 5));

	int initial_coordinate = 50;

	@Test
	public void testWidthAndLengthContraction() {
		TwoDimensionalSpace dimensions = new RelativisticTwoDimensions(OBSERVER_SPEED,
				new TwoDimensions(new TwoDimensionalCoordinates(initial_coordinate, initial_coordinate)));
		Plateau plateau = new Plateau(UUID.randomUUID(), dimensions);
		assertThat(plateau.getWidth()).isEqualTo(initial_coordinate -1);
		assertThat(plateau.getHeight()).isEqualTo(initial_coordinate -1);
	}

}
