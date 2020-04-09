package com.game.domain.application.command;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.testng.annotations.Test;

public class InititializePlateauCommandTest {

	@Test
	public void testBuilder() {
		int x = 3, y = 4;
		UUID uuid = UUID.randomUUID();
		InitializePlateauCommand command = new InitializePlateauCommand.Builder().withUuid(uuid).withAbscissa(x)
				.withOrdinate(y).build();
		assertThat(command.getAbscissa()).isEqualTo(x);
		assertThat(command.getOrdinate()).isEqualTo(y);
		assertThat(command.getPlateauUuid()).isEqualTo(uuid);
	}

}
