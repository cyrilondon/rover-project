package com.game.domain.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.game.domain.model.entity.Plateau;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

public class PlateauServiceImplTest {

	private final static int WIDTH = 5;

	private final static int HEIGHT = 5;

	private static final int OBSERVER_SPEED = Math.multiplyExact(2, (int) Math.pow(10, 7));

	PlateauServiceImpl plateauService = new PlateauServiceImpl();

	@Test
	public void testInitializePlateau() {
		Plateau plateau = plateauService.initializePlateau(new TwoDimensionalCoordinates(WIDTH, HEIGHT));
		assertThat(plateau.getWidth()).isEqualTo(WIDTH);
		assertThat(plateau.getWidth()).isEqualTo(HEIGHT);
	}

	@Test
	public void testInitializeRelativisticPlateau() {
		Plateau plateau = plateauService.initializeRelativisticPlateau(OBSERVER_SPEED,
				(new TwoDimensionalCoordinates(WIDTH, HEIGHT)));
		assertThat(plateau.getWidth()).isEqualTo(WIDTH - 1);
		assertThat(plateau.getWidth()).isEqualTo(HEIGHT - 1);
	}

}
