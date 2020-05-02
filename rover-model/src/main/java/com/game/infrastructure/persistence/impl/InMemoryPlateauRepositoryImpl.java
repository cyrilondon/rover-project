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

	Map<UUID, Plateau> plateaus = new ConcurrentHashMap<>();

	@Override
	public void remove(UUID id) {
		plateaus.remove(id);
	}

	@Override
	public Plateau load(UUID id) {
		if (plateaus.get(id) == null) {
			throw new PlateauNotFoundException(id);
		}
		return plateaus.get(id);
	}

	@Override
	public void add(Plateau plateau) {
		plateaus.putIfAbsent(plateau.getId(), plateau);
	}

	@Override
	public void update(Plateau plateau) {
		plateaus.put(plateau.getId(), plateau);
	}

}
