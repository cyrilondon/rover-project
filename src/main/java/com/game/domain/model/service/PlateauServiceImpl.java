package com.game.domain.model.service;

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

	private PlateauRepository plateauRepository;

	public PlateauServiceImpl(PlateauRepository plateauRepository) {
		this.plateauRepository = plateauRepository;
	}

	@Override
	public Plateau initializePlateau(TwoDimensionalCoordinates coordinates) {
		Plateau plateau = validate(new Plateau(
				new TwoDimensions(new TwoDimensionalCoordinates(coordinates.getAbscissa(), coordinates.getOrdinate()))))
						.initializeLocations();
		plateauRepository.addPlateau(plateau);
		return plateau;
	}

	@Override
	public Plateau initializeRelativisticPlateau(int speed, TwoDimensionalCoordinates coordinates) {
		Plateau plateau = validate(new Plateau(new RelativisticTwoDimensions(speed,
				(new TwoDimensionalCoordinates(coordinates.getAbscissa(), coordinates.getOrdinate())))))
						.initializeLocations();
		plateauRepository.addPlateau(plateau);
		return plateau;
	}

	private Plateau validate(Plateau plateau) {
		return plateau.validate(new EntityDefaultValidationNotificationHandler());
	}

	@Override
	public void markLocationBusy(Plateau plateau, TwoDimensionalCoordinates coordinates) {
		plateau.setLocationBusy(coordinates);
	}
	
	@Override
	public boolean isLocationBusy(TwoDimensionalCoordinates coordinates) {
		return plateauRepository.getPlateau().isLocationBusy(coordinates);
	}

}
