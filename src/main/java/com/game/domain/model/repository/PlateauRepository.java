package com.game.domain.model.repository;

import java.util.UUID;

import com.game.domain.model.entity.Plateau;

/**
 * "Secondary" port interface as described by Alistair CockBurn in his original
 * paper, i.e. port on the right side of the hexagon.
 * https://alistair.cockburn.us/hexagonal-architecture/ 
 * Located in the model layer
 * Implemented by the secondary port adapter {@link InMemoryPlateauRepositoryImpl} located in infrastructure package/module
 */
public interface PlateauRepository extends DomainRepository<Plateau, UUID> {
	

}
