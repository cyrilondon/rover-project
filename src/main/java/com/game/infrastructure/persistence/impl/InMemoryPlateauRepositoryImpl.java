package com.game.infrastructure.persistence.impl;

import com.game.domain.model.entity.Plateau;
import com.game.domain.model.repository.PlateauRepository;

/**
 * "Secondary" port interface as described by Alistair CockBurn in his
 * original paper, i.e. port on the right side of the hexagon.
 * https://alistair.cockburn.us/hexagonal-architecture/
 * Located in a infrastructure package and implements a model interface
 * {@link PlateauRepository}
 */
public class InMemoryPlateauRepositoryImpl implements PlateauRepository {

	Plateau plateau;

	@Override
	public Plateau getPlateau() {
		return plateau;
	}
	
	@Override
	public void addPlateau(Plateau plateau) {
		this.plateau = plateau;
	}
	
	

}
