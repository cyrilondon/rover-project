package com.game.domain.model.service;

import java.util.UUID;

import com.game.domain.model.entity.Plateau;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

public interface PlateauService extends DomainService {

	/**
	 * Initializes the plateau in classical referential
	 * 
	 * @param coordinates
	 * @return plateau with classical dimensions
	 */
	Plateau initializePlateau(UUID uuid, TwoDimensionalCoordinates coordinates);

	/**
	 * Initializes the plateau as observed in relativistic referential from an
	 * observer moving at speed v
	 * 
	 * @param speed       observer speed
	 * @param coordinates with rest dimensions
	 * @return plateau with relativistic dimensions
	 */
	Plateau initializeRelativisticPlateau(UUID uuid, int speed, TwoDimensionalCoordinates coordinates);

	/**
	 * Mark the current location as busy
	 */
	void setLocationBusy(UUID uuid, TwoDimensionalCoordinates coordinates);
	
	/**
	 * Mark the current location as busy
	 */
	void setLocationFree(UUID uuid, TwoDimensionalCoordinates coordinates);

	/**
	 * Check if the location is busy
	 * @param coordinates
	 * @return
	 */
	boolean isLocationBusy(UUID uuid, TwoDimensionalCoordinates coordinates);

}
