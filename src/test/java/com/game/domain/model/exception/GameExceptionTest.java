package com.game.domain.model.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.game.domain.model.exception.GameException;

public class GameExceptionTest {
	
	private final String errorMessage = "Something going wrong in this game";
	
	private final String errorCode = "CODE_001";
	
	@Test
	public void testSimpleMessage() {
		GameException exception = new GameException(errorMessage);
		assertThat(exception.getMessage()).isEqualTo(errorMessage);
	}
	
	@Test
	public void testSimpleMessageWithCode() {
		GameException exception = new GameException(errorMessage, errorCode);
		assertThat(exception.getMessage()).isEqualTo(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN, errorCode, errorMessage));
	}
	
	@Test
	public void testConstructorWithRootCause() {
		Exception rootException = new RuntimeException();
		GameException exception = new GameException(errorMessage, errorCode, rootException);
		assertThat(exception.getMessage()).isEqualTo(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN, errorCode, errorMessage));
		assertThat(exception.getCause()).isEqualTo(rootException);
	}

}
