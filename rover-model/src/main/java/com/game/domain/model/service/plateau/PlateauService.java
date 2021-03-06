package com.game.domain.model.service.plateau;

import java.util.UUID;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.plateau.Plateau;
import com.game.domain.model.service.DomainService;

public interface PlateauService extends DomainService {
	
	/**
	 * Add persistence plateau
	 * @param plateau
	 */
	void addPlateau(Plateau plateau);

	/**
	 * Initializes the plateau
	 * 
	 * @param speed       observer speed
	 * @param coordinates with rest dimensions
	 * @return plateau with relativistic dimensions
	 */
	Plateau initializePlateau(UUID uuid, TwoDimensionalCoordinates coordinates, int observerSpeed);

	/**
	 * Mark the current location as busy
	 */
	void updatePlateauWithOccupiedLocation(UUID uuid, TwoDimensionalCoordinates coordinates);
	
	/**
	 * Mark the current location as busy
	 */
	void updatePlateauWithFreeLocation(UUID uuid, TwoDimensionalCoordinates coordinates);

	/**
	 * Check if the location is busy
	 * @param coordinates
	 * @return
	 */
	boolean isLocationBusy(UUID uuid, TwoDimensionalCoordinates coordinates);

	/**
	 * Load plateau from Uuid
	 * @param plateauUuid
	 * @return
	 */
	Plateau getPlateau(UUID plateauUuid);

	void updatePlateau(Plateau plateau);

	/**
	 * Update the location of the persistent Plateau
	 * @param plateauUUID
	 * @param freeLocation
	 * @param busyLocation
	 */
	void updatePlateauWithLocations(UUID plateauUUID, TwoDimensionalCoordinates freeLocation,
			TwoDimensionalCoordinates busyLocation);

}
