package com.domain.model.event.plateau;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.testng.annotations.Test;

import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.event.plateau.PlateauSwitchedLocationEvent;

public class PlateauSwitchedLocationEventTest {

	@Test
	public void testEvent() {
		UUID plateauId = UUID.randomUUID();
		TwoDimensionalCoordinates currentPosition = new TwoDimensionalCoordinates(1, 1);
		TwoDimensionalCoordinates previousPosition = new TwoDimensionalCoordinates(2, 2);
		PlateauSwitchedLocationEvent event = new PlateauSwitchedLocationEvent.Builder().withPlateauId(plateauId)
				.withPreviousPosition(previousPosition).withCurrentPosition(currentPosition).build();
		assertThat(event.getPlateauId()).isEqualTo(plateauId);
		assertThat(event.getCurrentPosition()).isEqualTo(currentPosition);
		assertThat(event.getPreviousPosition()).isEqualTo(previousPosition);
	}

}
