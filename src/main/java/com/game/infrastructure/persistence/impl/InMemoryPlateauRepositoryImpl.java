package com.game.infrastructure.persistence.impl;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.game.domain.model.entity.plateau.Plateau;
import com.game.domain.model.exception.PlateauNotFoundException;
import com.game.domain.model.repository.PlateauRepository;

/**
 * "Secondary" port interface as described by Alistair CockBurn in his original
 * paper, i.e. port on the right side of the hexagon.
 * https://alistair.cockburn.us/hexagonal-architecture/ Located in a
 * infrastructure package and implements a model interface
 * {@link PlateauRepository}
 */
public class InMemoryPlateauRepositoryImpl implements PlateauRepository {

	Map<UUID, Plateau> plateaux = new ConcurrentHashMap<>();

	@Override
	public void remove(UUID id) {
		plateaux.remove(id);
	}

	@Override
	public Plateau load(UUID id) {
		if (plateaux.get(id) == null) {
			throw new PlateauNotFoundException(id);
		}
		return plateaux.get(id);
	}

	@Override
	public void add(Plateau plateau) {
		plateaux.putIfAbsent(plateau.getId(), plateau);
	}

	@Override
	public void update(Plateau plateau) {
		plateaux.put(plateau.getId(), plateau);
	}

}
