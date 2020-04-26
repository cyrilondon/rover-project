package com.game.core.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.testng.annotations.Test;

import com.game.domain.model.entity.rover.Orientation;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.IllegalArgumentGameException;

public class ArgumentCheckTest {

	private final static String ERROR_MESSAGE = "error message";

	@Test
	public void testCheckNull() {
		Object objectToCheck = null;
		Throwable thrown = catchThrowable(() -> ArgumentCheck.preNotNull(objectToCheck, ERROR_MESSAGE));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
						String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE, ERROR_MESSAGE)));
	}

	@Test
	public void testCheckNotNull() {
		Orientation objectToCheck = Orientation.EAST;
		assertThat(ArgumentCheck.preNotNull(objectToCheck, ERROR_MESSAGE)).isEqualTo(objectToCheck);
	}
	
	@Test
	public void testCheckEmpty() {
		String stringToCheck = "  ";
		Throwable thrown = catchThrowable(() -> ArgumentCheck.preNotEmpty(stringToCheck, ERROR_MESSAGE));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
						String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE, ERROR_MESSAGE)));
	}


}
