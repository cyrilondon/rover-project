package com.game.domain.model.entity.plateau;

import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.game.core.validation.ArgumentCheck;
import com.game.domain.model.entity.IdentifiedPublisherDomainEntity;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensionalSpace;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.event.DomainEvent;
import com.game.domain.model.event.plateau.PlateauInitializedEvent;
import com.game.domain.model.event.plateau.PlateauInitializedWithExceptionEvent;
import com.game.domain.model.event.plateau.PlateauSwitchedLocationEvent;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.validation.EntityDefaultValidationNotificationHandler;
import com.game.domain.model.validation.ValidationNotificationHandler;

public class Plateau extends IdentifiedPublisherDomainEntity<Plateau, UUID> implements TwoDimensionalSpace {

	private TwoDimensionalSpace dimensions;

	/**
	 * Matrix to keep track of the occupied locations
	 */
	boolean[][] locations;

	public Plateau(UUID uuid, TwoDimensionalSpace dimensions) {
		this.id = ArgumentCheck.preNotNull(uuid, GameExceptionLabels.MISSING_PLATEAU_UUID);
		this.dimensions = ArgumentCheck.preNotNull(dimensions, GameExceptionLabels.MISSING_PLATEAU_DIMENSIONS);
	}

	public Plateau() {
		this.dimensions = new TwoDimensions(
				new TwoDimensionalCoordinates(TwoDimensionalSpace.DEFAULT_WIDTH, TwoDimensionalSpace.DEFAULT_HEIGHT));
	}
	
	public final Function<DomainEvent, DomainEvent> initializePlateau = event -> {
		validate(new EntityDefaultValidationNotificationHandler());
		return event;
	};

	public final BiFunction<Exception, DomainEvent, DomainEvent> initializePlateauWithException = (exception, event) -> {
		return new PlateauInitializedWithExceptionEvent((PlateauInitializedEvent) event, exception);
	};
	
	public final Function<DomainEvent, DomainEvent> switchLocation = event -> {
		PlateauSwitchedLocationEvent switchEvent = (PlateauSwitchedLocationEvent)event;
		// update in memory plateau locations
		if (switchEvent.getPreviousPosition() != null) {
			this.setLocationFree(switchEvent.getPreviousPosition());
		}
		if (switchEvent.getCurrentPosition() != null)
			this.setLocationOccupied(switchEvent.getCurrentPosition());
		return event;
	};
	

	/**
	 * The check of negative coordinates is done in the validator So this
	 * initialization should be called in the validator if all is ok Initializing in
	 * the constructor would lead to java.lang.NegativeArraySizeException in case of
	 * negative coordinates
	 */
	public Plateau initializeLocations() {
		this.locations = new boolean[getLocationIndexFromDimensions(
				dimensions.getWidth())][getLocationIndexFromDimensions(dimensions.getHeight())];
		return this;
	}

	/**
	 * Mark the position as busy/already set
	 * 
	 * @param coordinates
	 * @return
	 */
	public void setLocationOccupied(TwoDimensionalCoordinates coordinates) {
		locations[coordinates.getAbscissa()][coordinates.getOrdinate()] = true;
	}

	/**
	 * Mark the position as busy/already set
	 * 
	 * @param coordinates
	 * @return
	 */
	public void setLocationFree(TwoDimensionalCoordinates coordinates) {
		locations[coordinates.getAbscissa()][coordinates.getOrdinate()] = false;
	}

	/**
	 * Check if the location is already occupied by a Rover or not
	 * 
	 * @param coordinates
	 * @return
	 */
	public boolean isLocationBusy(TwoDimensionalCoordinates coordinates) {
		return locations[coordinates.getAbscissa()][coordinates.getOrdinate()];
	}

	@Override
	public Plateau validate(ValidationNotificationHandler handler) {
		return new PlateauValidator(this, handler).validate();
	}

	private int getLocationIndexFromDimensions(int coordinate) {
		return coordinate + 1;
	}

	public int getWidth() {
		return dimensions.getWidth();
	}

	public int getHeight() {
		return dimensions.getHeight();
	}

}
