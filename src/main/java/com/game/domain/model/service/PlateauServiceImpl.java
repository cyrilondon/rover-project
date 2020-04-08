package com.game.domain.model.service;

import java.util.UUID;

import com.game.domain.model.entity.Plateau;
import com.game.domain.model.entity.dimensions.RelativisticTwoDimensions;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.repository.PlateauRepository;
import com.game.domain.model.validation.EntityDefaultValidationNotificationHandler;

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
	public Plateau initializePlateau(UUID uuid, TwoDimensionalCoordinates coordinates) {
		Plateau plateau = validate(new Plateau(uuid,
				new TwoDimensions(new TwoDimensionalCoordinates(coordinates.getAbscissa(), coordinates.getOrdinate()))));
		plateauRepository.add(plateau);
		return plateau;
	}

	@Override
	public Plateau initializeRelativisticPlateau(UUID uuid, int speed, TwoDimensionalCoordinates coordinates) {
		Plateau plateau = validate(new Plateau(uuid, new RelativisticTwoDimensions(speed,
				new TwoDimensions(
						(new TwoDimensionalCoordinates(coordinates.getAbscissa(), coordinates.getOrdinate()))))));
		plateauRepository.add(plateau);
		return plateau;
	}
	
	@Override
	public Plateau loadPlateau(UUID plateauUuid) {
		return plateauRepository.load(plateauUuid);
	}

	private Plateau validate(Plateau plateau) {
		return plateau.validate(new EntityDefaultValidationNotificationHandler());
	}

	@Override
	public void setLocationBusy(UUID uuid, TwoDimensionalCoordinates coordinates) {
		plateauRepository.load(uuid).setLocationBusy(coordinates);
	}

	@Override
	public boolean isLocationBusy(UUID uuid, TwoDimensionalCoordinates coordinates) {
		return plateauRepository.load(uuid).isLocationBusy(coordinates);
	}

	@Override
	public void setLocationFree(UUID uuid, TwoDimensionalCoordinates coordinates) {
		plateauRepository.load(uuid).setLocationFree(coordinates);
		
	}

}
