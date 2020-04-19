package com.game.domain.application.command;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.testng.annotations.Test;

import com.game.domain.application.command.plateau.PlateauInitializeCommand;

public class PlateauInitializeCommandTest {

	@Test
	public void testBuilder() {
		int x = 3, y = 4;
		UUID uuid = UUID.randomUUID();
		PlateauInitializeCommand command = new PlateauInitializeCommand.Builder().withId(uuid).withAbscissa(x)
				.withOrdinate(y).build();
		assertThat(command.getAbscissa()).isEqualTo(x);
		assertThat(command.getOrdinate()).isEqualTo(y);
		assertThat(command.getPlateauUuid()).isEqualTo(uuid);
	}

}
