package com.game.domain.application.command;

import java.util.UUID;

import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.game.domain.application.GameContext;
import com.game.domain.application.command.InitializeRoverCommand;

public class InitializeRoverCommandTest {

	@Test
	public void testBuilder() {
		UUID plateauUuid = UUID.randomUUID();
		String name = GameContext.ROVER_NAME_PREFIX;
		int x = 3, y = 4;
		InitializeRoverCommand command = new InitializeRoverCommand.Builder().withPlateauUuid(plateauUuid)
				.withName(name).withAbscissa(x).withOrdinate(y).withOrientation('W').build();
		assertThat(command.getPlateauUuid()).isEqualTo(plateauUuid);
		assertThat(command.getName()).isEqualTo(name);
		assertThat(command.getAbscissa()).isEqualTo(x);
		assertThat(command.getOrdinate()).isEqualTo(y);
	}

}
