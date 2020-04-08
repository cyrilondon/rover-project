package com.game.infrastructure.persistence.impl;

import java.util.UUID;

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
	public void remove(UUID id) {
		this.plateau = null;
	}

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
	
}
