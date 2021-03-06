package com.game.domain.model.entity.plateau;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.UUID;

import org.testng.annotations.Test;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensionalSpace;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.entity.plateau.Plateau;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.IllegalArgumentGameException;

public class PlateauTest {
	
	@Test
	public void testInitialisationIsOk() {
		TwoDimensions dimensions = new TwoDimensions(new TwoDimensionalCoordinates(3, 3));
		Plateau plateau = new Plateau(UUID.randomUUID(), dimensions);
		assertThat(plateau.getWidth()).isEqualTo(dimensions.getWidth());
		assertThat(plateau.getHeight()).isEqualTo(dimensions.getHeight());
	}
	
	@Test
	public void testDefaultInitialization() {
		Plateau plateau = new Plateau();
		assertThat(plateau.getWidth()).isEqualTo(TwoDimensionalSpace.DEFAULT_WIDTH);
		assertThat(plateau.getHeight()).isEqualTo(TwoDimensionalSpace.DEFAULT_HEIGHT);
	}
	
	@Test
	public void testInitialisationWithCoordinatesIsOk() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(3, 3);
		Plateau plateau = new Plateau(UUID.randomUUID(), new TwoDimensions(coordinates));
		assertThat(plateau.getWidth()).isEqualTo(coordinates.getAbscissa());
		assertThat(plateau.getHeight()).isEqualTo(coordinates.getOrdinate());
	}
	
	@Test
	public void testMissingDimensions() {
		Throwable thrown = catchThrowable(() -> new Plateau(UUID.randomUUID(), null));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
						String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE,
								GameExceptionLabels.MISSING_PLATEAU_DIMENSIONS)));
	}
	
	@Test
	public void testMissingUuid() {
		Throwable thrown = catchThrowable(() -> new Plateau(null, new TwoDimensions(new TwoDimensionalCoordinates(3, 4))));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
						String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE,
								GameExceptionLabels.MISSING_PLATEAU_UUID)));
	}
	
	@Test
	public void testSetLocationBusy() {
		int x=2, y=1;
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(3, 3);
		Plateau plateau = new Plateau(UUID.randomUUID(), new TwoDimensions(coordinates)).initializeLocations();
		plateau.setLocationOccupied(new TwoDimensionalCoordinates(x, y));
		assertThat(plateau.isLocationBusy(new TwoDimensionalCoordinates(x, y))).isTrue();
	}
	
	@Test
	public void testSetLocationFree() {
		int x=2, y=1;
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(3, 3);
		Plateau plateau = new Plateau(UUID.randomUUID(), new TwoDimensions(coordinates)).initializeLocations();
		plateau.setLocationOccupied(new TwoDimensionalCoordinates(x, y));
		plateau.setLocationFree(new TwoDimensionalCoordinates(x, y));
		assertThat(plateau.isLocationBusy(new TwoDimensionalCoordinates(x, y))).isFalse();
	}


}
