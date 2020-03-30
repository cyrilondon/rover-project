package com.game.domain.model.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.testng.annotations.Test;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.IllegalArgumentGameException;

public class BoardTest {
	
	@Test
	public void testInitialisationIsOk() {
		TwoDimensions dimensions = new TwoDimensions(new TwoDimensionalCoordinates(3, 3));
		Board board = new Board(dimensions);
		assertThat(board.getWidth()).isEqualTo(dimensions.getWidth());
		assertThat(board.getHeight()).isEqualTo(dimensions.getHeight());
	}
	
	@Test
	public void testMissingDimensions() {
		Throwable thrown = catchThrowable(() -> new Board(null));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
						String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE,
								GameExceptionLabels.MISSING_BOARD_DIMENSIONS)));
	}


}
