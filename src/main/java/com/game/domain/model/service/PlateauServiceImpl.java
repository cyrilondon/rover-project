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
public class PlateauServiceImpl implements DomainService {

	/**
	 * Initializes the plateau in classical referential
	 * @param coordinates
	 * @return
	 */
	public Plateau initializePlateau(TwoDimensionalCoordinates coordinates) {
		return validate(new Plateau(new TwoDimensions(
				new TwoDimensionalCoordinates(coordinates.getAbscissa(), coordinates.getOrdinate()))));
	}

	/**
	 * Initializes the plateau as observed in relativistic referential from an observer moving at speed v
	 * 
	 * @param speed       observer speed
	 * @param coordinates with rest dimensions
	 * @return relativistic plateau
	 */
	public Plateau initializeRelativisticPlateau(int speed, TwoDimensionalCoordinates coordinates) {
		return validate(new Plateau(new RelativisticTwoDimensions(speed,
				(new TwoDimensionalCoordinates(coordinates.getAbscissa(), coordinates.getOrdinate())))));
	}
	
	
	private Plateau validate(Plateau plateau) {
		return plateau.validate(new EntityDefaultValidationNotificationHandler());
	}

}
