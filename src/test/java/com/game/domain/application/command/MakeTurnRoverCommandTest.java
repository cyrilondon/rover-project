package com.game.domain.application.command;

import java.util.UUID;

import org.testng.annotations.Test;

import com.game.domain.application.GameContext;
import com.game.domain.model.entity.Orientation;
import static org.assertj.core.api.Assertions.assertThat;

public class MakeTurnRoverCommandTest {
	
	@Test
	public void testCommand() {
		UUID uuid = UUID.randomUUID();
		String name = GameContext.ROVER_NAME_PREFIX;
		char orientation = Orientation.SOUTH.getValue().charAt(0);
		MakeTurnRoverCommand command = new MakeTurnRoverCommand(uuid, name, orientation);
		assertThat(command.getPlateauUuid()).isEqualTo(uuid);
		assertThat(command.getName()).isEqualTo(name);
		assertThat(command.getTurn()).isEqualTo(orientation);
	}

}
