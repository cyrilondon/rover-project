package com.game.infrastructure.persistence.impl;

import com.game.domain.model.entity.Plateau;
import com.game.domain.model.repository.PlateauRepository;

/**
 * Not really needed as it is for now.
 * But here in case of other entities/value objects to configure
 *
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
