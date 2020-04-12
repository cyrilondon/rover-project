package com.game.domain.model.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.UUID;

import org.testng.annotations.Test;

import com.game.domain.model.entity.Plateau;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.exception.EntityInitialisationException;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.repository.PlateauRepository;
import com.game.infrastructure.persistence.impl.InMemoryRoverRepositoryImpl;

public class PlateauServiceImplTest {

	private final static int WIDTH = 5;

	private final static int HEIGHT = 5;

	private final static int NEGATIVE_WIDTH = -WIDTH;

	private static final int OBSERVER_SPEED = Math.multiplyExact(2, (int) Math.pow(10, 7));

	PlateauRepository plateauRepository = new MockPlateauRepositoryImpl();

	PlateauServiceImpl plateauService = new PlateauServiceImpl(plateauRepository);

	@Test
	public void testInitializePlateau() {
		Plateau plateau = plateauService.initializePlateau(UUID.randomUUID(), new TwoDimensionalCoordinates(WIDTH, HEIGHT));
		assertThat(plateau.getWidth()).isEqualTo(WIDTH);
		assertThat(plateau.getWidth()).isEqualTo(HEIGHT);
	}

	@Test
	public void testInitializeRelativisticPlateau() {
		Plateau plateau = plateauService.initializeRelativisticPlateau(UUID.randomUUID(), OBSERVER_SPEED,
				(new TwoDimensionalCoordinates(WIDTH, HEIGHT)));
		assertThat(plateau.getWidth()).isEqualTo(WIDTH - 1);
		assertThat(plateau.getWidth()).isEqualTo(HEIGHT - 1);
	}

	@Test
	public void testInitializePlateauNegativeWidth() {
		Throwable thrown = catchThrowable(
				() -> plateauService.initializePlateau(UUID.randomUUID(), (new TwoDimensionalCoordinates(NEGATIVE_WIDTH, HEIGHT))));
		assertThat(thrown).isInstanceOf(EntityInitialisationException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ENTITY_VALIDATION_ERROR_CODE,
						String.format(GameExceptionLabels.PLATEAU_NEGATIVE_WIDTH, NEGATIVE_WIDTH)));
	}

	@Test
	public void testInitializeRelativisticPlateauNegativeDimensions() {
		Throwable thrown = catchThrowable(() -> plateauService.initializeRelativisticPlateau(UUID.randomUUID(), OBSERVER_SPEED,
				(new TwoDimensionalCoordinates(NEGATIVE_WIDTH, HEIGHT))));
		assertThat(thrown).isInstanceOf(EntityInitialisationException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ENTITY_VALIDATION_ERROR_CODE,
						String.format(GameExceptionLabels.PLATEAU_NEGATIVE_WIDTH, NEGATIVE_WIDTH + 1)));
	}

	@Test
	public void testMarkLocationBusy() {
		UUID uuid = UUID.randomUUID();
		plateauRepository.add(getPlateau(uuid));
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(3, 4);
		plateauService.updatePlateauWithBusyLocation(uuid, coordinates);
		assertThat(plateauService.isLocationBusy(uuid, coordinates)).isTrue();
	}

	private Plateau getPlateau(UUID uuid) {
		return new Plateau(uuid, new TwoDimensions(new TwoDimensionalCoordinates(WIDTH, HEIGHT))).initializeLocations();
	}

	/**
	 * Simple MockClass for the RoverRepository We explicitly choose a different
	 * implementation than the {@link InMemoryRoverRepositoryImpl}
	 *
	 */
	private class MockPlateauRepositoryImpl implements PlateauRepository {

		Plateau plateau;


		@Override
		public Plateau load(UUID id) {
			return plateau;
		}

		@Override
		public void add(Plateau plateau) {
			this.plateau = plateau;
		}

		@Override
		public void update(Plateau plateau) {
			this.plateau = plateau;
		}

		@Override
		public void remove(UUID id) {
			this.plateau = null;
			
		}

	}

}
