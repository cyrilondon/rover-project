package com.game.domain.application.command.plateau;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.testng.annotations.Test;

import com.game.domain.application.command.plateau.PlateauInitializeCommand;

public class PlateauInitializeCommandTest {

	@Test
	public void testBuilder() {
		int x = 3, y = 4;
		UUID uuid = UUID.randomUUID();
		PlateauInitializeCommand command = new PlateauInitializeCommand.Builder().withId(uuid).withWidth(x)
				.withHeight(y).build();
		assertThat(command.getWidth()).isEqualTo(x);
		assertThat(command.getHeight()).isEqualTo(y);
		assertThat(command.getPlateauUuid()).isEqualTo(uuid);
	}

}
