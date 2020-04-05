package com.game.domain.model.service;

import com.game.domain.model.entity.Plateau;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

public interface PlateauService extends DomainService {

	/**
	 * Initializes the plateau in classical referential
	 * @param coordinates
	 * @return plateau with classical dimensions
	 */
	Plateau initializePlateau(TwoDimensionalCoordinates coordinates);

	
	/**
	 * Initializes the plateau as observed in relativistic referential from an observer moving at speed v
	 * 
	 * @param speed       observer speed
	 * @param coordinates with rest dimensions
	 * @return plateau with relativistic dimensions
	 */
	Plateau initializeRelativisticPlateau(int speed, TwoDimensionalCoordinates coordinates);

}
