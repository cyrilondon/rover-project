package com.game.domain.application.command;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.testng.annotations.Test;

import com.game.domain.application.GameContext;
import com.game.domain.model.entity.RoverIdentifier;
import com.game.domain.model.entity.RoverTurnInstruction;

public class RoverTurnCommandTest {
	
	@Test
	public void testCommand() {
		UUID uuid = UUID.randomUUID();
		String name = GameContext.ROVER_NAME_PREFIX;
		RoverTurnInstruction turn = RoverTurnInstruction.LEFT;
		RoverIdentifier roverId = new RoverIdentifier(uuid, name);
		RoverTurnCommand command = new RoverTurnCommand(roverId, turn);
		assertThat(command.getRoverId()).isEqualTo(roverId);
		assertThat(command.getTurn()).isEqualTo(turn);
	}

}
