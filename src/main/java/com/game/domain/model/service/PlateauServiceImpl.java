package com.game.domain.model.service;

import com.game.domain.model.entity.Plateau;
import com.game.domain.model.entity.dimensions.RelativisticTwoDimensions;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.validation.EntityDefaultValidationNotificationHandler;

/**
 * Domain service which has the responsibility to handle the entity
 * {@link Plateau}
 *
 */
public class PlateauServiceImpl implements PlateauService {

	
	@Override
	public Plateau initializePlateau(TwoDimensionalCoordinates coordinates) {
		return validate(new Plateau(new TwoDimensions(
				new TwoDimensionalCoordinates(coordinates.getAbscissa(), coordinates.getOrdinate()))));
	}

	
	@Override
	public Plateau initializeRelativisticPlateau(int speed, TwoDimensionalCoordinates coordinates) {
		return validate(new Plateau(new RelativisticTwoDimensions(speed,
				(new TwoDimensionalCoordinates(coordinates.getAbscissa(), coordinates.getOrdinate())))));
	}
	
	
	private Plateau validate(Plateau plateau) {
		return plateau.validate(new EntityDefaultValidationNotificationHandler());
	}

}
