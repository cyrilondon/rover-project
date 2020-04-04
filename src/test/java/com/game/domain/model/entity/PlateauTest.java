package com.game.domain.model.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.testng.annotations.Test;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensionalSpace;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.IllegalArgumentGameException;

public class PlateauTest {
	
	@Test
	public void testInitialisationIsOk() {
		TwoDimensions dimensions = new TwoDimensions(new TwoDimensionalCoordinates(3, 3));
		Plateau plateau = new Plateau(dimensions);
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
		Plateau plateau = new Plateau(coordinates);
		assertThat(plateau.getWidth()).isEqualTo(coordinates.getWidth());
		assertThat(plateau.getHeight()).isEqualTo(coordinates.getHeight());
	}
	
	@Test
	public void testMissingDimensions() {
		Throwable thrown = catchThrowable(() -> new Plateau((TwoDimensions)null));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
						String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE,
								GameExceptionLabels.MISSING_PLATEAU_DIMENSIONS)));
	}


}
