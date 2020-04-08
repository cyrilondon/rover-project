package com.game.domain.model.entity;

import java.util.UUID;

import com.game.core.validation.ArgumentCheck;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensionalSpace;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.validation.ValidationNotificationHandler;

public class Plateau implements Entity<Plateau>, TwoDimensionalSpace {
	
	private UUID uuid;

	private TwoDimensionalSpace dimensions;

	/**
	 * Matrix to keep track of the occupied locations
	 */
	boolean[][] locations;

	public Plateau(UUID uuid, TwoDimensionalSpace dimensions) {
		this.dimensions = ArgumentCheck.preNotNull(dimensions, GameExceptionLabels.MISSING_PLATEAU_DIMENSIONS);
	}

	public Plateau() {
		this.dimensions = new TwoDimensions(
				new TwoDimensionalCoordinates(TwoDimensionalSpace.DEFAULT_WIDTH, TwoDimensionalSpace.DEFAULT_HEIGHT));
	}
	
	/**
	 * The check of negative coordinates is done in the validator
	 * So this initialization should be called in the validator if all is ok 
	 * Initializing in the constructor would lead to java.lang.NegativeArraySizeException
	 * in case of negative coordinates
	 */
	public Plateau initializeLocations() {
		this.locations = new boolean[dimensions.getWidth()][dimensions.getHeight()];
		return this;
	}

	/**
	 * Mark the position as busy/already set
	 * 
	 * @param coordinates
	 * @return
	 */
	public void setLocationBusy(TwoDimensionalCoordinates coordinates) {
		locations[getLocationIndexFromCoordinate(coordinates.getAbscissa())][getLocationIndexFromCoordinate(
				coordinates.getOrdinate())] = true;
	}
	
	/**
	 * Mark the position as busy/already set
	 * 
	 * @param coordinates
	 * @return
	 */
	public void setLocationFree(TwoDimensionalCoordinates coordinates) {
		locations[getLocationIndexFromCoordinate(coordinates.getAbscissa())][getLocationIndexFromCoordinate(
				coordinates.getOrdinate())] = false;
	}

	/**
	 * Check if the location is already occupied by a Rover or not
	 * 
	 * @param coordinates
	 * @return
	 */
	public boolean isLocationBusy(TwoDimensionalCoordinates coordinates) {
		return locations[getLocationIndexFromCoordinate(coordinates.getAbscissa())][getLocationIndexFromCoordinate(
				coordinates.getOrdinate())];
	}

	@Override
	public Plateau validate(ValidationNotificationHandler handler) {
		return new PlateauValidator(this, handler).validate();
	}

	private int getLocationIndexFromCoordinate(int coordinate) {
		return coordinate - 1;
	}
	
	public int getWidth() {
		return dimensions.getWidth();
	}

	public int getHeight() {
		return dimensions.getHeight();
	}

	public UUID getUuid() {
		return uuid;
	}
	
}
