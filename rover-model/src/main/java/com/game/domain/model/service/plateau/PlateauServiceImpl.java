package com.game.domain.model.service.plateau;

import java.util.UUID;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.plateau.Plateau;
import com.game.domain.model.event.plateau.PlateauInitializedEvent;
import com.game.domain.model.repository.PlateauRepository;

/**
 * Domain service which has the responsibility to handle the entity
 * {@link Plateau}
 *
 */
public class PlateauServiceImpl implements PlateauService {

	PlateauRepository plateauRepository;

	public PlateauServiceImpl(PlateauRepository plateauRepository) {
		this.plateauRepository = plateauRepository;
	}

	@Override
	public Plateau initializePlateau(UUID uuid, TwoDimensionalCoordinates dimensions, int speed) {
		
		Plateau plateau = PlateauFactory.createPlateau(uuid, dimensions, speed);
		
		PlateauInitializedEvent event = new PlateauInitializedEvent.Builder().withPlateauId(uuid)
				.withDimensions(plateau.getDimensions()).build();
		
		plateau.applyAndPublishEvent(event, plateau.initializePlateau, plateau.initializePlateauWithException);

		return plateau;
	}
	
	@Override
	public void addPlateau(Plateau plateau) {
		plateauRepository.add(plateau);
	}

	@Override
	public Plateau getPlateau(UUID plateauUuid) {
		return plateauRepository.load(plateauUuid);
	}

	@Override
	public boolean isLocationBusy(UUID uuid, TwoDimensionalCoordinates coordinates) {
		return plateauRepository.load(uuid).isLocationBusy(coordinates);
	}

	@Override
	public void updatePlateau(Plateau plateau) {
		plateauRepository.update(plateau);
	}

	@Override
	public void updatePlateauWithFreeLocation(UUID uuid, TwoDimensionalCoordinates coordinates) {
		Plateau plateau = this.getPlateau(uuid);
		plateau.setLocationFree(coordinates);
		this.updatePlateau(plateau);
	}

	@Override
	public void updatePlateauWithOccupiedLocation(UUID uuid, TwoDimensionalCoordinates coordinates) {
		Plateau plateau = this.getPlateau(uuid);
		plateau.setLocationOccupied(coordinates);
		this.updatePlateau(plateau);
	}

	@Override
	public void updatePlateauWithLocations(UUID plateauUUID, TwoDimensionalCoordinates freeLocation,
			TwoDimensionalCoordinates busyLocation) {
		Plateau plateau = this.getPlateau(plateauUUID);
		plateau.setLocationFree(freeLocation);
		plateau.setLocationOccupied(busyLocation);
		this.updatePlateau(plateau);
	}


}
