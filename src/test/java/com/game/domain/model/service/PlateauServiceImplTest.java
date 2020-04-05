package com.game.domain.model.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.testng.annotations.Test;

import com.game.domain.model.entity.Plateau;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.exception.EntityValidationException;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.infrastructure.persistence.impl.InMemoryPlateauRepositoryImpl;

public class PlateauServiceImplTest {

	private final static int WIDTH = 5;

	private final static int HEIGHT = 5;
	
	private final static int NEGATIVE_WIDTH = - WIDTH;

	private static final int OBSERVER_SPEED = Math.multiplyExact(2, (int) Math.pow(10, 7));

	PlateauServiceImpl plateauService = new PlateauServiceImpl(new InMemoryPlateauRepositoryImpl());

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
	
	@Test
	public void testInitializePlateauNegativeWidth() {
		Throwable thrown = catchThrowable(() -> plateauService.initializePlateau(
				(new TwoDimensionalCoordinates(NEGATIVE_WIDTH, HEIGHT))));
		assertThat(thrown).isInstanceOf(EntityValidationException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ENTITY_VALIDATION_ERROR_CODE,
						String.format(GameExceptionLabels.PLATEAU_NEGATIVE_WIDTH, NEGATIVE_WIDTH)));
	}
	
	@Test
	public void testInitializeRelativisticPlateauNegativeDimensions() {
		Throwable thrown = catchThrowable(() -> plateauService.initializeRelativisticPlateau(OBSERVER_SPEED,
				(new TwoDimensionalCoordinates(NEGATIVE_WIDTH, HEIGHT))));
		assertThat(thrown).isInstanceOf(EntityValidationException.class)
		.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
				GameExceptionLabels.ENTITY_VALIDATION_ERROR_CODE,
				String.format(GameExceptionLabels.PLATEAU_NEGATIVE_WIDTH, NEGATIVE_WIDTH +1)));
	}
	
	

}
