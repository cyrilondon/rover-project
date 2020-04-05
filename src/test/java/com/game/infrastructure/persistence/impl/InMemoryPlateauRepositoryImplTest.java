package com.game.infrastructure.persistence.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.game.domain.model.entity.Plateau;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.repository.PlateauRepository;

public class InMemoryPlateauRepositoryImplTest {
	
	private final static int WIDTH = 5;

	private final static int HEIGHT = 5;
	
	PlateauRepository repository = new InMemoryPlateauRepositoryImpl();
	
	
	@Test
	public void testAddPlateau() {
		Plateau plateau = new Plateau(new TwoDimensions(new TwoDimensionalCoordinates(WIDTH, HEIGHT)));
		repository.addPlateau(plateau);
		assertThat(repository.getPlateau()).isEqualTo(plateau);
	}

}
