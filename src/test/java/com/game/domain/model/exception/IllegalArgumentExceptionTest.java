package com.game.domain.model.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

public class IllegalArgumentExceptionTest {

	private final String errorMessage = "Something going wrong in this game";

	@Test
	public void testSimpleMessage() {
		
		GameException exception = new IllegalArgumentGameException(errorMessage);
		assertThat(exception.getMessage()).isEqualTo(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
				GameExceptionLabels.ILLEGAL_ARGUMENT_CODE, errorMessage));
	}

}
