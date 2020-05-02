package com.game.infrastructure.persistence.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.testng.annotations.Test;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.entity.plateau.Plateau;
import com.game.domain.model.repository.PlateauRepository;

public class InMemoryPlateauRepositoryImplTest {
	
	private final static int WIDTH = 5;

	private final static int HEIGHT = 5;
	
	PlateauRepository repository = new InMemoryPlateauRepositoryImpl();
	
	
	@Test
	public void testAddPlateau() {
		UUID uuid = UUID.randomUUID();
		Plateau plateau = new Plateau(uuid, new TwoDimensions(new TwoDimensionalCoordinates(WIDTH, HEIGHT)));
		repository.add(plateau);
		assertThat(repository.load(uuid)).isEqualTo(plateau);
	}

}
